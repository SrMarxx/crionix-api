package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.Cargo;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseRecordDTO(
        UUID userId,
        String name,
        String cpf,
        LocalDate nascimento,
        String matricula,
        String email,
        boolean mustChangePassword,
        Cargo cargo
) {
}
