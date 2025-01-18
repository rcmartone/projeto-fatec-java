package br.com.projeto.projeto_fatec.models.doutor;

import java.io.Serializable;
import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="HST_DTR")
@IdClass(HistoricoDoutorId.class)
public class HistoricoDoutor implements Serializable {
    @Id
    @Column(name="timestamp_alt")
    @NotNull
    private Timestamp timestampAlt;

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "DTR_id", referencedColumnName = "id") // DTR_id refere-se ao id da tabela DTR
    @NotBlank
    private Doutor doutorId;
    @Column(name="operacao")
    @NotNull
    private String operacao;
    @Column(name="camposAlt", length=45)
    @NotBlank
    private String camposAlt;

    public HistoricoDoutor() {} //Construtor

    public Timestamp getTimestampAlt() {
        return timestampAlt;
    }

    public void setTimestampAlt(Timestamp timestampAlt) {
        this.timestampAlt = timestampAlt;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getCamposAlt() {
        return camposAlt;
    }

    public void setCamposAlt(String camposAlt) {
        this.camposAlt = camposAlt;
    }

    public Doutor getDoutorId() {
        return doutorId;
    }

    public void setDoutorId(Doutor doutorId) {
        this.doutorId = doutorId;
    }

    public Timestamp getTimestamp_Alt() {
        return timestampAlt;
    }

    public void setTimestamp_Alt(Timestamp timestampAlt) {
        this.timestampAlt = timestampAlt;
    }

}

