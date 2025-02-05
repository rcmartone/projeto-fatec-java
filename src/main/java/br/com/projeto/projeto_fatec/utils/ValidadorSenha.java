package br.com.projeto.projeto_fatec.utils;

import java.util.regex.Pattern;

public class ValidadorSenha {

    private static final String REGEX_SENHA_FORTE = "^(?=.*[0-9])" + // Pelo menos um número
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=!])" +
            "(?=\\S+$).{8,}$";

    private static final Pattern PATTERN_SENHA_FORTE = Pattern.compile(REGEX_SENHA_FORTE);

    public static void senhaForte(String senha) {
        if (senha == null || !PATTERN_SENHA_FORTE.matcher(senha).matches()) {
            throw new IllegalArgumentException("Senha não atende aos requisitos mínimos.");
        }
    }
}
