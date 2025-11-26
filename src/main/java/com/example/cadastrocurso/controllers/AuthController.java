package com.example.cadastrocurso.controllers;

import com.example.cadastrocurso.models.Role;
import com.example.cadastrocurso.models.User;
import com.example.cadastrocurso.models.dto.UserRegistrationDto;
import com.example.cadastrocurso.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/register")
  public String processRegister(@Valid @ModelAttribute("user") UserRegistrationDto dto, BindingResult result, Model model) {
    if (userRepository.existsByUsername(dto.getUsername())) {
      result.rejectValue("username", "username.exists", "Usuário já existe");
    }
    if (userRepository.existsByEmail(dto.getEmail())) {
      result.rejectValue("email", "email.exists", "Email já está em uso");
    }
    if (result.hasErrors()) {
      model.addAttribute("user", dto);
      return "register";
    }
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);
    return "redirect:/login?registered=true";
  }

  @PostMapping("/login")
  public String processLogin(@ModelAttribute("username") String username, @ModelAttribute("password") String password) {
    try {
      Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      SecurityContextHolder.getContext().setAuthentication(auth);
      return "redirect:/dashboard";
    } catch (BadCredentialsException ex) {
      return "redirect:/login?error=true";
    }
  }
}
