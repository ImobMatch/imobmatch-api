package br.com.imobmatch.api.services.broker;

import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BrokerValidationService {

    /*
    *   Serviço responsável por validar os dados e arquivos enviados no cadastro de corretor.
    *   Inclui validações como:
    *   Presença e formato dos arquivos PDF (CPF e CRECI)
    *   Formato válido do CRECI via regex!
    *   Outras validações de negócio conforme necessário
     */


    public void run(BrokerPostDTO data, MultipartFile cpfDoc, MultipartFile creciDoc) {
        
        validatePdfFile(cpfDoc, "Documento de CPF");
        validatePdfFile(creciDoc, "Documento do CRECI");
        if (!isCreciValid(data.getCreci())) {
            throw new IllegalArgumentException("O formato do CRECI é inválido. Ex: 12345F");
        }
        
    }

    private void validatePdfFile(MultipartFile file, String docName) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("O " + docName + " é obrigatório.");
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            throw new IllegalArgumentException("O " + docName + " deve estar no formato PDF.");
        }
    }

    /* 
        Validação simples do formato do CRECI via regex.
        Futuramente implementaremos a validação oficial via API do CRECI se disponível.
     */

    private boolean isCreciValid(String creci) {
        if (creci == null) return false;
        
        String cleanCreci = creci.replaceAll("[^a-zA-Z0-9]", "");
        
        return cleanCreci.matches("^\\d{4,6}[a-zA-Z]?$");
    }
}