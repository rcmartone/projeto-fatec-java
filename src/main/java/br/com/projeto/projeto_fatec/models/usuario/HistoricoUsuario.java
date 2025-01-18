package br.com.projeto.projeto_fatec.models.usuario;

import java.sql.Timestamp;

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
@Table(name="HST_USR")
@IdClass(HistoricoUsuarioId.class)
public class HistoricoUsuario {
    @Id
    @Column(name="timestamp_alt")
    @NotNull
    private Timestamp timestampAlt; // Campo da chave primária

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "USR_id", referencedColumnName = "id")
    @NotNull
    private Usuario usuarioId; // Relacionamento com a entidade Usuario
    @Column(name="operacao")
    @NotBlank
    private String operacao;
    @Column(name="camposAlt", length=45)
    private String camposAlt;

    // Construtores, Getters e Setters
    public HistoricoUsuario() {}

    // Getters e Setters

    public Timestamp getTimestampAlt() {
        return timestampAlt;
    }

    public void setTimestampAlt(Timestamp timestampAlt) {
        this.timestampAlt = timestampAlt;
    }

    public Usuario getUsr() {
        return usuarioId;
    }

    public void setUsr(Usuario usuarioId) {
        this.usuarioId = usuarioId;
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
}