package br.com.smarttech.frigonix.business.models.entities;

import br.com.smarttech.frigonix.business.models.repositories.IRoleJpaRepository;
import br.com.smarttech.frigonix.infrastructures.enums.Cargo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends ColaboradorEntity{
    @Override
    public void defineRoles(IRoleJpaRepository roleRepository){
        super.defineRoles(roleRepository);
        List<RoleEntity> adminRoles = roleRepository.findAllById(List.of(RoleEntity.Values.ADMINISTRAR.getRoleId()));
        this.getRoles().addAll(adminRoles);
    }

    @Override
    public Cargo getCargo(){
        return Cargo.ADMIN;
    }
}
