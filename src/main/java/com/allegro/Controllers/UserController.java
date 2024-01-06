package com.allegro.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import com.allegro.Entity.User;
import com.allegro.Service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/loginVerify")
    public ModelAndView login(@RequestParam  String email, @RequestParam String password, HttpSession session) {
        if (this.userService.login(email, password)) {
           session.setAttribute("login", email);
           session.setAttribute("password", password);
           return new ModelAndView("main-page");
        }
        ModelAndView modelAndView = new ModelAndView("error");
        String paragraphText = "Invalid login or password";
        modelAndView.addObject("paragraphText", paragraphText);
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String name, @RequestParam String surname, @RequestParam  String email, @RequestParam String password) {
        if (this.userService.getUserByEmail(email) != null) {
            ModelAndView modelAndView = new ModelAndView("error");
            String paragraphText = "User already exists";
            modelAndView.addObject("paragraphText", paragraphText);
            return modelAndView;
        }
        User newUser = new User(email, password, name, surname, new ArrayList<>(), new ArrayList<>());
        this.userService.addUser(newUser);
        return new ModelAndView("login");
    }

    @PostMapping("/buy-cart-items")
    public String buyCartItems(HttpSession session) {
        String login = (String)session.getAttribute("login");
        var user = this.userService.getUserByEmail(login);
        this.userService.buyCartItems(user);
        return "Successfully bought products";
    }
}
