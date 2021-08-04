package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carmodels")
public class CarModelsController {

    private final CarModelService carModelService;

    @Autowired
    public CarModelsController(CarModelService carModelService) {
        this.carModelService = carModelService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("carModels", carModelService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", carModelService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "carmodels/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("carModel", carModelService.getById(id));
        return "carmodels/getById";
    }

}
