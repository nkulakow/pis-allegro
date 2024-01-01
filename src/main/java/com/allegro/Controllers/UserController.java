package com.allegro.Controllers;
import com.allegro.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.allegro.DTO.ProductDTO;
import com.allegro.Entity.Category;
import com.allegro.Entity.User;
import com.allegro.Service.UserService;
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
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/loginVerify")
    public ModelAndView login(@RequestParam  String email, @RequestParam String password, HttpSession session) {
        if (this.userService.login(email, password)) {
            session.setAttribute("login", email);
            session.setAttribute("password", password);
            return new ModelAndView("main-page.html");
        }
        return new ModelAndView("loginError.html");
    }
}
