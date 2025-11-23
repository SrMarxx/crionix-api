package br.com.smarttech.frigonix.business.models.entities;

import br.com.smarttech.frigonix.infrastructures.enums.Prioridade;
import br.com.smarttech.frigonix.infrastructures.enums.TipoManutencao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_manutencoes")
public class ManutencaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manutencao_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maquina_id", nullable = false)
    MaquinaEntity maquina;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity colaborador;
    LocalDateTime dataCriacao;
    LocalDateTime dataLimite;
    LocalDateTime dataConclusao;
    Boolean ativo;
    String descricao;
    @Enumerated(EnumType.STRING)
    Prioridade prioridade;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    TipoManutencao tipoManutencao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MaquinaEntity getMaquina() {
        return maquina;
    }

    public void setMaquina(MaquinaEntity maquina) {
        this.maquina = maquina;
    }

    public UserEntity getColaborador() {
        return colaborador;
    }

    public void setColaborador(UserEntity colaborador) {
        this.colaborador = colaborador;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDateTime dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public TipoManutencao getTipoManutencao() {
        return tipoManutencao;
    }

    public void setTipoManutencao(TipoManutencao tipoManutencao) {
        this.tipoManutencao = tipoManutencao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}