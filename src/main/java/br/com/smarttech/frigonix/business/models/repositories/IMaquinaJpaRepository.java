package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMaquinaJpaRepository extends JpaRepository<MaquinaEntity, Long> {
}
