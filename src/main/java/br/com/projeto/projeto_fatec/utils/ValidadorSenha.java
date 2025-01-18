package br.com.projeto.projeto_fatec.utils;

import java.util.regex.Pattern;

public class ValidadorSenha {

    // Verificar a força da senha
    private static final String REGEX_SENHA_FORTE = "^(?=.*[0-9])" + // Pelo menos um número
            "(?=.*[a-z])" + // Pelo menos uma letra minúscula
            "(?=.*[A-Z])" + // Pelo menos uma letra maiúscula
            "(?=.*[@#$%^&+=!])" + // Pelo menos um caractere especial
            "(?=\\S+$).{8,}$"; // Pelo menos 8 caracteres sem espaços

    private static final Pattern PATTERN_SENHA_FORTE = Pattern.compile(REGEX_SENHA_FORTE);

    public static boolean senhaForte(String senha) {
        if (senha == null) {
            return false; // Senha não pode ser nula
        }
        return PATTERN_SENHA_FORTE.matcher(senha).matches();
    }
}

/*
 * -----------------------------------------------------------------------------
 * Explicação para a expressão do REGEX_SENHA_FORTE
 * -----------------------------------------------------------------------------
 * "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
 * -----------------------------------------------------------------------------
 * 1. ^ = Indica o início da string.
 * ---Objetivo: Garante que a verificação comece do início da senha.---
 * -----------------------------------------------------------------------------
 * 2. (?=.*[0-9])
 * (?=...): É uma lookahead positiva, ou seja, verifica se a condição dentro
 * dela é verdadeira, sem consumir os caracteres.
 * [0-9]: Qualquer dígito numérico de 0 a 9.
 * ---Objetivo: Garante que a senha contenha pelo menos um número.---
 * -----------------------------------------------------------------------------
 * 3. (?=.*[a-z])
 * Significado:
 * (?=...): Lookahead positiva.
 * [a-z]: Qualquer letra minúscula de 'a' a 'z'.
 * ---Objetivo: Garante que a senha contenha pelo menos uma letra minúscula.---
 * -----------------------------------------------------------------------------
 * 4. (?=.*[A-Z])
 * Significado:
 * (?=...): Lookahead positiva.
 * [A-Z]: Qualquer letra maiúscula de 'A' a 'Z'.
 * ---Objetivo: Garante que a senha contenha pelo menos uma letra maiúscula.---
 * -----------------------------------------------------------------------------
 * 5. (?=.*[@#$%^&+=!])
 * Significado:
 * (?=...): Lookahead positiva.
 * [@#$%^&+=!]: Qualquer caractere especial listado dentro dos colchetes.
 * ---Objetivo: Garante que a senha contenha pelo menos um caractere especial da
 * lista especificada.---
 * -----------------------------------------------------------------------------
 * 6. (?=\\S+$)
 * Significado:
 * (?=...): Lookahead positiva.
 * \\S+:
 * \\S: Qualquer caractere que não seja um espaço em branco (equivalente a [^
 * \t\n\r\f\v]).
 * +: Um ou mais desses caracteres consecutivos.
 * ---Objetivo: Garante que a senha não contenha espaços em branco.---
 * -----------------------------------------------------------------------------
 * 7. .{8,}$
 * Significado:
 * .: Qualquer caractere.
 * {8,}: Pelo menos 8 caracteres.
 * $: Fim da string.
 * ---Objetivo: Garante que a senha tenha no mínimo 8 caracteres de
 * comprimento.---
 * -----------------------------------------------------------------------------
 */