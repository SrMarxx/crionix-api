package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IEmpresaJpaRepository extends JpaRepository<EmpresaEntity, UUID> {
}
