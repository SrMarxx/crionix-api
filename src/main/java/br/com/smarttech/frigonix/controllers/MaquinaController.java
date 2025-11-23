package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.MaquinaService;
import br.com.smarttech.frigonix.controllers.dtos.LeituraResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.MaquinaResponseRecordDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/maquinas")
public class MaquinaController {
    private final MaquinaService maquinaService;

    @Autowired
    public MaquinaController(MaquinaService maquinaService) {
        this.maquinaService = maquinaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<MaquinaResponseRecordDTO>> listarMaquinas(){
        List<MaquinaResponseRecordDTO> maquinas = maquinaService.findAll();
        return ResponseEntity.ok(maquinas);
    }

    @GetMapping("/{maquinaId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<LeituraResponseRecordDTO>> lastLeituras(@PathVariable Long maquinaId){
        List<LeituraResponseRecordDTO> leituras = maquinaService.getLeituras(maquinaId);
        return ResponseEntity.ok(leituras);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CRIAR')")
    public ResponseEntity<Void> criarMaquina(@RequestBody @Valid MaquinaRequestRecordDTO maquinaDTO){
        MaquinaResponseRecordDTO newMaquinaDTO = maquinaService.newMaquina(maquinaDTO);
        URI location = URI.create("/api/maquinas/" + newMaquinaDTO.id());
        return ResponseEntity.created(location).build();
    }
}
