package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/users")
    public String getAllUsers(ModelMap model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }


    //AddUser
    @GetMapping("/users/add")
    public String showAddUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user, ModelMap model) {
        try {
            userService.addUser(user);
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid data. Please check your inputs.");
            return "addUser";
        }
    }


    //RemoveUser
    @GetMapping("/users/remove")
    public String showRemoveUserForm(ModelMap model) {
        model.addAttribute("searchCriteria", new User());
        return "removeUser";
    }

    @PostMapping("/users/remove/search")
    public String searchUsers(@ModelAttribute("searchCriteria") User searchCriteria, ModelMap model) {
        List<User> users = userService.searchUsers(searchCriteria);
        model.addAttribute("users", users);
        return "removeUser";
    }

    @GetMapping("/users/remove/delete")
    public String removeUserFromSearch(@RequestParam("id") Long userId) {
        userService.removeUser(userId);
        return "redirect:/users/remove";
    }


    //EditUser
//    @GetMapping("/users/edit")
//    public String editUser(ModelMap model) {
//        model.addAttribute("userList", userService.getAllUsers());
//        return "editUser2";
//    }
//    @PostMapping("/users/edit/editbutton")
//    public String copyChanges(ModelMap model) {
//        model.addAttribute("changedUser", new User());
//        return "editUser2";
//    }
//    @GetMapping("/users/edit/merge")
//    public String mergeChanges(@RequestParam("id") Long id, @RequestParam("changedUser") User changedUser) {
//        for(User user : userService.getAllUsers()) {
//            if (user.getId() == id) {
//                user = changedUser;
//                break;
//            }
//        }
//        return "editUser2";
//    }
//

    @GetMapping("/users/edit")
    public String showEditUserPage(ModelMap model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "editUser2"; // Переходим к editUser.html, где отображается таблица
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute("user") User user, ModelMap model) {
        User existingUser = userService.getUserById(user.getId());

        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getSecondName() != null && !user.getSecondName().isEmpty()) {
            existingUser.setSecondName(user.getSecondName());
        }
        if (user.getAge() > 0) { // Проверяем, чтобы возраст был положительным
            existingUser.setAge(user.getAge());
        }

        userService.editUser(existingUser);

        return "redirect:/users";
    }
}