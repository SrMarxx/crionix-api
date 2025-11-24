package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.RegistroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRegistroJpaRepository extends JpaRepository<RegistroEntity, Long> {
    Optional<RegistroEntity> findTopByOrderByDataRegistroDesc();
}
