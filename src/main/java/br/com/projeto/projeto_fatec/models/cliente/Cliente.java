package br.com.projeto.projeto_fatec.models.cliente;

import java.io.Serializable;
import java.util.List;

import br.com.projeto.projeto_fatec.models.usuario.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CLT")
public class Cliente implements Serializable {

    @Id
    @Column(name = "cpf", length = 11)
    @NotBlank
    private String cpf;

    @Column(name = "nome", length = 45)
    @NotBlank
    private String nome;

    @Column(name = "rg", length = 12)
    private String rg;

    @Column(name = "telefone", length = 11)
    @NotBlank
    private String telefone;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "USR_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ClientePrecedente> clientesPrecedente;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<ClienteContato> clientesContato;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public List<ClientePrecedente> getClientesPrecedente() {
        return clientesPrecedente;
    }

    public void setClientesPrecedente(List<ClientePrecedente> clientesPrecedente) {
        this.clientesPrecedente = clientesPrecedente;
    }

    public List<ClienteContato> getClientesContato() {
        return clientesContato;
    }

    public void setClientesContato(List<ClienteContato> clientesContato) {
        this.clientesContato = clientesContato;
    }
}