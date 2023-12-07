package com.allegro;

import com.mongodb.client.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class AllegroApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllegroApplication.class, args);
    }

    @RequestMapping("/hello")
    public ModelAndView sayHello(@RequestParam(value = "number", defaultValue = "1") int num) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello.html");
        modelAndView.addObject("num", num);
        modelAndView.addObject("even", isNumberEven(num));
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView connectToMongoDB(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        String connectionString = "mongodb://mongo:mypassword@localhost:27017/?authMechanism=SCRAM-SHA-256";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("pis");
            MongoCollection<Document> collection = database.getCollection("testing");
            String info = "";
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    info = info.concat(document.toString());
                    info = info.concat(" ");
                }
                modelAndView.addObject("info", info);
            }
        }
        return modelAndView;
    }

    static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.getDouble("ok").equals(1.0);
    }

    public static boolean isNumberEven(int number){return number%2==0;}

}
