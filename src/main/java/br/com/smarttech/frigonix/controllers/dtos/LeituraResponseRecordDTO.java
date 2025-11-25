package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.TipoSensor;

import java.time.LocalDateTime;

public record LeituraResponseRecordDTO(
        Long id,
        Double valor,
        LocalDateTime timestamp,
        TipoSensor tipo
) {
}
