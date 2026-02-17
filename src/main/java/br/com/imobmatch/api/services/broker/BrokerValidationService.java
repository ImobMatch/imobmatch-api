package br.com.imobmatch.api.services.broker;

import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import org.springframework.stereotype.Service;

@Service
public class BrokerValidationService {

    /*
    * Service responsible for validating the data sent in the broker registration.
    * Includes validations such as:
    * Validity of CRECI format.
    * Other validations of business rules as needed.
    * * Obs: PDF upload validation removed temporarily.
    */

    public void run(BrokerPostDTO data) {
        // PDF validation removed temporarily.
        if (!isCreciValid(data.getCreci())) {
            throw new IllegalArgumentException("O formato do CRECI é inválido. Ex: 12345F");
        }
    }

    /* Simples validation of CRECI format.
        Later can be expanded with API.
    */
    private boolean isCreciValid(String creci) {
        if (creci == null) return false;
        
        String cleanCreci = creci.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        
        return cleanCreci.matches("^\\d{4,6}[A-Z]$");
    }
}