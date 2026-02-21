package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

  @Query("""
    SELECT DISTINCT b
    FROM brokers b
    WHERE :regionInterest MEMBER OF b.regionsInterest
""")
  List<Broker> findByRegionInterest(BrazilianState regionInterest);

  @Query("""
      SELECT DISTINCT b
      FROM brokers b
      WHERE :propertyType MEMBER OF b.propertyTypes
  """)
  List<Broker> findByPropertyType(PropertyType propertyType);

  List<Broker> findByBusinessType(PropertyBusinessType businessType);

  List<Broker> findByAccountStatus(BrokerAccountStatus accountStatus);  

}