package com.allegro.Controllers;

import com.allegro.DTO.ProductDTO;
import com.allegro.DTO.ProductWithoutCategoryDTO;
import com.allegro.Entity.CartItem;
import com.allegro.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.allegro.Service.CategoryService;
import com.allegro.Service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;
    CategoryService categoryService;

    UserService userService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, UserService userService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("/fulltext-search-results")
    public List<ProductWithoutCategoryDTO> searchForProducts(@RequestParam String searchPhrase) {
        List<ProductDTO> productList = this.productService.findByText(searchPhrase);
        return this.productService.getProductsWithoutCategory(productList);
    }

    @PostMapping("/add")
    public String AddProduct(
            @RequestParam("name") String productName,
            @RequestParam("categories") List<String> categoriesNames,
            @RequestParam("price") float productPrice,
            @RequestParam("quantity") int quantity,
            @RequestParam("description") String productDescription,
            @RequestParam(value = "photo", required = false) MultipartFile productPhoto, HttpSession session) {
        // @TODO: logged in user
        String login = (String)session.getAttribute("login");
        if(login == null){
            return "You must login in order to add a product";
        }
        var user = this.userService.getUserByEmail(login);
        try {
            this.productService.addProduct(
                    user,
                    productName,
                    this.categoryService.getCategoriesByNames(categoriesNames),
                    productPrice,
                    quantity,
                    productDescription,
                    productPhoto);
            return "Successfully added a new product: ".concat(productName);
        } catch (IOException e) {
            return "Could not add a new product: ".concat(productName);
        }
    }

    @GetMapping("/get-categories")
    public List<String> getCategories() {
        return categoryService.getAllCategoryNames();
    }

    @GetMapping("/get-product-info")
    public ProductWithoutCategoryDTO getProductInfo(@RequestParam String productId) {
        return new ProductWithoutCategoryDTO(this.productService.getProductById(productId));
    }

    @GetMapping("/add-some-categories")
    public void insertCategories() {
        this.categoryService.addCategory("food");
        this.categoryService.addCategory("cloth");
        this.categoryService.addCategory("sports");
        this.categoryService.addCategory("game");
        this.categoryService.addCategory("electronic");
        this.categoryService.addCategory("film");
        this.categoryService.addCategory("book");
    }

    @GetMapping("/get-user-products")
    public List<ProductWithoutCategoryDTO> getUserProducts(HttpSession session) {
        String login = (String)session.getAttribute("login");
        if(login == null){
            return new ArrayList<>();
        }
        var user = this.userService.getUserByEmail(login);
        var user_id = user.getId();
        var products = this.productService.getProductByUser(user_id);
        return this.productService.getProductsWithoutCategory(products);
    }

    @PostMapping("/get-all-in-categories")
    public List<ProductWithoutCategoryDTO> getProductsInCategories(@RequestBody List<String> categories) {
        var products = this.productService.getProductByCategories(categories);
        return this.productService.getProductsWithoutCategory(products);
    }

    @PostMapping("/edit-product")
    public String editProduct(
            @RequestParam("name") String productName,
            @RequestParam("categories") List<String> categoriesNames,
            @RequestParam("price") float productPrice,
            @RequestParam("description") String productDescription,
            @RequestParam(value = "photo", required = false) MultipartFile productPhoto,
            @RequestParam("id") String productId,
            @RequestParam("quantity") int quantity) {
        ProductDTO productDTO = this.productService.getProductById(productId);
        productDTO.setName(productName);
        productDTO.setCategories(this.categoryService.getCategoriesByNames(categoriesNames));
        productDTO.setPrice(productPrice);
        productDTO.setDescription(productDescription);
        productDTO.setQuantity(quantity);
        if (productPhoto != null) {
            try {
                productDTO.setPhotos(List.of(new Binary(productPhoto.getBytes())));
            } catch (IOException e) {
                return "Could not edit a product: ".concat(productName);
            }
        }
        this.productService.updateProduct(productDTO);
        return "Successfully edited a product: ".concat(productName);
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String productId, HttpSession session) {
        String login = (String)session.getAttribute("login");
        if(login == null){
            return "You must login in order to add a product to cart";
        }
        var user = this.userService.getUserByEmail(login);
        var product = this.productService.getProductById(productId);
        var cartItem = new CartItem(user, product.getPostgres(), 1);
        this.userService.addCartItem(user, cartItem);
        return "Successfully added a product to cart";
    }

    @RequestMapping("/get-cart-items")
    public List<ProductWithoutCategoryDTO> getCartItems(HttpSession session) {
        String login = (String)session.getAttribute("login");
        if(login == null){
            return new ArrayList<>();
        }
        var user = this.userService.getUserByEmail(login);
        var cartItems = user.getCartItems();
        var products = new ArrayList<ProductDTO>();
        for (var cartItem: cartItems) {
            var product = this.productService.getProductById(cartItem.getProduct().getId());
            products.add(product);
        }
        return this.productService.getProductsWithoutCategory(products);
    }

}
