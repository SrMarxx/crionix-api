package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.TipoSensor;

public record SensorRequestRecordDTO(
        String name,
        String description,
        TipoSensor type
) {
}
