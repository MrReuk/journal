package com.mpt.journal.controller;

import com.mpt.journal.model.RoleUsersModel;
import com.mpt.journal.service.RoleUsersService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
public class RoleUsersController {
    private final RoleUsersService service;
    private static final int PAGE_SIZE = 10;

    public RoleUsersController(RoleUsersService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("role")) {
            model.addAttribute("role", new RoleUsersModel());
        }

        Page<RoleUsersModel> result = service.findAll(page, PAGE_SIZE);
        model.addAttribute("roles", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "roles";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) String name,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<RoleUsersModel> result = service.search(id, name, page, PAGE_SIZE);
        model.addAttribute("roles", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchName", name);
        return "searchRoles";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("role") RoleUsersModel role,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", bindingResult);
            redirectAttributes.addFlashAttribute("role", role);
            return "redirect:/admin/roles";
        }

        try {
            service.save(role);
            redirectAttributes.addFlashAttribute("success", "Роль успешно добавлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam Integer id,
            @RequestParam String name,
            RedirectAttributes redirectAttributes) {

        try {
            RoleUsersModel role = new RoleUsersModel();
            role.setId(id);
            role.setName(name);
            service.save(role);
            redirectAttributes.addFlashAttribute("success", "Роль успешно обновлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Integer id,
            RedirectAttributes redirectAttributes) {

        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Роль успешно удалена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/roles";
    }
}