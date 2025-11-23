package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.ManutencaoService;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoResponseRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencao")
public class ManutencaoController {
    private final ManutencaoService manutencaoService;

    @Autowired
    public ManutencaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }

    @PostMapping
    public ResponseEntity<ManutencaoResponseRecordDTO> create(@RequestBody ManutencaoRequestRecordDTO manutencaoRequestDTO) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.createManutencao(manutencaoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencao);
    }

    @GetMapping("/{manutencaoId}")
    public ResponseEntity<ManutencaoResponseRecordDTO> getManutencao(@PathVariable Long manutencaoId) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.getManutencao(manutencaoId);
        return ResponseEntity.ok(manutencao);
    }

    @GetMapping
    public ResponseEntity<List<ManutencaoResponseRecordDTO>> getManutencoes() {
        List<ManutencaoResponseRecordDTO> manutencoes = manutencaoService.getManutencoes();
        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/ativa")
    public ResponseEntity<List<ManutencaoResponseRecordDTO>> getManutencoesAtivas() {
        List<ManutencaoResponseRecordDTO> manutencoes = manutencaoService.getManutencoesAtivas();
        return ResponseEntity.ok(manutencoes);
    }

    @PatchMapping("{manutencaoId}")
    public ResponseEntity<ManutencaoResponseRecordDTO> updateManutencao(@PathVariable Long manutencaoId, @RequestBody ManutencaoRequestRecordDTO manutencaoRequestDTO) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.updateManutencao(manutencaoId, manutencaoRequestDTO);
        return ResponseEntity.ok(manutencao);
    }

    @PostMapping("{manutencaoId}")
    public ResponseEntity<Void> concluirManutencao(@PathVariable Long manutencaoId) {
        manutencaoService.concluirManutencao(manutencaoId);
        return ResponseEntity.ok().build();
    }
}
