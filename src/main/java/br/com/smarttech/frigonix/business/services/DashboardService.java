package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.business.models.entities.RegistroEntity;
import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.business.models.repositories.ILeituraJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IManutencaoJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IRegistroJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.PerformanceResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ResumoResponseRecordDTO;
import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import br.com.smarttech.frigonix.infrastructures.enums.Setor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final IMaquinaJpaRepository maquinaRepository;
    private final IRegistroJpaRepository registroRepository;
    private final IManutencaoJpaRepository manutencaoRepository;
    private final ILeituraJpaRepository leituraRepository;

    @Autowired
    public DashboardService(IMaquinaJpaRepository maquinaRepository, IRegistroJpaRepository registroRepository, IManutencaoJpaRepository manutencaoRepository, ILeituraJpaRepository leituraRepository) {
        this.maquinaRepository = maquinaRepository;
        this.registroRepository = registroRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.leituraRepository = leituraRepository;
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
        Double taxaManutencoesFalha = 0.0;
        if(manutencoesFalhas != 0) {
            taxaManutencoesFalha = ((manutencoesFalhas - registro.getQuantidadeFalha()) / manutencoesFalhas) * 100.0;
        }
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

    public List<PerformanceResponseRecordDTO> getPerformance() {
        List<MaquinaEntity> maquinas = maquinaRepository.findByAtivoTrue();
        Map<Setor, List<MaquinaEntity>> maquinasPorSetor = maquinas.stream()
                .collect(Collectors.groupingBy(MaquinaEntity::getSetor));

        List<PerformanceResponseRecordDTO> performanceList = new ArrayList<>();

        for (Map.Entry<Setor, List<MaquinaEntity>> entry : maquinasPorSetor.entrySet()) {
            Setor setor = entry.getKey();
            List<MaquinaEntity> maquinasDoSetor = entry.getValue();

            int sensoresConformes = 0;
            int sensoresTotal = 0;

            for (MaquinaEntity maquina : maquinasDoSetor) {
                for (SensorEntity sensor : maquina.getSensors()) {
                    // Supondo que existe um método sensor.getValorAtual()
                    Double valor = leituraRepository.findTopBySensorIdOrderByTimestampDesc(sensor.getId()).orElse(new LeituraEntity()).getValor();// adapte para sua leitura real!
                    boolean conforme = false;
                    if(valor.isNaN() || valor.isInfinite() || valor == null){
                        continue;
                    }
                    switch (sensor.getType()) {
                        case TEMPERATURE:
                            conforme = valor >= maquina.getTemperaturaPadrao() - maquina.getTemperaturaVariacao()
                                    && valor <= maquina.getTemperaturaPadrao() + maquina.getTemperaturaVariacao();
                            break;
                        case HUMIDITY:
                            conforme = valor >= maquina.getHumidadePadrao() - maquina.getHumidadeVariacao()
                                    && valor <= maquina.getHumidadePadrao() + maquina.getHumidadeVariacao();
                            break;
                        case PRESSURE:
                            conforme = valor >= maquina.getPressaoPadrao() - maquina.getPressaoVariacao()
                                    && valor <= maquina.getPressaoPadrao() + maquina.getPressaoVariacao();
                            break;
                        case VOLTAGE:
                            conforme = valor >= maquina.getTensaoPadrao() - maquina.getTensaoVariacao()
                                    && valor <= maquina.getTensaoPadrao() + maquina.getTensaoVariacao();
                            break;
                        // outros sensores, se necessário
                    }
                    sensoresTotal++;
                    if (conforme) sensoresConformes++;
                }
            }
            double performance = sensoresTotal == 0 ? 0.0 : ((double) sensoresConformes / sensoresTotal) * 100.0;
            performanceList.add(new PerformanceResponseRecordDTO(setor, performance));
        }
        return performanceList;
    }
}
