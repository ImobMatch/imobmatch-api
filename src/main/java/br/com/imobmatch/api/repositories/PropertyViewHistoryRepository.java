package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.property.PropertyViewHistory;
import org.springframework.context.annotation.ReflectiveScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@ReflectiveScan
public interface PropertyViewHistoryRepository extends JpaRepository<PropertyViewHistory, UUID>{

    List<PropertyViewHistory> findTop20ByBrokerOrderByViewedAtDesc(Broker broker);
}
