package br.com.smarttech.frigonix.controllers.dtos;

public record LoginResponseRecordDTO(
        String accessToken,
        Long expiresIn
) {
}
