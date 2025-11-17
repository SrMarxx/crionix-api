package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.SensorService;
import br.com.smarttech.frigonix.controllers.dtos.SensorRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorResponseRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CRIAR')")
    public ResponseEntity<Void> criarSensor(SensorRequestRecordDTO sensorDTO){
        SensorResponseRecordDTO newSensorDTO = sensorService.newSensor(sensorDTO);
        URI location = URI.create("/api/sensores/" + newSensorDTO.id());
        return ResponseEntity.created(location).build();
    }
}
