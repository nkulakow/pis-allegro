package com.allegro.Service;

import com.allegro.DTO.ProductWithoutCategoryDTO;
import com.allegro.Document.MongoProduct;
import com.allegro.Entity.CartItem;
import com.allegro.Entity.Category;
import com.allegro.Entity.PostgresProduct;
import com.allegro.DTO.ProductDTO;
import com.allegro.Entity.User;
import com.allegro.Repository.PostgresProductRepository;
import com.allegro.Repository.MongoProductRepository;
import com.allegro.utilis.IdGenerator;
import jakarta.transaction.Transactional;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final MongoProductRepository mongoProductRepository;

    private final PostgresProductRepository postgresProductRepository;


    private final CategoryService categoryService;

    @Autowired
    ProductService(MongoProductRepository mongoProductRepository, PostgresProductRepository repository, CategoryService categoryService){
        this.mongoProductRepository = mongoProductRepository;
        this.postgresProductRepository  = repository;
        this.categoryService = categoryService;
    }

    @Transactional
    public void addProduct(User user, String name, List<Category> categories, float price, int quantity, String description, MultipartFile photo) throws IOException {
        String id = IdGenerator.generateId();
        List<Binary> photos = null;
        if (photo != null) {
            byte[] photoData = photo.getBytes();
            photos = new ArrayList<>();
            photos.add(new Binary(photoData));
        }
        ProductDTO productDTO = new ProductDTO(user, id, name, categories, price, quantity, description, photos);
        postgresProductRepository.save(productDTO.getPostgres());
        mongoProductRepository.insert(productDTO.getMongo());
        user.addSoldProduct(productDTO.getPostgres());
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO){
        mongoProductRepository.save(productDTO.getMongo());
        postgresProductRepository.save(productDTO.getPostgres());
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO){
        mongoProductRepository.save(productDTO.getMongo());
        postgresProductRepository.save(productDTO.getPostgres());
    }

    public List<MongoProduct> getMongoProducts(){
        return mongoProductRepository.findAll();
    }

    public List<PostgresProduct> getPostgresProducts (){
        return this.postgresProductRepository.findAll();
    }

    public ArrayList<ProductDTO> getProducts () {
        var mongoList = this.mongoProductRepository.findAll();
        var postgresList = this.postgresProductRepository.findAll();
        var productList = new ArrayList<ProductDTO>();
        for (var postProd: postgresList) {
            var targetId = postProd.getId();
            Optional<MongoProduct> mProdFound = mongoList.stream().filter(mp -> targetId.equals(mp.getId())).findFirst();
            if (mProdFound.isPresent()){
                MongoProduct mongoProd = mProdFound.get();
                productList.add(new ProductDTO(postProd, mongoProd));
            }
            else {
//                @TODO throw or sth else
            }
        }
        return productList;
    }

    public ArrayList<ProductWithoutCategoryDTO> getProductsWithoutCategory(List<ProductDTO> productList){
        var productWithoutCategoryList = new ArrayList<ProductWithoutCategoryDTO>();
        for (var product: productList) {
            productWithoutCategoryList.add(new ProductWithoutCategoryDTO(product));
        }
        return productWithoutCategoryList;
    }

    public ArrayList<ProductDTO> getProductsWithCategory(ArrayList<ProductWithoutCategoryDTO> productWithoutCategoryList){
        var productList = new ArrayList<ProductDTO>();
        for (var product: productWithoutCategoryList) {
            var categories = categoryService.getCategoriesByNames(product.getCategories());
            productList.add(new ProductDTO(product.getUser(), product.getId(), product.getName(), categories, product.getPrice(), product.getQuantity(), product.getDescription(), product.getPhotos()));
        }
        return productList;
    }


    public List<ProductDTO> findByText(String text) {
        String[] wordArray = text.split("\\s+");
        var productList = new ArrayList<ProductDTO>();
        var seenProductIds = new HashSet<String>();
        for (String word: wordArray) {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(word);
            var postgresList = postgresProductRepository.searchProducts(word);
            var mongoList = mongoProductRepository.findAllBy(criteria, Sort.by("description"));

            for (var postProd : postgresList) {
                var targetId = postProd.getId();
                if (!seenProductIds.contains(targetId)) {
                    Optional<MongoProduct> mProdFound = mongoProductRepository.findById(targetId);
                    mProdFound.ifPresent(mongoProduct -> {
                        productList.add(new ProductDTO(postProd, mongoProduct));
                        seenProductIds.add(targetId);
                    });
                }
            }

            for (var mongoProd : mongoList) {
                var targetId = mongoProd.getId();
                if (!seenProductIds.contains(targetId)) {
                    Optional<PostgresProduct> pProdFound = postgresProductRepository.findById(targetId);
                    pProdFound.ifPresent(postgresProd -> {
                        productList.add(new ProductDTO(postgresProd, mongoProd));
                        seenProductIds.add(targetId);
                    });
                }
            }
        }

        return productList;
    }


    public ProductDTO getWholeProductByPostgres(PostgresProduct postgresProduct){
        var mongoProduct = this.mongoProductRepository.findById(postgresProduct.getId());
        if (mongoProduct.isPresent()){
            return new ProductDTO(postgresProduct, mongoProduct.get());
        }
        else {
            //@TODO throw or sth else
        }
        return null;
    }

    public List<ProductDTO> getWholeSoldProductsByPostgres(User user){
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (PostgresProduct postgresProduct : user.getSoldProducts()) {
            productDTOS.add(getWholeProductByPostgres(postgresProduct));
        }
        return productDTOS;
    }

    public ProductDTO getWholeProductByPostgres(CartItem cartItem){
        var mongoProduct = this.mongoProductRepository.findById(cartItem.getProduct().getId());
        if (mongoProduct.isPresent()){
            return new ProductDTO(cartItem.getProduct(), mongoProduct.get());
        }
        else {
            //@TODO throw or sth else
        }
        return null;
    }

    public List<ProductDTO> getWholeCartItemsByPostgres(User user){
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (CartItem cartItem : user.getCartItems()) {
            productDTOS.add(getWholeProductByPostgres(cartItem));
        }
        return productDTOS;
    }


    public ProductDTO getProductById(String productId) {
        var postgresProd = this.postgresProductRepository.findById(productId);
        var mongoProd = this.mongoProductRepository.findById(productId);
        if (postgresProd.isPresent() && mongoProd.isPresent()){
            return new ProductDTO(postgresProd.get(), mongoProd.get());
        }
        return null;
    }

    public List<ProductDTO> getProductByUser(String user_id) {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        var postgresProd = this.postgresProductRepository.findByUser_Id(user_id);
        for (PostgresProduct product : postgresProd) {
            var mongoProd = this.mongoProductRepository.findById(product.getId());
            mongoProd.ifPresent(mongoProduct -> productDTOS.add(new ProductDTO(product, mongoProduct)));
        }
        return productDTOS;
    }

    public List<ProductDTO> getProductByCategories(List<String> categories) {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        var postgresProd = this.postgresProductRepository.findByCategories_CategoryNameIn(categories);
        for (PostgresProduct product : postgresProd) {
            var mongoProd = this.mongoProductRepository.findById(product.getId());
            mongoProd.ifPresent(mongoProduct -> productDTOS.add(new ProductDTO(product, mongoProduct)));
        }
        return productDTOS;
    }
}
