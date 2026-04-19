package com.basic.model;

public class Email {
    private final String email;

    public Email(String email) {
        if (!validateEmail(email))
            throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    private boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}