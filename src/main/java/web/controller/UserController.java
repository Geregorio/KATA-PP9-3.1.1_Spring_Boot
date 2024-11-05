package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/add")
    public String inputNewUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid data. Please check your inputs.");
            return "addUser";
        }
    }

    @GetMapping("/users/remove")
    public String inputParams(Model model) {
        model.addAttribute("searchParams", new User());
        return "removeUser";
    }
    @PostMapping("/users/remove/search")
    public String searchUsers(@ModelAttribute("searchParams") User searchParams, Model model) {
        List<User> userList = userService.searchUsersByParams(searchParams);
        model.addAttribute("users", userList);
        return "removeUser";
    }
    @DeleteMapping("/users/remove/delete")
    public String removeUserFromSearch(@RequestParam("id") Long id) {
        userService.removeUser(id);
        return "redirect:/users/remove";
    }

    @GetMapping("/users/edit")
    public String showEditUserPage(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "editUser";
    }
    @PostMapping("/users/edit")
    public String editUser(@RequestParam("firstName") String firstName,
                           @RequestParam("secondName") String secondName,
                           @RequestParam(value = "age", required = false, defaultValue = "0") Integer age,
                           @RequestParam("id") Long id) {
        User changedUser = userService.getUserById(id);
        changedUser.setFirstName(firstName);
        changedUser.setSecondName(secondName);
        changedUser.setAge(age);
        userService.editUser(changedUser);
        return "redirect:/users/edit";
    }
}