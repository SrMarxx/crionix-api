package br.com.smarttech.frigonix.controllers.mappers;

import br.com.smarttech.frigonix.business.models.entities.ManutencaoEntity;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoResponseRecordDTO;

public class ManutencaoMapper {
    public static ManutencaoResponseRecordDTO toResponseDTO(ManutencaoEntity entity){
        return new ManutencaoResponseRecordDTO(
                entity.getId(),
                entity.getMaquina().getName(),
                entity.getColaborador().getName(),
                entity.getDataCriacao(),
                entity.getDataLimite(),
                entity.getAtivo(),
                entity.getDescricao(),
                entity.getPrioridade(),
                entity.getTipoManutencao(),
                entity.getDataConclusao()
        );
    }
}
