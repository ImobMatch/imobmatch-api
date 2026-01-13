package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface OwnerRepository extends JpaRepository<Owner, UUID> {

  Optional<Owner> findByCpf(String cpf);
  boolean existsOwnerByCpf(String cpf);
}
