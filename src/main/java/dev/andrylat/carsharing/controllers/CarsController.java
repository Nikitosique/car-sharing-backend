package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.Car;
import dev.andrylat.carsharing.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarsController {
    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {

        model.addAttribute("cars", carService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", carService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "cars/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carService.getById(id));
        return "cars/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("car") Car car) {
        return "cars/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("car") @Valid Car car,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "cars/add";

        Car addedCar = carService.add(car);
        return "redirect:/cars/" + addedCar.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("car", carService.getById(id));
        return "cars/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("car") @Valid Car car,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "cars/edit";

        carService.updateById(car);
        return "redirect:/cars/" + car.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

}
