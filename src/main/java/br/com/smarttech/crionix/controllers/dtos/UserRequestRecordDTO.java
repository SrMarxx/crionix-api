package br.com.smarttech.crionix.controllers.dtos;

import br.com.smarttech.crionix.infrastructures.enums.Cargo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserRequestRecordDTO(
        @NotBlank(message = "O nome não pode estar em branco.") String name,
        @NotBlank(message = "O CPF não pode estar em branco.") @CPF(message = "O CPF informado é inválido.") String cpf,
        @NotNull(message = "A data de nascimento não pode estar em branco.") @Past(message = "A data deve estar no passado") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate nascimento,
        @NotBlank(message = "O E-Mail não pode estar em branco.") @Email(message = "Este não é um email válido.") String email,
        @NotNull(message = "O cargo não pode ser nulo.") Cargo cargo
) {
}
