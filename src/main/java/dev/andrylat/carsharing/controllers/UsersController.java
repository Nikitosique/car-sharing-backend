package dev.andrylat.carsharing.controllers;

import dev.andrylat.carsharing.models.User;
import dev.andrylat.carsharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/add")
    public String showAdditionPage() {
        return "users/add";
    }

    @GetMapping("/add/customer")
    public String showCustomerAdditionForm(@ModelAttribute("user") User user) {
        return "users/addCustomer";
    }

    @GetMapping("/add/manager")
    public String showManagerAdditionForm(@ModelAttribute("user") User user) {
        return "users/addManager";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid User user,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String userType = user.getType();

            if ("manager".equals(userType)) {
                return "users/addManager";
            } else {
                return "users/addCustomer";
            }
        }

        User addedUser = userService.add(user);
        return "redirect:/users/" + addedUser.getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdatingForm(@PathVariable("id") long id,
                                   Model model) {

        model.addAttribute("user", userService.getById(id));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String updateById(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "users/edit";

        userService.updateById(user);
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
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

    @GetMapping("/managers/assign")
    public String showAssignmentForm() {
        return "users/assignCustomerToManager";
    }

    @PostMapping("/managers/assign")
    public String assignCustomerToManager(@RequestParam(name = "customerId") Long customerId,
                                          @RequestParam(name = "managerId") Long managerId) {

        userService.assignCustomerToManager(customerId, managerId);
        return "redirect:/users/managers/" + managerId;
    }

    @GetMapping("/managers/unassign")
    public String showUnassignmentForm() {
        return "users/unassignCustomerFromManager";
    }

    @PostMapping("/managers/unassign")
    public String unAssignCustomerFromManager(@RequestParam(name = "customerId") Long customerId,
                                              @RequestParam(name = "managerId") Long managerId) {

        userService.unassignCustomerFromManager(customerId, managerId);
        return "redirect:/users/managers/" + managerId;
    }

}
