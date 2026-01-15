package br.com.imobmatch.api;

import br.com.imobmatch.api.services.broker.BrokerValidationService;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BrokerValidationServiceTest {

    @InjectMocks
    private BrokerValidationService brokerValidationService;

    // Removemos os Mocks de MultipartFile pois não são mais usados

    @Test
    @DisplayName("Deve validar com sucesso quando CRECI é válido")
    void shouldValidateSuccessfully() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        dto.setCreci("12345F"); 

        // Act & Assert
        // Não precisamos mais de 'when' pois não há dependências externas mockadas
        assertDoesNotThrow(() -> 
            brokerValidationService.run(dto)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CRECI tiver formato inválido")
    void shouldThrowExceptionWhenCreciIsInvalid() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        dto.setCreci("INVALID-CRECI-123"); 

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            brokerValidationService.run(dto)
        );

        assertTrue(exception.getMessage().contains("formato do CRECI é inválido"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CRECI for nulo")
    void shouldThrowExceptionWhenCreciIsNull() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        dto.setCreci(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            brokerValidationService.run(dto)
        );

        assertTrue(exception.getMessage().contains("formato do CRECI é inválido"));
    }
}