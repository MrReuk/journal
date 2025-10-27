package com.mpt.journal.controller;

import com.mpt.journal.model.TypePayModel;
import com.mpt.journal.service.TypePayService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/type-pay")
public class TypePayController {
    private final TypePayService service;
    private static final int PAGE_SIZE = 10;

    public TypePayController(TypePayService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("typePay")) {
            model.addAttribute("typePay", new TypePayModel());
        }
        Page<TypePayModel> result = service.findAll(page, PAGE_SIZE);
        model.addAttribute("typesPay", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "typePay";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("typePay") TypePayModel typePay,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.typePay", bindingResult);
            redirectAttributes.addFlashAttribute("typePay", typePay);
            return "redirect:/admin/type-pay";
        }
        try {
            service.save(typePay);
            redirectAttributes.addFlashAttribute("success", "Тип оплаты успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/type-pay";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam String name,
                         RedirectAttributes redirectAttributes) {
        try {
            TypePayModel typePay = new TypePayModel();
            typePay.setId(id);
            typePay.setName(name);
            service.save(typePay);
            redirectAttributes.addFlashAttribute("success", "Тип оплаты успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/type-pay";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Тип оплаты успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/type-pay";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) String name,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<TypePayModel> result = service.search(id, name, page, PAGE_SIZE);
        model.addAttribute("typesPay", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchName", name);
        return "searchTypePay";
    }
}