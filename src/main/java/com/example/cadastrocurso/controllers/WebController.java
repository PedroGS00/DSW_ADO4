package com.example.cadastrocurso.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.cadastrocurso.models.dto.UserRegistrationDto;

@Controller
public class WebController {

  @GetMapping("/")
  public String index() { return "index"; }

  @GetMapping("/login")
  public String login() { return "login"; }

  @GetMapping("/register")
  public String register(Model model) { 
    model.addAttribute("user", new UserRegistrationDto());
    return "register"; 
  }

  @GetMapping("/dashboard")
  public String dashboard() { return "dashboard"; }

  @GetMapping("/profile")
  public String profile() { return "profile"; }

  @GetMapping("/admin")
  public String admin() { return "admin"; }
}
