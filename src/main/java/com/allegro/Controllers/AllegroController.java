package com.allegro.Controllers;

import com.allegro.Entity.Student;
import com.allegro.Service.StudentService;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AllegroController {


    @Autowired
    StudentService studentService;

    @RequestMapping("/hello")
    public ModelAndView sayHello(@RequestParam(value = "number", defaultValue = "1") int num) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello.html");
        modelAndView.addObject("num", num);
        modelAndView.addObject("even", isNumberEven(num));
        studentService.addStudent();
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView connectToMongoDB(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        String info = "";
        List<Student> listOfStudents = studentService.getStudents();
        for (Student student : listOfStudents
             ) {
            info = info.concat(student.toString());
            info = info.concat(",");
        }
        modelAndView.addObject("info", info);
        return modelAndView;
    }

    public static boolean isNumberEven(int number){return number%2==0;}

}
