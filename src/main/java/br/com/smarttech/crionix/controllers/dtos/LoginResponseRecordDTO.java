package br.com.smarttech.crionix.controllers.dtos;

public record LoginResponseRecordDTO(
        String accessToken,
        Long expiresIn
) {
}
