package br.com.smarttech.frigonix.controllers.dtos;

import java.time.LocalDateTime;

public record LeituraRequestRecordDTO(
        Double valor,
        LocalDateTime timestamp
) {
}
