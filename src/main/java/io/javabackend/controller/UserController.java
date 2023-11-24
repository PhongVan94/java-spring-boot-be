package io.javabackend.controller;

import io.javabackend.entity.User;
import io.javabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserPage(Model model) {
        model.addAttribute("users", userService.getUserList());

        return "user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userService.deleteUserById(id);
        return "redirect:/user";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute User user){
        userService.addUser(user);
        return "redirect:/user";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model){
        User user = userService.getUserById(id);
        if (user != null){
            model.addAttribute("user",user);
            return "user-update";
        }
        return "user";
    }
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user){
        userService.updateUser(user);
        return "redirect:/user";
    }


}
