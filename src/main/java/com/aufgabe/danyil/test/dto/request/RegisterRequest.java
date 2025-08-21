package com.aufgabe.danyil.test.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Incorrect format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Minimum password length is 6 characters")
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
