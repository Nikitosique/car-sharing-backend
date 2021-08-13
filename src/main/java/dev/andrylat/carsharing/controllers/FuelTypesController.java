package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.FuelType;
import dev.andrylat.carsharing.services.FuelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("fuelType", fuelTypeService.getById(id));
        return "fueltypes/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("fuelType") FuelType fuelType) {
        return "fueltypes/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("fuelType") @Valid FuelType fuelType,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "fueltypes/add";

        FuelType addedBodyType = fuelTypeService.add(fuelType);
        return "redirect:/fueltypes/" + addedBodyType.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("fuelType", fuelTypeService.getById(id));
        return "fueltypes/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("fuelType") @Valid FuelType fuelType,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "fueltypes/edit";

        fuelTypeService.updateById(fuelType);
        return "redirect:/fueltypes/" + fuelType.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        fuelTypeService.deleteById(id);
        return "redirect:/fueltypes";
    }

}
