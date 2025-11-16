package br.com.smarttech.frigonix.business.models.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_maquinas")
public class MaquinaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "maquina_id")
    private Long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EmpresaEntity empresa;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_maquinas_sensores",
            joinColumns = @JoinColumn(name = "maquina_id"),
            inverseJoinColumns = @JoinColumn (name = "sensor_id")
    )
    private Set<SensorEntity> sensors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpresaEntity getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
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

    public Set<SensorEntity> getSensors() {
        return sensors;
    }

    public void setSensors(Set<SensorEntity> sensors) {
        this.sensors = sensors;
    }
}
