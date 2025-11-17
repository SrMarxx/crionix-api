package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ILeituraJpaRepository extends JpaRepository<LeituraEntity, Long> {
    @Query("SELECT l FROM LeituraEntity l JOIN FETCH l.sensor WHERE l.timestamp BETWEEN :start AND :end")
    List<LeituraEntity> findReadingsWithSensorBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
