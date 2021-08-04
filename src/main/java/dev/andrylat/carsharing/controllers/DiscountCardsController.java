package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.DiscountCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/discountcards")
public class DiscountCardsController {

    private final DiscountCardService discountCardService;

    @Autowired
    public DiscountCardsController(DiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("discountCards", discountCardService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", discountCardService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "discountcards/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("discountCard", discountCardService.getById(id));
        return "discountcards/getById";
    }

}
