package com.allegro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
@Controller
public class AllegroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllegroApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "number", defaultValue = "1") int num, Model model) {
        model.addAttribute("num", num);
        model.addAttribute("even", isNumberEven(num));
        return "hello";
    }

    public static boolean isNumberEven(int number){return number%2==0;}

}
