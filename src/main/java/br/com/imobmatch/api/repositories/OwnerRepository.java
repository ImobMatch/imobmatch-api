package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.owner.Owner;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface OwnerRepository extends JpaRepository<Owner, UUID> {

  Optional<Owner> findByCpf(String cpf);

  List<Owner> findAllByNameContainingIgnoreCase(String name);
  Optional<Owner> findByUser_Email(String userEmail);
  List<Owner> findAllByBirthDate(LocalDate birthDate);
  List<Owner> findAllByWhatsAppPhoneNumberOrPersonalPhoneNumber(String whatsAppPhoneNumber,
      String personalPhoneNumber);

  boolean existsByBirthDate(LocalDate birthDate);
  boolean existsByUser_Email(String userEmail);
  boolean existsByCpf(String cpf);
  boolean existsByName(String name);
  boolean existsByWhatsAppPhoneNumber(String whatsAppPhoneNumber);
  boolean existsByPersonalPhoneNumber(String personalPhoneNumber);
}
