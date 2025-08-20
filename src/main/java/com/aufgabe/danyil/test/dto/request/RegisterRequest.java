package com.aufgabe.danyil.test.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Can not be empty")
    @Email(message = "Incorrect format")
    private String email;

    @NotBlank(message = "Can not be empty")
    @Size(min = 6, message = "Minimal password size - 6 symbol")
    private String password;

    public RegisterRequest() {}

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}
