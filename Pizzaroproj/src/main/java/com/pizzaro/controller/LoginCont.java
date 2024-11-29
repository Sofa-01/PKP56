package com.pizzaro.controller;

import com.pizzaro.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginCont extends Main {

    @GetMapping
    public String login(Model model) {
        AddAttributes(model);
        return "login";
    }
}
