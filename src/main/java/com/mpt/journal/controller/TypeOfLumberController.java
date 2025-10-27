package com.mpt.journal.controller;

import com.mpt.journal.model.TypeOfLumberModel;
import com.mpt.journal.service.TypeOfLumberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/types-of-lumber")
public class TypeOfLumberController {
    private final TypeOfLumberService service;
    private static final int PAGE_SIZE = 10;

    public TypeOfLumberController(TypeOfLumberService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("typeOfLumber")) {
            model.addAttribute("typeOfLumber", new TypeOfLumberModel());
        }

        Page<TypeOfLumberModel> result = service.findAll(page, PAGE_SIZE);
        model.addAttribute("typesOfLumber", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "typesOfLumber";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("typeOfLumber") TypeOfLumberModel typeOfLumber,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.typeOfLumber", bindingResult);
            redirectAttributes.addFlashAttribute("typeOfLumber", typeOfLumber);
            return "redirect:/admin/types-of-lumber";
        }

        try {
            service.save(typeOfLumber);
            redirectAttributes.addFlashAttribute("success", "Тип успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/types-of-lumber";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String name,
                         @RequestParam String areaOfSpeciesion,
                         RedirectAttributes redirectAttributes) {
        try {
            TypeOfLumberModel model = new TypeOfLumberModel();
            model.setId(id);
            model.setName(name);
            model.setAreaOfSpeciesion(areaOfSpeciesion);
            service.save(model);
            redirectAttributes.addFlashAttribute("success", "Тип пиломатериала успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/types-of-lumber";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Тип успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/types-of-lumber";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<TypeOfLumberModel> result = service.search(id, name, area, page, PAGE_SIZE);
        model.addAttribute("typesOfLumber", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchName", name);
        model.addAttribute("searchArea", area);
        return "searchTypesOfLumber";
    }
}