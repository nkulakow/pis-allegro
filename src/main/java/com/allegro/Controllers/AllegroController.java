//package com.allegro.Controllers;
//
//import com.allegro.DTO.ProductDTO;
//import com.allegro.Document.MongoProduct;
//import com.allegro.Entity.Category;
//import com.allegro.Entity.PostgresProduct;
//import com.allegro.Service.CategoryService;
//import com.allegro.Service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//public class AllegroController {
//
//
//    ProductService productService;
//    CategoryService categoryService;
//
//    @Autowired
//    public AllegroController(ProductService productService, CategoryService categoryService){
//        this.productService = productService;
//        this.categoryService = categoryService;
//    }
//
//
//    @RequestMapping("/hello")
//    public ModelAndView sayHello(@RequestParam(value = "name", defaultValue = "name") String name, @RequestParam(value = "description", defaultValue = "description") String des, @RequestParam(value = "categories", required = false) List<String> categoryIds, @RequestParam("photo") MultipartFile photo) throws IOException {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("hello.html");
//        modelAndView.addObject("name", name);
//        modelAndView.addObject("des", des);
//        List<Category> selectedCategories = categoryService.getCategoriesByIds(categoryIds);
//
//        productService.addProduct(name, selectedCategories, 123, des, photo);
//        return modelAndView;
//    }
//
//    @RequestMapping("/")
//    public ModelAndView getMainPage(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index.html");
//
//        String info = "MONGODB: ";
//        List<MongoProduct> listOfMongoProducts = productService.getMongoProducts();
//        for (MongoProduct mongoProduct : listOfMongoProducts
//             ) {
//            info = info.concat(mongoProduct.toString());
//            info = info.concat(",");
//        }
//        List<PostgresProduct> postgresProductList = productService.getPostgresProducts();
//        info = info.concat("/n POSTGRESQL: ");
//        for (PostgresProduct product: postgresProductList){
//            info = info.concat(product.toString());
//            info = info.concat(",");
//        }
//        modelAndView.addObject("info", info);
//
//        List<Category> categories = categoryService.getAllCategories();
//        modelAndView.addObject("categories", categories);
//
//        return modelAndView;
//    }
//
//    @RequestMapping("/fulltext-search")
//    public ModelAndView fullTextSearch(@RequestParam(value = "search", defaultValue = "search") String search){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("fulltext-search.html");
//        List<ProductDTO> productList = productService.findByText(search);
//        modelAndView.addObject("productList", productList);
//        return modelAndView;
//    }
//
//    @RequestMapping("/add-category")
//    public ModelAndView addCategory(@RequestParam(value = "name", defaultValue = "name") String name){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("added-category.html");
//        categoryService.addCategory(name);
//        modelAndView.addObject("name", name);
//        return modelAndView;
//    }
//
//    public static boolean isNumberEven(int number){return number%2==0;}
//
//}
