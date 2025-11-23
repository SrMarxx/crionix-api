package br.com.smarttech.frigonix.controllers.mappers;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;

public class LeituraMapper {
    public static LeituraResponseRecordDTO toResponseDTO(LeituraEntity entity){
        return new LeituraResponseRecordDTO(
                entity.getId(),
                entity.getValor(),
                entity.getTimestamp(),
                entity.getSensor().getType()
        );
    }
}
