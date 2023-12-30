package com.allegro.Controllers;

import com.allegro.Service.CategoryService;
import com.allegro.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class PageNavigator {

    ProductService productService;
    CategoryService categoryService;

    @Autowired
    public PageNavigator(ProductService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/")
    public ModelAndView getMainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main-page.html");
        return modelAndView;
    }

    @RequestMapping("/fulltext-search")
    public ModelAndView getSearchPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fulltext-search.html");
        return modelAndView;
    }

    @RequestMapping("/previous-buys")
    public ModelAndView getPreviousBuysPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("previous-buys.html");
        return modelAndView;
    }

    @RequestMapping("/manage-products")
    public ModelAndView getAddProductPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manage-products.html");
        return modelAndView;
    }

    @RequestMapping("/cart")
    public ModelAndView getCartPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cart.html");
        return modelAndView;
    }

    @RequestMapping("/view-all-products")
    public ModelAndView getAllProductsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view-all-products.html");
        return modelAndView;
    }

//    @RequestMapping("/add-category")
//    public ModelAndView getAddCategoryPage(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("add-category.html");
//        return modelAndView;
//    }
}
