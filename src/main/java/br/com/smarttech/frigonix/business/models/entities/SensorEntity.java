package br.com.smarttech.frigonix.business.models.entities;

import br.com.smarttech.frigonix.infrastructures.enums.TipoSensor;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_sensores")
public class SensorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sensor_id")
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private TipoSensor type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TipoSensor getType() {
        return type;
    }

    public void setType(TipoSensor type) {
        this.type = type;
    }
}
