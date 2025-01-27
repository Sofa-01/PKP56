package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import com.pizzaro.model.Users;
import com.pizzaro.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegCont extends Main {

    @GetMapping
    public String reg() {
        return "reg";
    }

    @PostMapping
    public String regUser(Model model, @RequestParam String username, @RequestParam String password) {
        if (usersRepo.findAll().isEmpty()) {
            usersRepo.save(new Users(Role.ADMIN, username, password));
            return "redirect:/login";
        }

        if (usersRepo.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            AddAttributes(model);
            return "reg";
        }

        usersRepo.save(new Users(Role.CLIENT, username, password));

        return "redirect:/login";
    }
}
