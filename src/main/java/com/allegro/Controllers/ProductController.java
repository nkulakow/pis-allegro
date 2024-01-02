package com.allegro.Controllers;

import com.allegro.DTO.ProductDTO;
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
import jakarta.servlet.http.HttpSession;

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
    public List<String> searchForProducts(@RequestParam String searchPhrase) {
        List<ProductDTO> productList = this.productService.findByText(searchPhrase);
        List<String> nameList = new ArrayList<>();
        for (ProductDTO productDTO : productList) nameList.add(productDTO.getName());
        return nameList;
    }

    @PostMapping("/add")
    public String AddProduct(
            @RequestParam String productName,
            @RequestParam List<String> productCategories,
            @RequestParam int productPrice,
            @RequestParam String productDescription,
            @RequestPart(value = "file", required = false) MultipartFile productPhoto, HttpSession session) {
        System.out.println(productPhoto == null);
        // @TODO: logged in user
        var user = this.userService.getUserByEmail((String)session.getAttribute("login"));
        var quantity = 2;
        try {
            this.productService.addProduct(user,
                    productName,
                    new ArrayList<>(),
                    productPrice,
                    quantity,
                    productDescription,
                    productPhoto);
            return "Successfully added a new product: ".concat(productName);
        } catch (IOException e) {
            return "Could not add a new product: ".concat(productName);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ProductDTO> getData() {
        ProductDTO responseData = this.productService.getProducts().get(3);
        System.out.println(responseData.getPhotos()==null);
        return ResponseEntity.ok(responseData);
    }
}
