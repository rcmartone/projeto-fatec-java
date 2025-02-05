package br.com.projeto.projeto_fatec.services;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.projeto.projeto_fatec.events.EnvioEmailEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    @EventListener
    public void enviarEmailPorTemplate(EnvioEmailEvent evento)
            throws MailException, MessagingException {
        Context context = new Context();
        Map<String, Object> variaveis = evento.getVariaveis();
        if (variaveis != null && !variaveis.isEmpty()) {
            for (Map.Entry<String, Object> variavel : variaveis.entrySet()) {
                context.setVariable(variavel.getKey(), variavel.getValue());
            }
        }
        String emailHtml = templateEngine.process(evento.getModelo(), context);
        enviarEmailHtml(emailHtml, evento.getDestinatario(), evento.getAssunto());
    }

    private void enviarEmailHtml(String html, String destinatario, String assunto)
            throws MailException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(html, true);
        helper.setFrom("rafaelcmartone@gmail.com");// Email para teste
        mailSender.send(message);
    }

}