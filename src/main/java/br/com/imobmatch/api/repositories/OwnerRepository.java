package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.owner.Owner;
import java.time.LocalDate;
import java.util.List; // Ainda pode ser usado para métodos não paginados se necessário
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {

  Optional<Owner> findByCpf(String cpf);
  Optional<Owner> findByUser_Email(String userEmail);
  Page<Owner> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
  Page<Owner> findAllByBirthDate(LocalDate birthDate, Pageable pageable);
  List<Owner> findAllByWhatsAppPhoneNumberOrPersonalPhoneNumber(String whatsAppPhoneNumber, String personalPhoneNumber);

  boolean existsByBirthDate(LocalDate birthDate);
  boolean existsByUser_Email(String userEmail);
  boolean existsByCpf(String cpf);
  boolean existsByName(String name);
  boolean existsByWhatsAppPhoneNumber(String whatsAppPhoneNumber);
  boolean existsByPersonalPhoneNumber(String personalPhoneNumber);
}