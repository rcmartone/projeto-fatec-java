package br.com.projeto.projeto_fatec.models.cliente;

public enum Sexo {
    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    NAO_BINARIO("NAO BINÁRIO"),
    OUTROS("OUTROS");

    private final String nomeSexo;

    Sexo(String nomeSexo) {
        this.nomeSexo = nomeSexo;
    }

    public String getNomeSexo() {
        return nomeSexo;
    }
}
