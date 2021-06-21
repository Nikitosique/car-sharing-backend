package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @Autowired
    BodyTypeDAO bodyTypeDAO;

    @GetMapping("/hello")
    public String sayHello() {
        bodyTypeDAO.showAll();
        return "hello";
    }
}

