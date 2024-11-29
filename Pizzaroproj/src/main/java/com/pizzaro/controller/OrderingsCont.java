package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Details;
import com.pizzaro.model.Orderings;
import com.pizzaro.model.Stats;
import com.pizzaro.model.Users;
import com.pizzaro.model.enums.Discount;
import com.pizzaro.model.enums.OrderingStatus;
import com.pizzaro.model.enums.PaymentType;
import com.pizzaro.repo.OrderingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orderings")
public class OrderingsCont extends Main {
    @Autowired
    private OrderingsRepo orderingsRepo;

    @GetMapping
    public String orderings(Model model) {
        AddAttributes(model);
        Users user = getUser();
        List<Orderings> orderings = orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.WAITING);
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.PROCESSED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.CONFIRMED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.PAID));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.DELIVERED));
        orderings.addAll(orderingsRepo.findAllByOwner_IdAndStatus(user.getId(), OrderingStatus.REJECTED));
        model.addAttribute("orderings", orderings);
        model.addAttribute("paymentTypes", PaymentType.values());
        return "orderings";
    }

    @GetMapping("/paid/{id}")
    public String orderingPaid(@PathVariable Long id) {
        Orderings ordering = orderingsRepo.getReferenceById(id);
        ordering.setStatus(OrderingStatus.PAID);
        Stats stat = ordering.getManager().getStats();
        stat.setPrice(stat.getPrice() + ordering.getPrice());
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }

    @PostMapping("/add")
    public String orderingAdd(@RequestParam String discount, @RequestParam PaymentType paymentType) {
        Users user = getUser();
        List<Details> details = user.getDetails();
        System.out.println(paymentType);
        Orderings ordering = new Orderings(user, paymentType);
        int quantity = 0;
        float price = 0;
        for (Details d : details) {
            d.setOwner(null);
            d.setOrdering(ordering);
            quantity += d.getQuantity();
            price += d.getPrice();
        }
        ordering.setQuantity(quantity);

        for (Discount i : Discount.values()) {
            if (i.name().equals(discount)) {
                price = (float) Math.round((float) (price * i.getDiscount()) * 100) / 100;
            }
        }
        ordering.setPrice(price);
        ordering.setDate(getDateNow());
        orderingsRepo.save(ordering);
        return "redirect:/orderings";
    }
}
