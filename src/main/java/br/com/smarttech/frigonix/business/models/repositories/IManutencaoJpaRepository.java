package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.ManutencaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IManutencaoJpaRepository extends JpaRepository<ManutencaoEntity, Long> {
    List<ManutencaoEntity> findByAtivoTrue();
}
