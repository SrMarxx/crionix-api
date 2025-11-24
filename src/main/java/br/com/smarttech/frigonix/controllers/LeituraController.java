package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.SensorService;
import br.com.smarttech.frigonix.controllers.dtos.LeituraRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/leitura")
public class LeituraController {
    private final SensorService sensorService;

    @Autowired
    public LeituraController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/new/{sensorId}")
    public ResponseEntity<Void> novaLeitura(@PathVariable Long id, @RequestBody @Valid LeituraRequestRecordDTO leituraRequestDTO){
        LeituraResponseRecordDTO newLeituraDTO = sensorService.newLeitura(id, leituraRequestDTO);
        URI location = URI.create("/api/leitura/" + newLeituraDTO.id());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("{leituraId}")
    public ResponseEntity<LeituraResponseRecordDTO> getLeitura(@PathVariable Long leituraId){
        LeituraResponseRecordDTO leitura = sensorService.getLeitura(leituraId);
        return ResponseEntity.ok(leitura);
    }
}
