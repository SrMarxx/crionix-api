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
    @Column(name = "tensao_padrao")
    private Double tensaoPadrao;
    @Column(name = "tensao_variacao")
    private Double tensaoVariacao;
    @Column(name = "temperatura_padrao")
    private Double temperaturaPadrao;
    @Column(name = "temperatura_variacao")
    private Double temperaturaVariacao;
    @Column(name = "pressao_padrao")
    private Double pressaoPadrao;
    @Column(name = "pressao_variacao")
    private Double pressaoVariacao;
    @Column(name = "humidade_padrao")
    private Double humidadePadrao;
    @Column(name = "humidade_variacao")
    private Double humidadeVariacao;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_maquinas_sensores",
            joinColumns = @JoinColumn(name = "maquina_id"),
            inverseJoinColumns = @JoinColumn (name = "sensor_id")
    )
    private Set<SensorEntity> sensors;
    private Boolean ativo;

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

    public Set<SensorEntity> getSensors() {
        return sensors;
    }

    public void setSensors(Set<SensorEntity> sensors) {
        this.sensors = sensors;
    }

    public Double getTensaoPadrao() {
        return tensaoPadrao;
    }

    public void setTensaoPadrao(Double tensaoPadrao) {
        this.tensaoPadrao = tensaoPadrao;
    }

    public Double getTemperaturaPadrao() {
        return temperaturaPadrao;
    }

    public void setTemperaturaPadrao(Double temperaturaPadrao) {
        this.temperaturaPadrao = temperaturaPadrao;
    }

    public Double getPressaoPadrao() {
        return pressaoPadrao;
    }

    public void setPressaoPadrao(Double pressaoPadrao) {
        this.pressaoPadrao = pressaoPadrao;
    }

    public Double getHumidadePadrao() {
        return humidadePadrao;
    }

    public void setHumidadePadrao(Double humidadePadrao) {
        this.humidadePadrao = humidadePadrao;
    }

    public Double getTensaoVariacao() {
        return tensaoVariacao;
    }

    public void setTensaoVariacao(Double tensaoVariacao) {
        this.tensaoVariacao = tensaoVariacao;
    }

    public Double getTemperaturaVariacao() {
        return temperaturaVariacao;
    }

    public void setTemperaturaVariacao(Double temperaturaVariacao) {
        this.temperaturaVariacao = temperaturaVariacao;
    }

    public Double getPressaoVariacao() {
        return pressaoVariacao;
    }

    public void setPressaoVariacao(Double pressaoVariacao) {
        this.pressaoVariacao = pressaoVariacao;
    }

    public Double getHumidadeVariacao() {
        return humidadeVariacao;
    }

    public void setHumidadeVariacao(Double humidadeVariacao) {
        this.humidadeVariacao = humidadeVariacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
