package com.mpt.journal.controller;

import com.mpt.journal.model.UserModel;
import com.mpt.journal.model.RoleUsersModel;
import com.mpt.journal.service.RoleUsersService;
import com.mpt.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService service;
    private final RoleUsersService roleService;
    private static final int PAGE_SIZE = 10;

    public UserController(UserService service, RoleUsersService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @ModelAttribute("allRoles")
    public List<RoleUsersModel> populateRoles() {
        return roleService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserModel());
        }
        Page<UserModel> result = service.findAll(page, PAGE_SIZE);
        model.addAttribute("users", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "users";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("user") UserModel user,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/admin/users";
        }
        try {
            // Загружаем полную сущность роли
            if (user.getRole() != null && user.getRole().getId() != null) {
                Optional<RoleUsersModel> role = roleService.findById(user.getRole().getId());
                role.ifPresent(user::setRole);
            }
            service.save(user);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String surname,
                         @RequestParam String name,
                         @RequestParam(required = false) String middleName,
                         @RequestParam(required = false) Integer roleId,
                         @RequestParam String login,
                         @RequestParam String password,
                         RedirectAttributes redirectAttributes) {
        try {
            UserModel user = service.findById(id).orElse(new UserModel());
            user.setId(id);
            user.setSurname(surname);
            user.setName(name);
            user.setMiddleName(middleName);
            user.setLogin(login);
            user.setPassword(password);

            if (roleId != null) {
                Optional<RoleUsersModel> role = roleService.findById(roleId);
                role.ifPresent(user::setRole);
            } else {
                user.setRole(null);
            }

            service.save(user);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Long id,
                         @RequestParam(required = false) String surname,
                         @RequestParam(required = false) Integer roleId,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<UserModel> result = service.search(
                id != null ? id : null,
                surname,
                roleId != null ? roleId : null,
                page,
                PAGE_SIZE
        );
        model.addAttribute("users", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchSurname", surname);
        model.addAttribute("searchRoleId", roleId);
        return "searchUsers";
    }
}