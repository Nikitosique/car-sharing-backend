package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                         Model model) {
        model.addAttribute("users", userService.getAll(pageNumber, pageSize));
        model.addAttribute("recordsNumber", userService.getRecordsNumber());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "users/getAll";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "users/getById";
    }

    @GetMapping("/managers/{id}")
    public String getCustomersIdByManagerId(@PathVariable("id") long managerId,
                                            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                            Model model) {
        model.addAttribute("customersId", userService.getCustomersIdByManagerId(managerId, pageNumber, pageSize));
        model.addAttribute("customersIdNumber", userService.getCustomersNumberByManagerId(managerId));
        model.addAttribute("managerId", managerId);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        return "users/getCustomersIdByManagerId";
    }

}
