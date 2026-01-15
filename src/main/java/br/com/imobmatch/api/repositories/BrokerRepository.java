package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrokerRepository extends JpaRepository<Broker, UUID> {

  Optional<Broker> findByCpf(String cpf);
  boolean existsBrokerByCpf(String cpf );

  Optional<Broker> findByCreci(String creci);
  boolean existsBrokerByCreci(String creci );

  Optional<Broker> findByUser_Email(String email);
  boolean existsByUser_Email(String email );

  List<Broker> findByNameContainingIgnoreCase(String name);
  List<Broker> findByRegionInterestContainingIgnoreCase(String regionInterest);
  List<Broker> findByOperationCityContainingIgnoreCase(String operationCity);
  List<Broker> findByPropertyType(BrokerPropertyType propertyType);
  List<Broker> findByBusinessType(BrokerBusinessType businessType);
}