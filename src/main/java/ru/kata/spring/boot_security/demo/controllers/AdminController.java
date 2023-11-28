package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RegistrationService registrationService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", admin);
        model.addAttribute("users", userService.allUsers());
        return "admin/admin";
    }

    @PostMapping("/read")
    public String readUser(@RequestParam("userId") Long userId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", admin);
        if (userId != null) {
            User user = userService.findUserById(userId);
            if (user != null) {
                List<User> users = new ArrayList<>();
                users.add(user);
                model.addAttribute("users", users);
                return "admin/admin";
            } else {
                model.addAttribute("error", "User not found");
                return "admin/readId";
            }
        } else {
            model.addAttribute("error", "User ID parameter is missing");
            return "admin/readId";
        }
    }

    @PostMapping("/limit")
    public String showLimitedUsersList(@RequestParam(value = "limit", required = false, defaultValue = "0") int limit,
                                Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        model.addAttribute("admin", admin);
        if (limit <= 0) {
            model.addAttribute("error", "The requested limit is less than 1 user");
            return "admin/limit";
        } else if ( limit > userService.allUsers().size()) {
            model.addAttribute("warning", "The total list is less that requested input. Here is the total list.");
        }
        model.addAttribute("users", userService.getUsersList(limit));
        return "admin/admin";
    }

    @GetMapping("/create-user")
    public String getCreationPage(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user,
                                  BindingResult bindingResult, Model model,
                                  @RequestParam(name = "roleAdmin", defaultValue = "false") boolean roleAdmin) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("passwordError", "Passwords mismatch");
            return "admin/new";
        }
        if (!registrationService.registerUser(user, roleAdmin)){
            return "admin/new";
        }
        return "redirect:/admin/admin";
    }

    @PostMapping("/edit-by-id")
    public String getEditingPage(@RequestParam("userId") Long userId, Model model) {
        if (userId != null) {
            User user = userService.findUserById(userId);
            if (user != null) {
                model.addAttribute("user", user);
                return "admin/edit";
            } else {
                model.addAttribute("error", "User not found");
                return "admin/editId";
            }
        } else {
            model.addAttribute("error", "User ID parameter is missing");
            return "admin/editId";
        }
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid  User editedUser,
                           BindingResult bindingResult, Model model,
                             @RequestParam(name = "roleAdmin", defaultValue = "false") boolean roleAdmin) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        if (!editedUser.getPassword().equals(editedUser.getPasswordConfirm())){
            model.addAttribute("passwordError", "Passwords mismatch");
            return "admin/edit";
        }
        registrationService.saveUser(editedUser, roleAdmin);
        return "redirect:/admin/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId, Model model) {

        if (userId != null) {
            User user = userService.findUserById(userId);
            if (user != null) {
//                model.addAttribute("not_error", "not_error");
                userService.deleteUser(userId);
                return "redirect:/admin/admin";
            } else {
                model.addAttribute("error", "User not found");
                return "admin/deleteId";
            }
        } else {
            model.addAttribute("error", "User ID parameter is missing");
            return "admin/deleteId";
        }
    }
}
