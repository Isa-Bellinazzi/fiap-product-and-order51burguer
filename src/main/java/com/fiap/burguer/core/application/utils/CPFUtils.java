package com.fiap.burguer.core.application.utils;

public class CPFUtils {

    public static boolean isValidCPF(String cpf) {

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Cálculo do primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            // Cálculo do segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            sum += firstDigit * 2;
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            // Verifica se os dígitos verificadores são iguais aos calculados
            return cpf.charAt(9) == Character.forDigit(firstDigit, 10) && cpf.charAt(10) == Character.forDigit(secondDigit, 10);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
