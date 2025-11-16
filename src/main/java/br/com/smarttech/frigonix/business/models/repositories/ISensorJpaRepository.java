package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISensorJpaRepository extends JpaRepository<SensorEntity, Long> {
}
