package com.basic.validation;

import java.util.Map;
import java.util.regex.Pattern;

import com.basic.exception.ValidationException;

public final class RequestValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9()\\-\\s]{10,24}$");

    private RequestValidator() {
    }

    public static String requireText(Map<String, String> payload, String field, int minLength, int maxLength) {
        String value = payload.get(field);
        if (value == null) {
            throw new ValidationException("Required field: " + field + ".");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.length() < minLength || trimmedValue.length() > maxLength) {
            throw new ValidationException("Invalid field: " + field + ".");
        }
        return trimmedValue;
    }

    public static String requireEmail(Map<String, String> payload, String field) {
        String email = requireText(payload, field, 5, 120);
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Email invalid.");
        }
        return email.toLowerCase();
    }

    public static String requirePhone(Map<String, String> payload, String field) {
        String phone = requireText(payload, field, 8, 20);
        if (!PHONE_PATTERN.matcher(phone).matches() || !hasValidPhoneDigits(phone)) {
            throw new ValidationException("Cell-Phone invalid.");
        }
        return phone;
    }

    private static boolean hasValidPhoneDigits(String phone) {
        String digits = phone.replaceAll("\\D", "");
        if (digits.startsWith("55") && (digits.length() == 12 || digits.length() == 13)) {
            digits = digits.substring(2);
        }
        return digits.length() == 10 || digits.length() == 11;
    }

    public static int requireInt(Map<String, String> payload, String field, int minValue, int maxValue) {
        String rawValue = payload.get(field);
        if (rawValue == null) {
            throw new ValidationException("Required field: " + field + ".");
        }

        try {
            int parsedValue = Integer.parseInt(rawValue.trim());
            if (parsedValue < minValue || parsedValue > maxValue) {
                throw new ValidationException("Invalid field: " + field + ".");
            }
            return parsedValue;
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid field " + field + ".");
        }
    }

    public static double requireDecimal(Map<String, String> payload, String field, double minValue, double maxValue) {
        String rawValue = payload.get(field);
        if (rawValue == null) {
            throw new ValidationException("Required field: " + field + ".");
        }

        try {
            double parsedValue = Double.parseDouble(rawValue.trim());
            if (parsedValue < minValue || parsedValue > maxValue) {
                throw new ValidationException("Invalid field: " + field + ".");
            }
            return parsedValue;
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid field: " + field + ".");
        }
    }
}