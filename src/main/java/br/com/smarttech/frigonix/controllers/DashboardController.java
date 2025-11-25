package br.com.smarttech.frigonix.controllers;

import br.com.smarttech.frigonix.business.services.DashboardService;
import br.com.smarttech.frigonix.controllers.dtos.PerformanceResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ResumoResponseRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resume")
    @PreAuthorize("hasAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<ResumoResponseRecordDTO> resume() {
        ResumoResponseRecordDTO resumo = dashboardService.getResume();
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/performance")
    @PreAuthorize("hasAuthority('SCOPE_VISUALIZAR')")
    public ResponseEntity<List<PerformanceResponseRecordDTO>> performance() {
        List<PerformanceResponseRecordDTO> performance = dashboardService.getPerformance();
        return ResponseEntity.ok(performance);
    }
}
