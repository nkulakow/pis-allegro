package com.allegro.Controllers;

import com.allegro.DTO.ProductDTO;
import com.allegro.DTO.ProductWithoutCategoryDTO;
import com.allegro.Entity.Category;
import com.allegro.Entity.User;
import com.allegro.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.allegro.Service.CategoryService;
import com.allegro.Service.ProductService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
            @RequestParam("description") String productDescription,
            @RequestParam(value = "photo", required = false) MultipartFile productPhoto, HttpSession session) {
        // @TODO: logged in user
        String login = (String)session.getAttribute("login");
        if(login == null){
            return "You must login in order to add a product";
        }
        var user = this.userService.getUserByEmail(login);
        var quantity = 2;
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

    @GetMapping("/get-all")
    public List<ProductWithoutCategoryDTO> getData() {
        var products = this.productService.getProducts();
        return this.productService.getProductsWithoutCategory(products);
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

    @GetMapping("/get-products-in-categories")
    public List<ProductWithoutCategoryDTO> getProductsInCategories() {
        var products = this.productService.getProductByCategories(List.of("food", "book"));
        return this.productService.getProductsWithoutCategory(products);
    }
}
