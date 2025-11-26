package com.example.cadastrocurso.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size(min = 8, max = 100)
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
      message = "A senha deve conter maiúscula, minúscula, número e caractere especial"
  )
  private String password;

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }
}
