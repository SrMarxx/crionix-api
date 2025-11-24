package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.ManutencaoEntity;
import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IManutencaoJpaRepository extends JpaRepository<ManutencaoEntity, Long> {
    List<ManutencaoEntity> findByAtivoTrue();
    long countByAtivoTrue();
    @Query("SELECT m.conclusao, COUNT(m) " +
            "FROM ManutencaoEntity m " +
            "WHERE m.dataConclusao BETWEEN :startDate AND :endDate " +
            "GROUP BY m.conclusao")
    List<Object[]> countManutencoesByConclusaoInPeriod(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    long countByPrioridadeAndAtivoTrue(Prioridade prioridade);
    @Query("SELECT COUNT(m) FROM ManutencaoEntity m WHERE m.ativo = false AND m.dataConclusao BETWEEN :startDate AND :endDate")
    long countFechadasNoPeriodo(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    @Query("SELECT COUNT(m) FROM ManutencaoEntity m WHERE m.conclusao = 'FALHA' AND m.dataConclusao BETWEEN :startDate AND :endDate")
    long countFalhasNoPeriodo(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
