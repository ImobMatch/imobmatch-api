package br.com.imobmatch.api;

import br.com.imobmatch.api.services.broker.BrokerValidationService;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrokerValidationServiceTest {

    @InjectMocks
    private BrokerValidationService brokerValidationService;

    @Mock
    private MultipartFile cpfFile;

    @Mock
    private MultipartFile creciFile;

	/* 
		Testes unitários para o BrokerValidationService.
		Cobre cenários de sucesso e falha nas validações de arquivos e CRECI.
	 */

    @Test
    @DisplayName("Deve validar com sucesso quando arquivos são PDFs e CRECI é válido")
    void shouldValidateSuccessfully() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        dto.setCreci("12345F"); 


        // Act
        when(cpfFile.isEmpty()).thenReturn(false);
        when(cpfFile.getContentType()).thenReturn("application/pdf");

        when(creciFile.isEmpty()).thenReturn(false);
        when(creciFile.getContentType()).thenReturn("application/pdf");

        // Assert
        assertDoesNotThrow(() -> 
            brokerValidationService.run(dto, cpfFile, creciFile)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o arquivo de CPF estiver vazio/nulo")
    void shouldThrowExceptionWhenCpfFileIsMissing() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        
        // Act
        when(cpfFile.isEmpty()).thenReturn(true);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            brokerValidationService.run(dto, cpfFile, creciFile)
        );

        assertTrue(exception.getMessage().contains("Documento de CPF é obrigatório"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o arquivo não for PDF")
    void shouldThrowExceptionWhenFileIsNotPdf() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();

        // Act. Simulando arquivo que existe, mas é uma imagem (ex: PNG)
        when(cpfFile.isEmpty()).thenReturn(false);
        when(cpfFile.getContentType()).thenReturn("image/png");

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            brokerValidationService.run(dto, cpfFile, creciFile)
        );

        assertTrue(exception.getMessage().contains("deve estar no formato PDF"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CRECI tiver formato inválido")
    void shouldThrowExceptionWhenCreciIsInvalid() {
        // Arrange
        BrokerPostDTO dto = new BrokerPostDTO();
        dto.setCreci("INVALID-CRECI-123"); // Formato incorreto de CRECI. Fora do padrão esperado

        // Act. Os arquivos estão ok
        when(cpfFile.isEmpty()).thenReturn(false);
        when(cpfFile.getContentType()).thenReturn("application/pdf");
        when(creciFile.isEmpty()).thenReturn(false);
        when(creciFile.getContentType()).thenReturn("application/pdf");

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            brokerValidationService.run(dto, cpfFile, creciFile)
        );

        assertTrue(exception.getMessage().contains("formato do CRECI é inválido"));
    }
}