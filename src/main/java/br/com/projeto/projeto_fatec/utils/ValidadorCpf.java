package br.com.projeto.projeto_fatec.utils;

public class ValidadorCpf {

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
 * Desmembrando a expressão ---- (\\d)\\1{10} ----
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