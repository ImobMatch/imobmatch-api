package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.phone.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
    Optional<Phone> findByDddAndNumber(String ddd, String number);
}
