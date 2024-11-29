package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Orderings;
import com.pizzaro.model.enums.OrderingStatus;
import com.pizzaro.repo.OrderingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/apps")
public class AppsCont extends Main {

    @Autowired
    private OrderingsRepo orderingsRepo;

    @GetMapping
    public String apps(Model model) {
        AddAttributes(model);
        model.addAttribute("orderings", orderingsRepo.findAllByStatus(OrderingStatus.WAITING));
        return "apps";
    }

    @GetMapping("/my")
    public String appsMy(Model model) {
        AddAttributes(model);
        List<Orderings> orderings = orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), OrderingStatus.PROCESSED);
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), OrderingStatus.CONFIRMED));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), OrderingStatus.PAID));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), OrderingStatus.DELIVERED));
        orderings.addAll(orderingsRepo.findAllByManager_IdAndStatus(getUser().getId(), OrderingStatus.REJECTED));
        model.addAttribute("orderings", orderings);
        return "apps_my";
    }

    @GetMapping("/confirmed/{id}")
    public String appsConfirmed(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.CONFIRMED);
        ordering.setDateStart(getDateNow());
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/rejected/{id}")
    public String appsRejected(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.REJECTED);
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/delivered/{id}")
    public String appsDelivered(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.getManager().getStats().setQuantity(ordering.getManager().getStats().getQuantity() + 1);
        ordering.setStatus(OrderingStatus.DELIVERED);
        ordering.setDateFinish(getDateNow());
        orderingsRepo.save(ordering);
        return "redirect:/apps/my";
    }

    @GetMapping("/processing/{id}")
    public String appProcessing(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.PROCESSED);
        ordering.setManager(getUser());
        orderingsRepo.save(ordering);
        return "redirect:/apps";
    }
}