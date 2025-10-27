package com.mpt.journal.controller;

import com.mpt.journal.model.*;
import com.mpt.journal.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/lumber")
public class LumberController {
    private final LumberService lumberService;
    private final TypeOfLumberService typeService;
    private final TreeSpeciesService speciesService;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public LumberController(LumberService lumberService,
                            TypeOfLumberService typeService,
                            TreeSpeciesService speciesService) {
        this.lumberService = lumberService;
        this.typeService = typeService;
        this.speciesService = speciesService;
    }

    @ModelAttribute("allTypes")
    public List<TypeOfLumberModel> populateTypes() {
        return typeService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @ModelAttribute("allSpecies")
    public List<TreeSpeciesModel> populateSpecies() {
        return speciesService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("lumber")) {
            model.addAttribute("lumber", new LumberModel());
        }

        Page<LumberModel> result = lumberService.findAll(page, PAGE_SIZE);
        model.addAttribute("lumberList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "lumber";
    }

    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable Integer id, Model model) {
        lumberService.findById(id).ifPresent(lumber -> model.addAttribute("lumber", lumber));
        return "lumberDetails";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("lumber") LumberModel lumber,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lumber", bindingResult);
            redirectAttributes.addFlashAttribute("lumber", lumber);
            return "redirect:/admin/lumber";
        }

        try {
            // Загружаем полные сущности для связей
            if (lumber.getTypeOfLumber() != null && lumber.getTypeOfLumber().getId() != null) {
                Optional<TypeOfLumberModel> type = typeService.findById(lumber.getTypeOfLumber().getId());
                type.ifPresent(lumber::setTypeOfLumber);
            }
            if (lumber.getTreeSpecies() != null && lumber.getTreeSpecies().getId() != null) {
                Optional<TreeSpeciesModel> species = speciesService.findById(lumber.getTreeSpecies().getId());
                species.ifPresent(lumber::setTreeSpecies);
            }

            lumberService.save(lumber);
            redirectAttributes.addFlashAttribute("success", "Пиломатериал успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/lumber";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam Long typeId,
                         @RequestParam Integer speciesId,
                         @RequestParam BigDecimal volume,
                         RedirectAttributes redirectAttributes) {
        try {
            LumberModel lumber = lumberService.findById(id).orElse(new LumberModel());
            lumber.setId(id);

            Optional<TypeOfLumberModel> type = typeService.findById(typeId);
            Optional<TreeSpeciesModel> species = speciesService.findById(speciesId);

            if (type.isPresent() && species.isPresent()) {
                lumber.setTypeOfLumber(type.get());
                lumber.setTreeSpecies(species.get());
                lumber.setVolume(volume);
                lumberService.save(lumber);
                redirectAttributes.addFlashAttribute("success", "Пиломатериал успешно обновлен");
            } else {
                redirectAttributes.addFlashAttribute("error", "Тип или вид не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/lumber";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            lumberService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Пиломатериал успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/lumber";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) Long typeId,
                         @RequestParam(required = false) Integer speciesId,
                         @RequestParam(required = false) BigDecimal volumeMin,
                         @RequestParam(required = false) BigDecimal volumeMax,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<LumberModel> result = lumberService.search(id, typeId, speciesId,
                volumeMin, volumeMax,
                page, PAGE_SIZE);
        model.addAttribute("lumberList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchTypeId", typeId);
        model.addAttribute("searchSpeciesId", speciesId);
        model.addAttribute("searchVolumeMin", volumeMin);
        model.addAttribute("searchVolumeMax", volumeMax);
        return "searchLumber";
    }
}