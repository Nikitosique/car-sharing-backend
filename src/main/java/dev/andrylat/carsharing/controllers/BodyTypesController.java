package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.implementations.BodyTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bodytypes")
public class BodyTypesController {

    private final BodyTypeServiceImpl bodyTypeServiceImpl;

    @Autowired
    public BodyTypesController(BodyTypeServiceImpl bodyTypeServiceImpl) {
        this.bodyTypeServiceImpl = bodyTypeServiceImpl;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("bodyTypes", bodyTypeServiceImpl.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", bodyTypeServiceImpl.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "bodytypes/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("bodyType", bodyTypeServiceImpl.getById(id));
        return "bodytypes/getById";
    }

}