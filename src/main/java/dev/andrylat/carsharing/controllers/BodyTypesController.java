package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.dao.BodyTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bodytypes")
public class BodyTypesController {

    private final BodyTypeDAO bodyTypeDAO;

    @Autowired
    public BodyTypesController(BodyTypeDAO bodyTypeDAO) {
        this.bodyTypeDAO = bodyTypeDAO;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("bodyTypes", bodyTypeDAO.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", bodyTypeDAO.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "bodytypes/getAll";
    }

}