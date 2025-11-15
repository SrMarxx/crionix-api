package br.com.smarttech.crionix.business.models.entities;

import br.com.smarttech.crionix.business.models.repositories.IRoleJpaRepository;
import br.com.smarttech.crionix.infrastructures.enums.Cargo;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.List;

@Entity
@DiscriminatorValue("COLABORADOR")
public class ColaboradorEntity extends UserEntity{
    @Override
    public void defineRoles(IRoleJpaRepository roleRepository) {
        List<RoleEntity> roles = roleRepository.findAllById(List.of(RoleEntity.Values.BATER_PONTO.getRoleId()));
        this.setRoles(new HashSet<>(roles));
    }

    @Override
    public Cargo getCargo(){
        return Cargo.COLABORADOR;
    }
}
