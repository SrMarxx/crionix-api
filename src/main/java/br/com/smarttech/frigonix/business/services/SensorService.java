package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.business.models.repositories.ILeituraJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.ISensorJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.LeituraRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.mappers.LeituraMapper;
import br.com.smarttech.frigonix.controllers.mappers.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private final ISensorJpaRepository sensorRepository;
    private final ILeituraJpaRepository leituraRepository;

    @Autowired
    public SensorService(ISensorJpaRepository sensorRepository, ILeituraJpaRepository leituraRepository) {
        this.sensorRepository = sensorRepository;
        this.leituraRepository = leituraRepository;
    }

    public SensorResponseRecordDTO newSensor(SensorRequestRecordDTO sensorDTO){
        SensorEntity newSensor = new SensorEntity();
        newSensor.setName(sensorDTO.name());
        newSensor.setDescription(sensorDTO.description());
        newSensor.setType(sensorDTO.type());

        SensorEntity savedSensor = sensorRepository.save(newSensor);

        return SensorMapper.toResponseDTO(savedSensor);
    }

    public LeituraResponseRecordDTO newLeitura(Long id, LeituraRequestRecordDTO leituraDTO){
        SensorEntity sensor = sensorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor não encontrado"));

        LeituraEntity leitura = new LeituraEntity();

        leitura.setSensor(sensor);
        leitura.setValor(leituraDTO.valor());
        leitura.setTimestamp(leituraDTO.timestamp());

        LeituraEntity savedLeitura = leituraRepository.save(leitura);
        return LeituraMapper.toResponseDTO(savedLeitura);
    }

    public List<SensorResponseRecordDTO> listSensors(){
        List<SensorEntity> sensores =  sensorRepository.findAll();
        return sensores.stream().map(SensorMapper::toResponseDTO).collect(Collectors.toList());
    }

    public LeituraResponseRecordDTO getLeitura(Long id){
        LeituraEntity leitura = leituraRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Leitura não encontrada."));
        return LeituraMapper.toResponseDTO(leitura);
    }
}
