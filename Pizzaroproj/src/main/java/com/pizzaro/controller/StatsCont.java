package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Stats;
import com.pizzaro.repo.OrderingsRepo;
import com.pizzaro.repo.StatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Main {
    @Autowired
    private OrderingsRepo orderingsRepo;
    @Autowired
    private StatsRepo statsRepo;
    @GetMapping
    public String Stats(Model model) {
        AddAttributes(model);
        List<Stats> stats = statsRepo.findAll();
        stats.sort(Comparator.comparing(Stats::getPrice));
        Collections.reverse(stats);
        model.addAttribute("stats", stats);

        float[] topPriceNumber = new float[5];
        String[] topPriceName = new String[5];
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topPriceName[i] = stats.get(i).getManager().getUsername();
            topPriceNumber[i] = stats.get(i).getPrice();
        }
        model.addAttribute("topPriceName", topPriceName);
        model.addAttribute("topPriceNumber", topPriceNumber);

        stats.sort(Comparator.comparing(Stats::getQuantity));
        Collections.reverse(stats);
        String[] topQuantityName = new String[5];
        int[] topQuantityNumber = new int[5];
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topQuantityName[i] = stats.get(i).getManager().getUsername();
            topQuantityNumber[i] = stats.get(i).getQuantity();
        }
        model.addAttribute("topQuantityName", topQuantityName);
        model.addAttribute("topQuantityNumber", topQuantityNumber);
        return "stats";
    }

    @GetMapping("/{id}")
    public String AppsStats(Model model, @PathVariable Long id) {
        AddAttributes(model);
        model.addAttribute("orderings", orderingsRepo.findAllByManager_Id(id));
        return "apps_stats";
    }
}
