package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.ManutencaoService;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoConclusaoRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoResponseRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<ManutencaoResponseRecordDTO> create(@RequestBody ManutencaoRequestRecordDTO manutencaoRequestDTO) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.createManutencao(manutencaoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencao);
    }

    @GetMapping("/{manutencaoId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<ManutencaoResponseRecordDTO> getManutencao(@PathVariable Long manutencaoId) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.getManutencao(manutencaoId);
        return ResponseEntity.ok(manutencao);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<ManutencaoResponseRecordDTO>> getManutencoes() {
        List<ManutencaoResponseRecordDTO> manutencoes = manutencaoService.getManutencoes();
        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/ativa")
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<ManutencaoResponseRecordDTO>> getManutencoesAtivas() {
        List<ManutencaoResponseRecordDTO> manutencoes = manutencaoService.getManutencoesAtivas();
        return ResponseEntity.ok(manutencoes);
    }

    @PatchMapping("{manutencaoId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<ManutencaoResponseRecordDTO> updateManutencao(@PathVariable Long manutencaoId, @RequestBody ManutencaoRequestRecordDTO manutencaoRequestDTO) {
        ManutencaoResponseRecordDTO manutencao = manutencaoService.updateManutencao(manutencaoId, manutencaoRequestDTO);
        return ResponseEntity.ok(manutencao);
    }

    @PostMapping("{manutencaoId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<Void> concluirManutencao(@PathVariable Long manutencaoId, @RequestBody ManutencaoConclusaoRequestRecordDTO manutencaoConclusaoDTO) {
        manutencaoService.concluirManutencao(manutencaoId, manutencaoConclusaoDTO);
        return ResponseEntity.ok().build();
    }
}
