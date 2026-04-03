package com.sammid37.health_person.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CpfValido, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) return true; // @NotBlank trata campo vazio

        String digits = cpf.replaceAll("\\D", "");

        if (digits.length() != 11) return false;
        if (digits.matches("(\\d)\\1{10}")) return false; // sequências como 111.111.111-11

        int firstDigit = calcDigit(digits, 10);
        if (firstDigit != Character.getNumericValue(digits.charAt(9))) return false;

        int secondDigit = calcDigit(digits, 11);
        return secondDigit == Character.getNumericValue(digits.charAt(10));
    }

    private int calcDigit(String digits, int factor) {
        int sum = 0;
        for (int i = 0; i < factor - 1; i++) {
            sum += Character.getNumericValue(digits.charAt(i)) * (factor - i);
        }
        int remainder = (sum * 10) % 11;
        return remainder >= 10 ? 0 : remainder;
    }
}
