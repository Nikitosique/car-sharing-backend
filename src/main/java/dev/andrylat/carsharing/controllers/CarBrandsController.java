package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarBrand;
import dev.andrylat.carsharing.services.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("carBrand", carBrandService.getById(id));
        return "carbrands/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("carBrand") CarBrand carBrand) {
        return "carbrands/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("carBrand") @Valid CarBrand carBrand,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "carbrands/add";

        CarBrand addedCarBrand = carBrandService.add(carBrand);
        return "redirect:/carbrands/" + addedCarBrand.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("carBrand", carBrandService.getById(id));
        return "carbrands/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("carBrand") @Valid CarBrand carBrand,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "carbrands/edit";

        carBrandService.updateById(carBrand);
        return "redirect:/carbrands/" + carBrand.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        carBrandService.deleteById(id);
        return "redirect:/carbrands";
    }

}
