package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.business.models.repositories.ILeituraJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.ISensorJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.mappers.LeituraMapper;
import br.com.smarttech.frigonix.controllers.mappers.MaquinaMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaquinaService {
    private final IMaquinaJpaRepository maquinaRepository;
    private final ISensorJpaRepository sensorRepository;
    private final ILeituraJpaRepository leituraRepository;

    @Autowired
    public MaquinaService(IMaquinaJpaRepository maquinaRepository, ISensorJpaRepository sensorRepository, ILeituraJpaRepository leituraRepository) {
        this.maquinaRepository = maquinaRepository;
        this.sensorRepository = sensorRepository;
        this.leituraRepository = leituraRepository;
    }

    public List<MaquinaResponseRecordDTO> findAll(){
        List<MaquinaEntity> maquinas = maquinaRepository.findAll();
        return maquinas.stream().map(MaquinaMapper::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public MaquinaResponseRecordDTO newMaquina(MaquinaRequestRecordDTO maquinaDTO){
        MaquinaEntity maquina = new MaquinaEntity();
        maquina.setAtivo(true);
        maquina.setName(maquinaDTO.name());
        maquina.setDescription(maquinaDTO.description());
        maquina.setPressaoPadrao(maquinaDTO.pressaoPadrao());
        maquina.setPressaoVariacao(maquinaDTO.pressaoVariacao());
        maquina.setTemperaturaPadrao(maquinaDTO.temperaturaPadrao());
        maquina.setTemperaturaVariacao(maquinaDTO.temperaturaVariacao());
        maquina.setTensaoPadrao(maquinaDTO.tensaoPadrao());
        maquina.setTensaoVariacao(maquinaDTO.tensaoVariacao());
        maquina.setHumidadePadrao(maquinaDTO.humidadePadrao());
        maquina.setHumidadeVariacao(maquinaDTO.humidadeVariacao());

        List<SensorEntity> sensores = sensorRepository.findAllById(maquinaDTO.sensores());
        maquina.setSensors(new HashSet<>(sensores));

        MaquinaEntity savedMaquina = maquinaRepository.save(maquina);

        return MaquinaMapper.toResponseDTO(savedMaquina);
    }

    public List<LeituraResponseRecordDTO> getLeituras(Long idMaquina){
        MaquinaEntity maquina = maquinaRepository.findById(idMaquina).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Set<SensorEntity> sensores = maquina.getSensors();

        List<LeituraEntity> leiturasRecentes = sensores.stream()
                .map(sensor -> leituraRepository.findTopBySensorIdOrderByTimestampDesc(sensor.getId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        return leiturasRecentes.stream().map(LeituraMapper::toResponseDTO).collect(Collectors.toList());
    }

    public void deleteMaquina(Long maquinaId){
        MaquinaEntity maquina = maquinaRepository.findById(maquinaId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Máquina não encontrada."));
        maquina.setAtivo(false);
        maquinaRepository.save(maquina);
    }
}
