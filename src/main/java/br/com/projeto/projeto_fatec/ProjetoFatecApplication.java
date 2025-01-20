package br.com.projeto.projeto_fatec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjetoFatecApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoFatecApplication.class, args);

	}

}
