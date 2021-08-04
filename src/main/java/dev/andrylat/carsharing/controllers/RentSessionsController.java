package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.RentSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("rentSession", rentSessionService.getById(id));
        return "rentsessions/getById";
    }

}
