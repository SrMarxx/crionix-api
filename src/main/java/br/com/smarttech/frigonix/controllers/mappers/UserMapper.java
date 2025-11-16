package br.com.smarttech.frigonix.controllers.mappers;

import br.com.smarttech.frigonix.business.models.entities.UserEntity;
import br.com.smarttech.frigonix.controllers.dtos.UserResponseRecordDTO;

public class UserMapper {
    public static UserResponseRecordDTO toResponseDTO(UserEntity entity){
        return new UserResponseRecordDTO(
                entity.getUserId(),
                entity.getName(),
                entity.getCpf(),
                entity.getNascimento(),
                entity.getMatricula(),
                entity.getEmail(),
                entity.isMustChangePassword(),
                entity.getCargo()
        );
    }
}
