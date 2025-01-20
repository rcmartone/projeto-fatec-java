package br.com.projeto.projeto_fatec.events;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

public class EnvioEmailEvent extends ApplicationEvent {
    private Map<String, Object> variaveis;
    private final String destinatario;
    private final String assunto;
    private final String modelo;

    public EnvioEmailEvent(Object source, String destinatario, String assunto,
            String modelo, Map<String, Object> variaveis) {
        super(source);
        this.assunto = assunto;
        this.destinatario = destinatario;
        this.modelo = modelo;
        this.variaveis = variaveis;
    }

    public EnvioEmailEvent(Object source, String destinatario, String assunto, String modelo) {
        super(source);
        this.assunto = assunto;
        this.destinatario = destinatario;
        this.modelo = modelo;
    }

    public Map<String, Object> getVariaveis() {
        return variaveis;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getModelo() {
        return modelo;
    }

}
