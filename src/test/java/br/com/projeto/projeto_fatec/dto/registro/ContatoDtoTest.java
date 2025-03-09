package br.com.projeto.projeto_fatec.dto.registro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContatoDtoTest {
    private final ObjectMapper objMapper = new ObjectMapper();
    private final String JSON_DTO = "{\"tipo\":\"instagram\",\"contato\":\"@test\"}";

    @Test
    public void testeCriacaoDto() {
        ContatoDto contato = new ContatoDto("instagram", "@test");
        assertEquals("instagram", contato.tipo());// Condicional do teste (espera, recebe)
        assertEquals("@test", contato.contato());
    }

    @Test
    public void testeSerializacaoDto() throws JsonProcessingException {
        ContatoDto contato = new ContatoDto("instagram", "@test");
        String jsonContato = objMapper.writeValueAsString(contato);
        assertEquals(JSON_DTO, jsonContato);
    }

    @Test
    public void testeDeserializacaoDto() throws JsonProcessingException {
        ContatoDto contato = objMapper.readValue(JSON_DTO, ContatoDto.class);
        assertEquals("instagram", contato.tipo());// Condicional do teste (espera, recebe)
        assertEquals("@test", contato.contato());
    }
}
