package com.mpt.journal.controller;

import com.mpt.journal.model.UserModel;
import com.mpt.journal.service.RoleUsersService;
import com.mpt.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUsersService roleUsersService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(UserModel user, RedirectAttributes redirectAttributes) {
        if (userService.existsByLogin(user.getLogin())) {
            redirectAttributes.addFlashAttribute("error", "Логин уже существует");
            return "redirect:/register";
        }

        if (user.getPassword() == null || user.getPassword().length() < 3) {
            redirectAttributes.addFlashAttribute("error", "Пароль должен содержать минимум 3 символа");
            return "redirect:/register";
        }

        roleUsersService.findById(2).ifPresent(user::setRole);
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "Регистрация успешна");
        return "redirect:/login";
    }
}