package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.business.models.entities.ColaboradorEntity;
import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import br.com.smarttech.frigonix.infrastructures.enums.TipoManutencao;

import java.time.LocalDateTime;

public record ManutencaoResponseRecordDTO(
        Long id,
        String maquina,
        String colaborador,
        LocalDateTime dataCriacao,
        LocalDateTime dataLimite,
        Boolean ativo,
        String descricao,
        Prioridade prioridade,
        TipoManutencao tipo,
        LocalDateTime dataConclusao
) {
}
