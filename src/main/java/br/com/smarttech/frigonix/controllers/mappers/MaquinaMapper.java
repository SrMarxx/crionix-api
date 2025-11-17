package br.com.smarttech.frigonix.controllers.mappers;

import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaResponseRecordDTO;

public class MaquinaMapper {
    public static MaquinaResponseRecordDTO toResponseDTO(MaquinaEntity entity){
        return new MaquinaResponseRecordDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getTensaoPadrao(),
            entity.getTensaoVariacao(),
            entity.getTemperaturaPadrao(),
            entity.getTemperaturaVariacao(),
            entity.getPressaoPadrao(),
            entity.getPressaoVariacao(),
            entity.getHumidadePadrao(),
            entity.getHumidadeVariacao()
        );
    }
}
