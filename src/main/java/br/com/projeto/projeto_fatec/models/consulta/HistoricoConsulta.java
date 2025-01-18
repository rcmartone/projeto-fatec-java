package br.com.projeto.projeto_fatec.models.consulta;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="HST_CST")
@IdClass(HistoricoConsultaId.class)
public class HistoricoConsulta implements Serializable {
    @Id
    private Timestamp timestampAlt; // Campo da chave primária

    @Id
    @ManyToOne
    @JoinColumn(name = "CST_id", referencedColumnName = "id") // A coluna CST_id refere-se ao id da tabela Consulta
    private Consulta cstId; // Relacionamento com a entidade Consulta
    @Column(name="tipo",length=30)
    @NotBlank
    private String tipo;
    @Column(name="status",length=10)
    @NotBlank
    private String status;
    @Column(name="observacao",length=255)
    private String observacao;
    @Column(name="dataInicio")
    @NotNull
    private Timestamp dataInicio;
    @Column(name="dataFim")
    @NotNull
    private Timestamp dataFim;

    // Construtores, Getters e Setters
    public HistoricoConsulta() {}

    // Getters e Setters

    public Timestamp getTimestampAlt() {
        return timestampAlt;
    }

    public void setTimestampAlt(Timestamp timestampAlt) {
        this.timestampAlt = timestampAlt;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Timestamp getDataFim() {
        return dataFim;
    }

    public void setDataFim(Timestamp dataFim) {
        this.dataFim = dataFim;
    }

    public Consulta getCstId() {
        return cstId;
    }

    public void setCstId(Consulta cstId) {
        this.cstId = cstId;
    }
}

