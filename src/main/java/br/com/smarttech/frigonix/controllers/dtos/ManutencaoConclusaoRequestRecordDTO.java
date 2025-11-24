package br.com.smarttech.frigonix.controllers.dtos;

import br.com.smarttech.frigonix.infrastructures.enums.TipoConclusao;

public record ManutencaoConclusaoRequestRecordDTO(
        TipoConclusao conclusao,
        String relatorio
) {
}
