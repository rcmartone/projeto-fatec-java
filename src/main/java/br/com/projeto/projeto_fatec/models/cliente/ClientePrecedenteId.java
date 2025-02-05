package br.com.projeto.projeto_fatec.models.cliente;

import java.io.Serializable;
import java.util.Objects;

import br.com.projeto.projeto_fatec.models.precedente.Precedente;

public class ClientePrecedenteId implements Serializable {

    private Precedente precedente;
    private Cliente cliente;

    public ClientePrecedenteId() {
    }

    public ClientePrecedenteId(Precedente precedente, Cliente cliente) {
        this.precedente = precedente;
        this.cliente = cliente;
    }

    public Precedente getPrecedente() {
        return precedente;
    }

    public void setPrecedente(Precedente precedente) {
        this.precedente = precedente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientePrecedenteId that = (ClientePrecedenteId) o;
        return Objects.equals(precedente, that.precedente) &&
                Objects.equals(cliente, that.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(precedente, cliente);
    }
}
