package br.com.projeto.projeto_fatec.utils;

import java.util.regex.Pattern;

public class Validator {

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

    public static boolean cpfValido(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; // CPF nulo, com tamanho inválido ou com todos os dígitos iguais
        }

        try {
            // Cálculo do primeiro dígito verificador
            int soma1 = 0;
            for (int i = 0; i < 9; i++) {
                soma1 += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = 11 - (soma1 % 11);
            digito1 = (digito1 >= 10) ? 0 : digito1;

            // Cálculo do segundo dígito verificador
            int soma2 = 0;
            for (int i = 0; i < 10; i++) {
                soma2 += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = 11 - (soma2 % 11);
            digito2 = (digito2 >= 10) ? 0 : digito2;

            // Comparação dos dígitos calculados com os fornecidos
            return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                    digito2 == Character.getNumericValue(cpf.charAt(10));

        } catch (NumberFormatException e) {
            return false; // CPF contém caracteres não numéricos
        }
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
 * -----------------------------------------------------------------------------
 * Desmembrando a expressão ---- (\\d)\\1{10} ---- Validador CPF
 * -----------------------------------------------------------------------------
 * 1. \\d
 * Significado:
 * \\d corresponde a qualquer dígito numérico de 0 a 9.
 * O duplo \\ é necessário em Java para escapar o caractere de escape da regex.
 * -----------------------------------------------------------------------------
 * 2. (\\d)
 * Significado:
 * O parêntese () cria um grupo de captura, que captura o valor encontrado pela
 * expressão dentro dele.
 * Aqui, captura-se um único dígito, de 0 a 9.
 * -----------------------------------------------------------------------------
 * 3. \\1
 * Significado:
 * Refere-se ao valor capturado pelo primeiro grupo de captura (\\d).
 * Basicamente, verifica se o próximo caractere é igual ao capturado
 * anteriormente.
 * -----------------------------------------------------------------------------
 * 4. {10}
 * Significado:
 * Um quantificador que indica exatamente 10 repetições do caractere anterior
 * (ou grupo).
 * Aqui, verifica se o dígito capturado pelo grupo (\\d) aparece 10 vezes
 * consecutivas após o primeiro.
 * -----------------------------------------------------------------------------
 * 5. Combinação completa: (\\d)\\1{10}
 * Significado geral:
 * Captura o primeiro dígito usando (\\d).
 * Verifica se esse mesmo dígito (\\1) é repetido exatamente 10 vezes
 * consecutivas.
 * No total, valida strings formadas por 11 dígitos iguais.
 */
