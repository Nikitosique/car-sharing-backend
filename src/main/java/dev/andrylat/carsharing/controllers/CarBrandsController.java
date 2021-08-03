package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carbrands")
public class CarBrandsController {
    private final CarBrandService carBrandService;

    @Autowired
    public CarBrandsController(CarBrandService carBrandService) {
        this.carBrandService = carBrandService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("carBrands", carBrandService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", carBrandService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "carbrands/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("carBrand", carBrandService.getById(id));
        return "carbrands/getById";
    }

}
