package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.SensorEntity;
import br.com.smarttech.frigonix.business.models.repositories.ISensorJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.SensorRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.mappers.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorService {
    private final ISensorJpaRepository sensorRepository;

    @Autowired
    public SensorService(ISensorJpaRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public SensorResponseRecordDTO newSensor(SensorRequestRecordDTO sensorDTO){
        SensorEntity newSensor = new SensorEntity();
        newSensor.setName(sensorDTO.name());
        newSensor.setDescription(sensorDTO.description());
        newSensor.setType(sensorDTO.type());

        SensorEntity savedSensor = sensorRepository.save(newSensor);

        return SensorMapper.toResponseDTO(savedSensor);
    }
}
