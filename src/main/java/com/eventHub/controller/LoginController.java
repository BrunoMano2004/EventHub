package com.eventHub.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        session.setAttribute("user", "exampleUser");
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute("user") String user, Model model) {
        model.addAttribute("username", user);
        return "dashboard";
    }
}
