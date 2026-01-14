package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.owner.Owner;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;


public interface OwnerRepository extends JpaRepository<Owner, UUID> {

  Optional<Owner> findByCpf(String cpf);
  List<Owner> findAllByName(String name);
  Optional<Owner> findByUser_Email(String userEmail);
  List<Owner> findAllByBirthDate(LocalDate birthDate);
  List<Owner> findAllByWhatsAppPhoneNumberOrPersonalPhoneNumber(String whatsAppPhoneNumber,
      String personalPhoneNumber);

  boolean existsByBirthDate(LocalDate birthDate);
  boolean existsOwnerByUser_Email(String userEmail);
  boolean existsOwnerByCpf(String cpf);
  boolean existsOwnerByName(String name);
  boolean existsOwnerByWhatsAppPhoneNumber(String whatsAppPhoneNumber);
  boolean existsOwnerByPersonalPhoneNumber(String personalPhoneNumber);
}
