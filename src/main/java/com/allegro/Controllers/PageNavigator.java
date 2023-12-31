package com.allegro.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/")
public class PageNavigator {


    public PageNavigator(){}

    @RequestMapping("/")
    public ModelAndView getLoginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView getRegisterPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register.html");
        return modelAndView;
    }

    @RequestMapping("/mainPage")
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

    @RequestMapping("/product-info/{productId}")
    public ModelAndView getProductInfoPage(@PathVariable String productId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productId", productId);
        modelAndView.setViewName("product-info.html");
        return modelAndView;
    }

}
