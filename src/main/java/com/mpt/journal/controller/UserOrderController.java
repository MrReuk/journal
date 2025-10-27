package com.mpt.journal.controller;

import com.mpt.journal.model.*;
import com.mpt.journal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@SessionAttributes({"savedLumberId", "savedLumber"})
public class UserOrderController {

    private final SalesService salesService;
    private final UserService userService;
    private final LumberService lumberService;
    private final TypeOfLumberService typeService;
    private final TreeSpeciesService speciesService;
    private final TypePayService typePayService;
    private final PayService payService;

    @Autowired
    public UserOrderController(SalesService salesService, UserService userService,
                               LumberService lumberService, TypeOfLumberService typeService,
                               TreeSpeciesService speciesService, TypePayService typePayService,
                               PayService payService) {
        this.salesService = salesService;
        this.userService = userService;
        this.lumberService = lumberService;
        this.typeService = typeService;
        this.speciesService = speciesService;
        this.typePayService = typePayService;
        this.payService = payService;
    }

    @GetMapping("/orders")
    public String getUserOrders(Authentication authentication, Model model) {
        String username = authentication.getName();
        UserModel user = userService.findByLogin(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<SalesModel> userOrders = salesService.findByBuyerId(user.getId());
        model.addAttribute("orders", userOrders);
        return "user-orders";
    }

    @GetMapping("/order")
    public String showOrderForm(Model model) {
        if (!model.containsAttribute("lumber")) {
            model.addAttribute("lumber", new LumberModel());
        }
        model.addAttribute("allTypes", typeService.findAll(0, 100).getContent());
        model.addAttribute("allSpecies", speciesService.findAll(0, 100).getContent());
        return "order-lumber";
    }

    @PostMapping("/order")
    public String processOrder(@ModelAttribute LumberModel lumber,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        try {
            if (lumber.getTypeOfLumber() == null || lumber.getTypeOfLumber().getId() == null) {
                redirectAttributes.addFlashAttribute("error", "Выберите тип пиломатериала");
                redirectAttributes.addFlashAttribute("lumber", lumber);
                return "redirect:/user/order";
            }

            if (lumber.getTreeSpecies() == null || lumber.getTreeSpecies().getId() == null) {
                redirectAttributes.addFlashAttribute("error", "Выберите вид дерева");
                redirectAttributes.addFlashAttribute("lumber", lumber);
                return "redirect:/user/order";
            }

            if (lumber.getVolume() == null || lumber.getVolume().compareTo(BigDecimal.ZERO) <= 0) {
                redirectAttributes.addFlashAttribute("error", "Укажите объем больше 0");
                redirectAttributes.addFlashAttribute("lumber", lumber);
                return "redirect:/user/order";
            }

            Optional<TypeOfLumberModel> type = typeService.findById(lumber.getTypeOfLumber().getId());
            Optional<TreeSpeciesModel> species = speciesService.findById(lumber.getTreeSpecies().getId());

            if (!type.isPresent() || !species.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Ошибка: выбранный тип или вид не найден");
                redirectAttributes.addFlashAttribute("lumber", lumber);
                return "redirect:/user/order";
            }

            lumber.setTypeOfLumber(type.get());
            lumber.setTreeSpecies(species.get());

            LumberModel savedLumber = lumberService.save(lumber);

            model.addAttribute("savedLumberId", savedLumber.getId());
            model.addAttribute("savedLumber", savedLumber);

            return "redirect:/user/order/payment";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании заказа: " + e.getMessage());
            redirectAttributes.addFlashAttribute("lumber", lumber);
            return "redirect:/user/order";
        }
    }

    @GetMapping("/order/payment")
    public String showPaymentForm(Model model, Authentication authentication) {
        if (!model.containsAttribute("savedLumberId") || !model.containsAttribute("savedLumber")) {
            model.addAttribute("error", "Сначала выберите пиломатериалы");
            return "redirect:/user/order";
        }

        model.addAttribute("pay", new PayModel());
        model.addAttribute("typePays", typePayService.findAll(0, 100).getContent());

        return "order-payment";
    }

    @PostMapping("/order/payment")
    public String processPayment(@ModelAttribute PayModel pay,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes,
                                 @ModelAttribute("savedLumberId") Integer savedLumberId,
                                 @ModelAttribute("savedLumber") LumberModel savedLumber,
                                 Model model) {
        try {
            String username = authentication.getName();
            UserModel user = userService.findByLogin(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

            if (savedLumberId == null || savedLumber == null) {
                redirectAttributes.addFlashAttribute("error", "Ошибка: данные заказа утеряны. Пожалуйста, начните заказ заново.");
                return "redirect:/user/order";
            }

            if (pay.getTypePay() == null || pay.getTypePay().getId() == null) {
                redirectAttributes.addFlashAttribute("error", "Выберите способ оплаты");
                redirectAttributes.addFlashAttribute("pay", pay);
                return "redirect:/user/order/payment";
            }

            if (pay.getPrice() == null || pay.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                redirectAttributes.addFlashAttribute("error", "Укажите сумму оплаты больше 0");
                redirectAttributes.addFlashAttribute("pay", pay);
                return "redirect:/user/order/payment";
            }

            Optional<TypePayModel> typePay = typePayService.findById(pay.getTypePay().getId());
            if (!typePay.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Ошибка: выбранный тип оплаты не найден");
                redirectAttributes.addFlashAttribute("pay", pay);
                return "redirect:/user/order/payment";
            }

            pay.setBuyer(user);
            pay.setTypePay(typePay.get());

            PayModel savedPay = payService.save(pay);

            SalesModel sale = new SalesModel();
            sale.setBuyer(user);
            sale.setPay(savedPay);
            sale.setLumber(savedLumber);

            UserModel admin = userService.findByLogin("admin")
                    .orElseThrow(() -> new RuntimeException("Администратор не найден"));
            sale.setEmployee(admin);

            salesService.save(sale);

            model.addAttribute("savedLumberId", null);
            model.addAttribute("savedLumber", null);

            redirectAttributes.addFlashAttribute("success", "Заказ успешно оформлен!");
            return "redirect:/user/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при оплате: " + e.getMessage());
            redirectAttributes.addFlashAttribute("pay", pay);
            return "redirect:/user/order/payment";
        }
    }
}