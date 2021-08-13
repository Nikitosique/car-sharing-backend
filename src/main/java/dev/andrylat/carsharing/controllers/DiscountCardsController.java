package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.DiscountCard;
import dev.andrylat.carsharing.services.DiscountCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String getById(@PathVariable("id") long id,
                          Model model) {

        model.addAttribute("discountCard", discountCardService.getById(id));
        return "discountcards/getById";
    }

    @GetMapping("/add")
    public String showAdditionForm(@ModelAttribute("discountCard") DiscountCard discountCard) {
        return "discountcards/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("discountCard") @Valid DiscountCard discountCard,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "discountcards/add";

        DiscountCard addedDiscountCard = discountCardService.add(discountCard);
        return "redirect:/discountcards/" + addedDiscountCard.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("discountCard", discountCardService.getById(id));
        return "discountcards/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("discountCard") @Valid DiscountCard discountCard,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "discountcards/edit";

        discountCardService.updateById(discountCard);
        return "redirect:/discountcards/" + discountCard.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        discountCardService.deleteById(id);
        return "redirect:/discountcards";
    }

}
