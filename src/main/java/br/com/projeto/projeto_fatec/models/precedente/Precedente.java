package br.com.projeto.projeto_fatec.models.precedente;

import java.io.Serializable;
import java.util.List;

import br.com.projeto.projeto_fatec.models.cliente.ClientePrecedente;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PRC_SD")
public class Precedente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", length = 45)
    @NotBlank
    private String nome;
    @Column(name = "adicional")
    @NotNull
    private Boolean adicional;

    @OneToMany(mappedBy = "precedente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ClientePrecedente> clientesPrecedente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAdicional() {
        return adicional;
    }

    public void setAdicional(Boolean adicional) {
        this.adicional = adicional;
    }

    public List<ClientePrecedente> getClientesPrecedente() {
        return clientesPrecedente;
    }

    public void setClientesPrecedente(List<ClientePrecedente> clientesPrecedente) {
        this.clientesPrecedente = clientesPrecedente;
    }
}
