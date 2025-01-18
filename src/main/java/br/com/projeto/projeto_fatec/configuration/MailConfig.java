// package br.com.projeto.projeto_fatec.configuration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.util.Properties;

// @Configuration
// public class MailConfig {

// @Bean
// public JavaMailSender mailSender() {
// JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
// mailSender.setHost("smtp.gmail.com");
// mailSender.setPort(587);
// mailSender.setUsername("seu-email@gmail.com");
// mailSender.setPassword("sua-senha");

// Properties props = mailSender.getJavaMailProperties();
// props.put("mail.transport.protocol", "smtp");
// props.put("mail.smtp.auth", "true");
// props.put("mail.smtp.starttls.enable", "true");
// props.put("mail.debug", "true");

// return mailSender;
// }
// }
