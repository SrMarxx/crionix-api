package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.infrastructures.enums.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMaquinaJpaRepository extends JpaRepository<MaquinaEntity, Long> {
    long countByAtivoTrue();
    List<MaquinaEntity> findByAtivoTrue();
    List<MaquinaEntity> findBySetor(Setor setor);
}
