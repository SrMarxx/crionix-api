package br.com.smarttech.crionix.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRecordDTO(
        @NotBlank(message = "A senha antiga é obrigatória.")
        String senhaAntiga,

        @NotBlank(message = "A nova senha é obrigatória.")
        @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres.")
        String novaSenha
) {
}
