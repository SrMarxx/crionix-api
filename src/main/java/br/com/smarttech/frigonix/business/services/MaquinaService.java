package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.ISensorJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.mappers.MaquinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaquinaService {
    private final IMaquinaJpaRepository maquinaRepository;
    private final ISensorJpaRepository sensorRepository;

    @Autowired
    public MaquinaService(IMaquinaJpaRepository maquinaRepository, ISensorJpaRepository sensorRepository) {
        this.maquinaRepository = maquinaRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<MaquinaResponseRecordDTO> findAll(){
        List<MaquinaEntity> maquinas = maquinaRepository.findAll();
        return maquinas.stream().map(MaquinaMapper::toResponseDTO).collect(Collectors.toList());
    }

    public MaquinaResponseRecordDTO newMaquina(MaquinaRequestRecordDTO maquinaDTO){
        MaquinaEntity maquina = new MaquinaEntity();
        maquina.setName(maquinaDTO.name());
        maquina.setDescription(maquinaDTO.description());
        maquina.setPressaoPadrao(maquinaDTO.pressaoPadrao());
        maquina.setPressaoVariacao(maquinaDTO.pressaoVariacao());
        maquina.setTemperaturaPadrao(maquinaDTO.temperaturaPadrao());
        maquina.setTemperaturaVariacao(maquinaDTO.temperaturaVariacao());
        maquina.setTensaoPadrao(maquinaDTO.tensaoPadrao());
        maquina.setTensaoVariacao(maquinaDTO.tensaoVariacao());

        List<SensorEntity> sensores = sensorRepository.findAllById(maquinaDTO.sensores());
        maquina.setSensors(new HashSet<>(sensores));

        MaquinaEntity savedMaquina = maquinaRepository.save(maquina);

        return MaquinaMapper.toResponseDTO(savedMaquina);
    }

}
