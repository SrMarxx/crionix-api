package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.GeradorMatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGeradorMatriculaJpaRepository extends JpaRepository<GeradorMatriculaEntity, Integer> {
    Optional<GeradorMatriculaEntity> findByAno(Integer ano);
}
