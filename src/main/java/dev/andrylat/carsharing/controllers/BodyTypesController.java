package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.BodyType;
import dev.andrylat.carsharing.services.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/bodytypes")
public class BodyTypesController {

    private final BodyTypeService bodyTypeService;

    @Autowired
    public BodyTypesController(BodyTypeService bodyTypeService) {
        this.bodyTypeService = bodyTypeService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {

        model.addAttribute("bodyTypes", bodyTypeService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", bodyTypeService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "bodytypes/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("bodyType", bodyTypeService.getById(id));
        return "bodytypes/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("bodyType") BodyType bodyType) {
        return "bodytypes/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bodyType") @Valid BodyType bodyType,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "bodytypes/add";

        BodyType addedBodyType = bodyTypeService.add(bodyType);
        return "redirect:/bodytypes/" + addedBodyType.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("bodyType", bodyTypeService.getById(id));
        return "bodytypes/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("bodyType") @Valid BodyType bodyType,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "bodytypes/edit";

        bodyTypeService.updateById(bodyType);
        return "redirect:/bodytypes/" + bodyType.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        bodyTypeService.deleteById(id);
        return "redirect:/bodytypes";
    }

}