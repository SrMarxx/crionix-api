package br.com.smarttech.frigonix.controllers.dtos;

import java.util.List;

public record MaquinaRequestRecordDTO(
    String name,
    String description,
    Double tensaoPadrao,
    Double tensaoVariacao,
    Double temperaturaPadrao,
    Double temperaturaVariacao,
    Double pressaoPadrao,
    Double pressaoVariacao,
    Double humidadePadrao,
    Double humidadeVariacao,
    List<Long> sensores
) {
}
