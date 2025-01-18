package br.com.projeto.projeto_fatec.models.usuario;

public enum Papel {
    DOUTOR("DOUTOR"),
    CLIENTE("CLIENTE"),
    ADMINISTRADOR("ADMINISTRADOR");


    private final String nomePapel;

    Papel(String nomePapel){
        this.nomePapel = nomePapel;
    }
    
    public String getNomePapel(){
        return nomePapel;
    }
}
