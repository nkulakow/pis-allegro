package com.allegro.Controllers;

import com.allegro.Entity.Product;
import com.allegro.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AllegroController {


    @Autowired
    ProductService productService;

    @RequestMapping("/hello")
    public ModelAndView sayHello(@RequestParam(value = "name", defaultValue = "name") String name, @RequestParam(value = "category", defaultValue = "cat") String cat) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello.html");
        modelAndView.addObject("name", name);
        modelAndView.addObject("cat", cat);
        productService.addStudent(name, cat);
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView connectToMongoDB(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        String info = "";
        List<Product> listOfProducts = productService.getStudents();
        for (Product product : listOfProducts
             ) {
            info = info.concat(product.toString());
            info = info.concat(",");
        }
        modelAndView.addObject("info", info);
        return modelAndView;
    }

    public static boolean isNumberEven(int number){return number%2==0;}

}
