package br.com.smarttech.frigonix.business.models.entities;

import br.com.smarttech.frigonix.business.models.repositories.IRoleJpaRepository;
import br.com.smarttech.frigonix.infrastructures.enums.Cargo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.List;

@Entity
@DiscriminatorValue("COLABORADOR")
public class ColaboradorEntity extends UserEntity{
    @Override
    public void defineRoles(IRoleJpaRepository roleRepository) {
        List<RoleEntity> roles = roleRepository.findAllById(List.of(RoleEntity.Values.VISUALIZAR.getRoleId()));
        this.setRoles(new HashSet<>(roles));
    }

    @Override
    public Cargo getCargo(){
        return Cargo.COLABORADOR;
    }
}
