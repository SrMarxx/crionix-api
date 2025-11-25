package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.SensorService;
import br.com.smarttech.frigonix.controllers.dtos.LeituraRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.SensorResponseRecordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Void> criarSensor(@RequestBody @Valid SensorRequestRecordDTO sensorDTO){
        SensorResponseRecordDTO newSensorDTO = sensorService.newSensor(sensorDTO);
        URI location = URI.create("/api/sensores/" + newSensorDTO.id());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{sensorId}")
    @PreAuthorize("hasAuthority('SCOPE_CRIAR')")
    public ResponseEntity<SensorResponseRecordDTO> updateSensor(@PathVariable Long sensorId, @RequestBody SensorRequestRecordDTO sensorDTO){
        SensorResponseRecordDTO newSensorDTO = sensorService.updateSensor(sensorId, sensorDTO);
        return ResponseEntity.ok(newSensorDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<SensorResponseRecordDTO>> listarSensores(){
        List<SensorResponseRecordDTO> sensores = sensorService.listSensors();
        return ResponseEntity.ok(sensores);
    }
}
