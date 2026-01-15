package br.com.imobmatch.api.services.broker;

import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import org.springframework.stereotype.Service;

@Service
public class BrokerValidationService {

    /*
    * Serviço responsável por validar os dados enviados no cadastro de corretor.
    * Inclui validações como:
    * Formato válido do CRECI via regex!
    * Outras validações de negócio conforme necessário.
    * * Obs: Validação de upload de PDF removida temporariamente.
    */

    public void run(BrokerPostDTO data) {
        // Removida validação de arquivos PDF por enquanto
        if (!isCreciValid(data.getCreci())) {
            throw new IllegalArgumentException("O formato do CRECI é inválido. Ex: 12345F");
        }
    }

    /* Validação simples do formato do CRECI via regex.
        Futuramente implementaremos a validação oficial via API do CRECI se disponível.
    */
    private boolean isCreciValid(String creci) {
        if (creci == null) return false;
        
        String cleanCreci = creci.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        
        return cleanCreci.matches("^\\d{4,6}[a-zA-Z]?$");
    }
}