package com.basic.model;

public class Cpf {
    private static final int[] weightCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private final String cpf;

    public Cpf(String cpf) {
        if (!validateCpf(cpf))
            throw new IllegalArgumentException("Invalid CPF");
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    private static int calculateDigit(String str, int[] weight) { // Calculate the check digit based on the provided string and weight array
        int sum = 0;
        for (int indice=str.length()-1, digit; indice >= 0; indice-- ) { // Loop through the string from the end to the beginning
            digit = Integer.parseInt(str.substring(indice,indice+1));
            sum += digit*weight[weight.length-str.length()+indice];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    private static String padLeft(String text, char character) {
        return String.format("%11s", text).replace(' ', character); // Pad with the specified character to the left until the string is 11 characters long
    }

    public boolean validateCpf(String cpf) {
        cpf = cpf.trim() // Remove spaces
            .replace(".", "") // Remove points
            .replace("-", ""); //Remove hyphen
        if ((cpf==null) || (cpf.length()!=11)) return false; // Basic check for null and length

        for (int j = 0; j < 10; j++)
            if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(cpf)) // Check for sequences of the same digit (e.g., "00000000000", "11111111111", etc.)
                return false;

        Integer digit1 = calculateDigit(cpf.substring(0,9), weightCPF);
        Integer digit2 = calculateDigit(cpf.substring(0,9) + digit1, weightCPF);
        return cpf.equals(cpf.substring(0,9) + digit1.toString() + digit2.toString()); // Check if the calculated digits match the last two digits of the CPF
    }
}