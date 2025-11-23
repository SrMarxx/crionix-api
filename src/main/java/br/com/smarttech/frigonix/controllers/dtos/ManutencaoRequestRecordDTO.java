package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import br.com.smarttech.frigonix.infrastructures.enums.TipoManutencao;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManutencaoRequestRecordDTO(
        Long maquina,
        UUID colaborador,
        LocalDateTime dataLimite,
        String descricao,
        Prioridade prioridade,
        TipoManutencao tipo
) {
}
