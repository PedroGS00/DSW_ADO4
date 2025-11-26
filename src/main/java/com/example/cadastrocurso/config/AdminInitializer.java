package com.example.cadastrocurso.config;

import com.example.cadastrocurso.models.Role;
import com.example.cadastrocurso.models.User;
import com.example.cadastrocurso.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

import java.util.Collections;

@Configuration
public class AdminInitializer {

  @Bean
  public CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder encoder) {
    return args -> {
      String username = envOrDefault("APP_ADMIN_USERNAME", "admin");
      String email = envOrDefault("APP_ADMIN_EMAIL", "admin@example.com");
      String rawPassword = envOrDefault("APP_ADMIN_PASSWORD", "Admin@123456");

      if (!userRepository.existsByUsername(username)) {
        User admin = new User();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPassword(encoder.encode(rawPassword));
        admin.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(admin);
      }
    };
  }

  private String envOrDefault(String key, String def) {
    String v = System.getenv(key);
    return v == null || v.isEmpty() ? def : v;
  }
}

