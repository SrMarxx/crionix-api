package br.com.smarttech.frigonix.business.models.repositories;

import br.com.smarttech.frigonix.business.models.entities.LeituraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILeituraJpaRepository extends JpaRepository<LeituraEntity, Long> {
    @Query("SELECT l FROM LeituraEntity l JOIN FETCH l.sensor WHERE l.timestamp BETWEEN :start AND :end")
    List<LeituraEntity> findReadingsWithSensorBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    Optional<LeituraEntity> findTopByOrderByTimestampDesc();

    Optional<LeituraEntity> findTopBySensorIdOrderByTimestampDesc(Long sensorId);

    @Query("""
        SELECT l FROM LeituraEntity l
        WHERE l.timestamp = (
            SELECT MAX(l2.timestamp)
            FROM LeituraEntity l2
            WHERE l2.sensor.id = l.sensor.id
        )
        """)
    List<LeituraEntity> findLatestReadingPerSensor();
}
