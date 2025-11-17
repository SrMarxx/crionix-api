package br.com.smarttech.frigonix.controllers.dtos;

import java.time.LocalDateTime;

public record LeituraResponseRecordDTO(
        Long id,
        Double valor,
        LocalDateTime timestamp
) {
}
