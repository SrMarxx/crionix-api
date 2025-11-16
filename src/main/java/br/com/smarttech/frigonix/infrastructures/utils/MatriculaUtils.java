package br.com.smarttech.frigonix.infrastructures.utils;

import java.util.regex.Pattern;

public final class MatriculaUtils {

    private static final Pattern MATRICULA_PATTERN = Pattern.compile("\\d{9}-\\d");

    private MatriculaUtils() {
        throw new IllegalStateException("Classe de utilidade não deve ser instanciada.");
    }

    public static String calcularDigitoVerificador(String numeroBase) {
        int soma = 0;
        int peso = 2;

        for (int i = numeroBase.length() - 1; i >= 0; i--) {
            int digito = Integer.parseInt(String.valueOf(numeroBase.charAt(i)));
            soma += digito * peso;

            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }

        int resto = soma % 11;
        int digitoVerificador = 11 - resto;

        if (digitoVerificador == 10 || digitoVerificador == 11) {
            return "0";
        }

        return String.valueOf(digitoVerificador);
    }

    public static boolean validarMatricula(String matriculaCompleta) {
        if (matriculaCompleta == null || !MATRICULA_PATTERN.matcher(matriculaCompleta).matches()) {
            return false;
        }

        String[] partes = matriculaCompleta.split("-");
        String numeroBase = partes[0];
        String digitoVerificadorInformado = partes[1];

        String digitoVerificadorCalculado = calcularDigitoVerificador(numeroBase);

        return digitoVerificadorCalculado.equals(digitoVerificadorInformado);
    }

}
