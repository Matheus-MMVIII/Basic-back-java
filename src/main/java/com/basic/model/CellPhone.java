package com.basic.model;

public class CellPhone {
    private final String number;

    public CellPhone(String number) {
        if (!validateNumber(number))
            throw new IllegalArgumentException("Invalid cell phone number");
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    private boolean validateNumber(String number) {
        return number != null && number.matches("^\\+?[1-9]\\d{1,14}$"); // Basic validation for international phone numbers
    }
    
}
