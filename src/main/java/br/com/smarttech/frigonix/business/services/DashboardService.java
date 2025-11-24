package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.RegistroEntity;
import br.com.smarttech.frigonix.business.models.repositories.IManutencaoJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IRegistroJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.ResumoResponseRecordDTO;
import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {
    private final IMaquinaJpaRepository maquinaRepository;
    private final IRegistroJpaRepository registroRepository;
    private final IManutencaoJpaRepository manutencaoRepository;

    @Autowired
    public DashboardService(IMaquinaJpaRepository maquinaRepository, IRegistroJpaRepository registroRepository, IManutencaoJpaRepository manutencaoRepository) {
        this.maquinaRepository = maquinaRepository;
        this.registroRepository = registroRepository;
        this.manutencaoRepository = manutencaoRepository;
    }

    public ResumoResponseRecordDTO getResume(){
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

        RegistroEntity registro = registroRepository.findTopByOrderByDataRegistroDesc().orElse(new RegistroEntity());
        Long maquinasAtivas = maquinaRepository.countByAtivoTrue();
        Double taxaMaquina = ((maquinasAtivas - registro.getQuantidadeMaquinas())/maquinasAtivas)*100.0;
        ResumoResponseRecordDTO.Equipamentos equipamentos = new ResumoResponseRecordDTO.Equipamentos(
                maquinasAtivas,
                taxaMaquina
        );
        ResumoResponseRecordDTO.ManutencoesPendentes manutencoesPendentes = new ResumoResponseRecordDTO.ManutencoesPendentes(
                manutencaoRepository.countByAtivoTrue(),
                manutencaoRepository.countByPrioridadeAndAtivoTrue(Prioridade.URGENTE)
        );
        Long manutencoesFechadas = manutencaoRepository.countFechadasNoPeriodo(startOfMonth, endOfMonth);
        Double taxaManutencoes = ((manutencoesFechadas - registro.getQuantidadeManutencoesFechadas())/manutencoesFechadas)*100.0;
        ResumoResponseRecordDTO.ManutencoesConcluidas manutencoesConcluidas = new ResumoResponseRecordDTO.ManutencoesConcluidas(
            manutencoesFechadas,
                taxaManutencoes
        );
        Long manutencoesFalhas = manutencaoRepository.countFalhasNoPeriodo(startOfMonth, endOfMonth);
        Double taxaManutencoesFalha = ((manutencoesFalhas - registro.getQuantidadeFalha())/manutencoesFalhas)*100.0;
        ResumoResponseRecordDTO.TaxaFalha taxaFalha = new ResumoResponseRecordDTO.TaxaFalha(
                manutencoesFalhas,
                taxaManutencoesFalha
        );
        return new ResumoResponseRecordDTO(
            equipamentos,
            manutencoesPendentes,
            manutencoesConcluidas,
            taxaFalha
        );
    }
}
