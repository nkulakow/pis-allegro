package com.allegro.Controllers;

import com.allegro.DTO.ProductDTO;
import com.allegro.DTO.ProductWithoutCategoryDTO;
import com.allegro.Entity.Category;
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

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
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
            @RequestParam("name") String productName,
            @RequestParam("categories") List<String> categoriesNames,
            @RequestParam("price") float productPrice,
            @RequestParam("description") String productDescription,
            @RequestParam(value = "photo", required = false) MultipartFile productPhoto) {
//        System.out.println(productName);
//        System.out.println(productCategories);
//        System.out.println(productPrice);
//        System.out.println(productDescription);
//        System.out.println(productPhoto.getSize());
        try {
            this.productService.addProduct(
                    productName,
                    this.categoryService.getCategoriesByNames(categoriesNames),
                    productPrice,
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

//    @GetMapping("/get-product-info")
//    public ProductDTO getProductInfo(@RequestParam String producdId) {
////        var product = this.productService.get
//    }

    @GetMapping("/get-all")
    public List<ProductWithoutCategoryDTO> getData() {
        return this.productService.getProductsWithoutCategory();
    }
}
