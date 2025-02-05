package br.com.projeto.projeto_fatec.models.cliente;

import java.io.Serializable;

import br.com.projeto.projeto_fatec.models.precedente.Precedente;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLT_PRC")
@IdClass(ClientePrecedenteId.class)
public class ClientePrecedente implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "PRC_id", referencedColumnName = "id")
    private Precedente precedente;

    @Id
    @ManyToOne
    @JoinColumn(name = "CLT_cpf", referencedColumnName = "cpf")
    private Cliente cliente;

    @Nullable
    @Column(name = "resposta_adicional", length = 255)
    private String respostaAdicional;

    public String getRespostaAdicional() {
        return respostaAdicional;
    }

    public void setRespostaAdicional(String respostaAdicional) {
        this.respostaAdicional = respostaAdicional;
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

}
