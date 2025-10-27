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
@RequestMapping("/admin/pay")
public class PayController {
    private final PayService payService;
    private final TypePayService typePayService;
    private final UserService userService;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public PayController(PayService payService, TypePayService typePayService, UserService userService) {
        this.payService = payService;
        this.typePayService = typePayService;
        this.userService = userService;
    }

    @ModelAttribute("allTypePays")
    public List<TypePayModel> populateTypePays() {
        return typePayService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @ModelAttribute("allBuyers")
    public List<UserModel> populateBuyers() {
        return userService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("pay")) {
            model.addAttribute("pay", new PayModel());
        }
        Page<PayModel> result = payService.findAll(page, PAGE_SIZE);
        model.addAttribute("pays", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "pay";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("pay") PayModel pay,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pay", bindingResult);
            redirectAttributes.addFlashAttribute("pay", pay);
            return "redirect:/admin/pay";
        }

        try {
            // Загружаем полные сущности для связей
            if (pay.getTypePay() != null && pay.getTypePay().getId() != null) {
                Optional<TypePayModel> typePay = typePayService.findById(pay.getTypePay().getId());
                typePay.ifPresent(pay::setTypePay);
            }
            if (pay.getBuyer() != null && pay.getBuyer().getId() != null) {
                Optional<UserModel> buyer = userService.findById(pay.getBuyer().getId());
                buyer.ifPresent(pay::setBuyer);
            }

            payService.save(pay);
            redirectAttributes.addFlashAttribute("success", "Платеж успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/pay";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam Integer typePayId,
                         @RequestParam Long buyerId,
                         @RequestParam BigDecimal price,
                         RedirectAttributes redirectAttributes) {
        try {
            PayModel pay = payService.findById(id).orElse(new PayModel());
            pay.setId(id);

            Optional<TypePayModel> typePay = typePayService.findById(typePayId);
            Optional<UserModel> buyer = userService.findById(buyerId);

            if (typePay.isPresent() && buyer.isPresent()) {
                pay.setTypePay(typePay.get());
                pay.setBuyer(buyer.get());
                pay.setPrice(price);
                payService.save(pay);
                redirectAttributes.addFlashAttribute("success", "Платеж успешно обновлен");
            } else {
                redirectAttributes.addFlashAttribute("error", "Тип оплаты или покупатель не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/pay";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            payService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Платеж успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/pay";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) Integer typePayId,
                         @RequestParam(required = false) Long buyerId,
                         @RequestParam(required = false) BigDecimal priceMin,
                         @RequestParam(required = false) BigDecimal priceMax,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<PayModel> result = payService.search(
                id, typePayId, buyerId, priceMin, priceMax, page, PAGE_SIZE
        );
        model.addAttribute("pays", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchTypePayId", typePayId);
        model.addAttribute("searchBuyerId", buyerId);
        model.addAttribute("searchPriceMin", priceMin);
        model.addAttribute("searchPriceMax", priceMax);
        return "searchPay";
    }
}