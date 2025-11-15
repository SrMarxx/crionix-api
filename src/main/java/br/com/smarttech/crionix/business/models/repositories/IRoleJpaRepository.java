package br.com.smarttech.crionix.business.models.repositories;

import br.com.smarttech.crionix.business.models.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleJpaRepository extends JpaRepository<RoleEntity, Long> {
}
