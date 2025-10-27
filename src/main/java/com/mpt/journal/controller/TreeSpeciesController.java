package com.mpt.journal.controller;

import com.mpt.journal.model.TreeSpeciesModel;
import com.mpt.journal.service.TreeSpeciesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/admin/tree-species")
public class TreeSpeciesController {
    private final TreeSpeciesService service;
    private static final int PAGE_SIZE = 10;

    public TreeSpeciesController(TreeSpeciesService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("treeSpecies")) {
            model.addAttribute("treeSpecies", new TreeSpeciesModel());
        }

        Page<TreeSpeciesModel> result = service.findAll(page, PAGE_SIZE);
        model.addAttribute("treeSpeciesList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "treeSpecies";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable Integer id, Model model) {
        Optional<TreeSpeciesModel> treeSpecies = service.findById(id);
        if (treeSpecies.isPresent()) {
            model.addAttribute("treeSpecies", treeSpecies.get());
        } else {
            model.addAttribute("error", "Вид дерева с ID " + id + " не найден");
        }
        return "treeSpeciesDetails";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("treeSpecies") TreeSpeciesModel treeSpecies,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.treeSpecies", bindingResult);
            redirectAttributes.addFlashAttribute("treeSpecies", treeSpecies);
            return "redirect:/admin/tree-species";
        }

        try {
            service.save(treeSpecies);
            redirectAttributes.addFlashAttribute("success", "Вид дерева успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/tree-species";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam String name,
                         @RequestParam String characteristics,
                         RedirectAttributes redirectAttributes) {
        try {
            TreeSpeciesModel model = new TreeSpeciesModel();
            model.setId(id);
            model.setName(name);
            model.setCharacteristics(characteristics);
            service.save(model);
            redirectAttributes.addFlashAttribute("success", "Вид дерева успешно обновлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/tree-species";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Вид дерева успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/tree-species";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<TreeSpeciesModel> result = service.search(id, name, page, PAGE_SIZE);
        model.addAttribute("treeSpeciesList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchName", name);
        return "searchTreeSpecies";
    }
}