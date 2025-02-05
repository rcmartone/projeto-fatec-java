package br.com.projeto.projeto_fatec.utils;

public class ValidadorValoresObrigatorios {
    public static void verificacaValoresObrigatorios(String... args) throws IllegalArgumentException {
        for (String valor : args) {
            if (valor == null || valor.isBlank()) {
                throw new IllegalArgumentException("Campos obrigatórios nulos ou inválidos");
            }
        }
    }
}
