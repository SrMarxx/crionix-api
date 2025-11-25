package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.Setor;

public record PerformanceResponseRecordDTO(
    Setor setor,
    Double performance
) {
}
