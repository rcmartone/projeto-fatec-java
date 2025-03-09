package br.com.projeto.projeto_fatec.rests;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.projeto.projeto_fatec.dto.registro.ContatoDto;
import br.com.projeto.projeto_fatec.dto.registro.PrecedenteDto;
import br.com.projeto.projeto_fatec.dto.registro.requisicao.RequisicaoRegistrarClienteDto;
import br.com.projeto.projeto_fatec.models.cliente.Sexo;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class RegistroRestTest {
        private final MockMvc mockMvc;
        private final ObjectMapper objectMapper;

        @Autowired
        public RegistroRestTest(MockMvc mockMvc, ObjectMapper objectMapper) {
                this.mockMvc = mockMvc;
                this.objectMapper = objectMapper;
        }

        @Test
        public void testeCriacaoCliente() throws Exception {
                List<ContatoDto> contatos = Arrays.asList(
                                new ContatoDto[] { new ContatoDto("11998765432", "Amigo"),
                                                new ContatoDto("11923456789", "Familiar") });
                List<PrecedenteDto> precedentes = Arrays.asList(
                                new PrecedenteDto[] { new PrecedenteDto((long) 1, "desmaio"),
                                                new PrecedenteDto((long) 2, "a dipirona") });
                RequisicaoRegistrarClienteDto requisicao = new RequisicaoRegistrarClienteDto(
                                "Rafael Martone",
                                "47865692838",
                                LocalDate.parse("1990-05-15"),
                                Sexo.MASCULINO,
                                "123456789",
                                "rafaelcmartone@hotmail.com",
                                "11987654321",
                                contatos,
                                precedentes,
                                "SenhaForte@123");
                String corpoRequisicao = objectMapper.writeValueAsString(requisicao);
                mockMvc.perform(post("/registrar/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(corpoRequisicao))
                                .andExpect(status().isOk());
        }
}
