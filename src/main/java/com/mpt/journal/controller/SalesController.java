package com.mpt.journal.controller;

import com.mpt.journal.model.*;
import com.mpt.journal.service.*;
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
@RequestMapping("/admin/sales")
public class SalesController {
    private final SalesService salesService;
    private final UserService userService;
    private final PayService payService;
    private final LumberService lumberService;
    private static final int PAGE_SIZE = 10;

    public SalesController(SalesService salesService,
                           UserService userService,
                           PayService payService,
                           LumberService lumberService) {
        this.salesService = salesService;
        this.userService = userService;
        this.payService = payService;
        this.lumberService = lumberService;
    }

    @ModelAttribute("allEmployees")
    public List<UserModel> populateEmployees() {
        return userService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @ModelAttribute("allBuyers")
    public List<UserModel> populateBuyers() {
        return userService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @ModelAttribute("allPays")
    public List<PayModel> populatePays() {
        return payService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @ModelAttribute("allLumbers")
    public List<LumberModel> populateLumbers() {
        return lumberService.findAll(0, Integer.MAX_VALUE).getContent();
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "0") int page, Model model) {
        if (!model.containsAttribute("sale")) {
            model.addAttribute("sale", new SalesModel());
        }
        Page<SalesModel> result = salesService.findAll(page, PAGE_SIZE);
        model.addAttribute("salesList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        return "sales";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("sale") SalesModel sale,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sale", bindingResult);
            redirectAttributes.addFlashAttribute("sale", sale);
            return "redirect:/admin/sales";
        }
        try {
            // Загружаем полные сущности для связей
            if (sale.getEmployee() != null && sale.getEmployee().getId() != null) {
                Optional<UserModel> employee = userService.findById(sale.getEmployee().getId());
                employee.ifPresent(sale::setEmployee);
            }
            if (sale.getBuyer() != null && sale.getBuyer().getId() != null) {
                Optional<UserModel> buyer = userService.findById(sale.getBuyer().getId());
                buyer.ifPresent(sale::setBuyer);
            }
            if (sale.getPay() != null && sale.getPay().getId() != null) {
                Optional<PayModel> pay = payService.findById(sale.getPay().getId());
                pay.ifPresent(sale::setPay);
            }
            if (sale.getLumber() != null && sale.getLumber().getId() != null) {
                Optional<LumberModel> lumber = lumberService.findById(sale.getLumber().getId());
                lumber.ifPresent(sale::setLumber);
            }

            salesService.save(sale);
            redirectAttributes.addFlashAttribute("success", "Продажа успешно добавлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/admin/sales";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam Long employeeId,
                         @RequestParam Long buyerId,
                         @RequestParam Integer payId,
                         @RequestParam(required = false) Integer lumberId,
                         RedirectAttributes redirectAttributes) {
        try {
            SalesModel sale = salesService.findById(id).orElse(new SalesModel());
            sale.setId(id);

            Optional<UserModel> employee = userService.findById(employeeId);
            Optional<UserModel> buyer = userService.findById(buyerId);
            Optional<PayModel> pay = payService.findById(payId);
            Optional<LumberModel> lumber = lumberId != null ? lumberService.findById(lumberId) : Optional.empty();

            if (employee.isPresent() && buyer.isPresent() && pay.isPresent()) {
                sale.setEmployee(employee.get());
                sale.setBuyer(buyer.get());
                sale.setPay(pay.get());
                lumber.ifPresent(sale::setLumber);

                salesService.save(sale);
                redirectAttributes.addFlashAttribute("success", "Продажа успешно обновлена");
            } else {
                redirectAttributes.addFlashAttribute("error", "Сотрудник, покупатель или платеж не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/admin/sales";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        try {
            salesService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Продажа успешно удалена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/admin/sales";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) Integer id,
                         @RequestParam(required = false) Long employeeId,
                         @RequestParam(required = false) Long buyerId,
                         @RequestParam(required = false) Integer payId,
                         @RequestParam(required = false) Integer lumberId,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<SalesModel> result = salesService.search(
                id,
                employeeId,
                buyerId,
                payId,
                lumberId,
                page,
                PAGE_SIZE
        );
        model.addAttribute("salesList", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchId", id);
        model.addAttribute("searchEmployeeId", employeeId);
        model.addAttribute("searchBuyerId", buyerId);
        model.addAttribute("searchPayId", payId);
        model.addAttribute("searchLumberId", lumberId);
        return "searchSales";
    }
}