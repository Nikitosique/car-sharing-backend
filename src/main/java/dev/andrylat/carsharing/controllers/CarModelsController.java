package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.CarModel;
import dev.andrylat.carsharing.services.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("carModel", carModelService.getById(id));
        return "carmodels/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("carModel") CarModel carModel) {
        return "carmodels/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("carModel") @Valid CarModel carModel,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "carmodels/add";

        CarModel addedCarModel = carModelService.add(carModel);
        return "redirect:/carmodels/" + addedCarModel.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("carModel", carModelService.getById(id));
        return "carmodels/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("carModel") @Valid CarModel carModel,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "carmodels/edit";

        carModelService.updateById(carModel);
        return "redirect:/carmodels/" + carModel.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        carModelService.deleteById(id);
        return "redirect:/carmodels";
    }

}
