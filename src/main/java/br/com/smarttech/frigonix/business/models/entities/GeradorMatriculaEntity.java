package br.com.smarttech.frigonix.business.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gerador_matricula")
public class GeradorMatriculaEntity {

    @Id
    private Integer ano;

    private Long ultimoSequencial;

    public GeradorMatriculaEntity() {
    }

    public GeradorMatriculaEntity(Integer ano) {
        this.ano = ano;
        this.ultimoSequencial = 0L;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getUltimoSequencial() {
        return ultimoSequencial;
    }

    public void setUltimoSequencial(Long ultimoSequencial) {
        this.ultimoSequencial = ultimoSequencial;
    }

    public Long incrementarEObter() {
        this.ultimoSequencial++;
        return this.ultimoSequencial;
    }
}
