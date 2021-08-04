package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fueltypes")
public class FuelTypesController {

    private final FuelTypeService fuelTypeService;

    @Autowired
    public FuelTypesController(FuelTypeService fuelTypeService) {
        this.fuelTypeService = fuelTypeService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("fuelTypes", fuelTypeService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", fuelTypeService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "fueltypes/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("fuelType", fuelTypeService.getById(id));
        return "fueltypes/getById";
    }

}
