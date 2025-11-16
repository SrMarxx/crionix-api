package br.com.smarttech.frigonix.controllers.dtos;

import jakarta.validation.constraints.Email;

public record UserUpdateRecordDTO(
        String name,
        @Email(message = "Este não é um email válido.") String email
) {
}
