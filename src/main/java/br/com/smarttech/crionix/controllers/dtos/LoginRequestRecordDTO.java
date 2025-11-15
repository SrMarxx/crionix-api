package br.com.smarttech.crionix.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecordDTO(
        @NotBlank String matricula,
        @NotBlank String password
) {
}
