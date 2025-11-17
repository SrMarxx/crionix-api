package br.com.smarttech.frigonix.controllers.dtos;

public record MaquinaResponseRecordDTO(
        Long id,
        String name,
        String description,
        Double tensaoPadrao,
        Double tensaoVariacao,
        Double temperaturaPadrao,
        Double temperaturaVariacao,
        Double pressaoPadrao,
        Double pressaoVariacao,
        Double humidadePadrao,
        Double humidadeVariacao
) {
}
