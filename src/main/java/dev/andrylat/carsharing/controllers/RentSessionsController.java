package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.RentSession;
import dev.andrylat.carsharing.services.RentSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/rentsessions")
public class RentSessionsController {

    private final RentSessionService rentSessionService;

    @Autowired
    public RentSessionsController(RentSessionService rentSessionService) {
        this.rentSessionService = rentSessionService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {

        model.addAttribute("rentSessions", rentSessionService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", rentSessionService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        return "rentsessions/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("rentSession", rentSessionService.getById(id));
        return "rentsessions/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("rentSession") RentSession rentSession) {
        return "rentsessions/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("rentSession") @Valid RentSession rentSession,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "rentsessions/add";

        RentSession addedRentSession = rentSessionService.add(rentSession);
        return "redirect:/rentsessions/" + addedRentSession.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("rentSession", rentSessionService.getById(id));
        return "rentsessions/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("rentSession") @Valid RentSession rentSession,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "rentsessions/edit";

        rentSessionService.updateById(rentSession);
        return "redirect:/rentsessions/" + rentSession.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        rentSessionService.deleteById(id);
        return "redirect:/rentsessions";
    }

}
