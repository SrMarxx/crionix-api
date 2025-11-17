package br.com.smarttech.frigonix.controllers.mappers;

import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.controllers.dtos.SensorResponseRecordDTO;

public class SensorMapper {
    public static SensorResponseRecordDTO toResponseDTO(SensorEntity entity){
        return new SensorResponseRecordDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getType()
        );
    }
}
