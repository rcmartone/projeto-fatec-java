package br.com.projeto.projeto_fatec.utils;

public class ValidadorTelefone {
    public static void telefoneValido(String telefone) {
        if (telefone == null || !telefone.matches("\\d{11}")) {
            throw new IllegalArgumentException("Senha não atende aos requisitos mínimos.");
        }
    }
}
