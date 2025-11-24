package br.com.smarttech.frigonix.business.models.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registros")
public class RegistroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    LocalDateTime dataRegistro;
    Long quantidadeMaquinas = 0L;
    Long quantidadePessoas = 0L;
    Long quantidadeManutencoesAbertas = 0L;
    Long quantidadeManutencoesFechadas = 0L;
    Long quantidadeSucesso = 0L;
    Long quantidadeFalha = 0L;
    Long quantidadeAdiamento = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Long getQuantidadeMaquinas() {
        return quantidadeMaquinas;
    }

    public void setQuantidadeMaquinas(Long quantidadeMaquinas) {
        this.quantidadeMaquinas = quantidadeMaquinas;
    }

    public Long getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(Long quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public Long getQuantidadeManutencoesAbertas() {
        return quantidadeManutencoesAbertas;
    }

    public void setQuantidadeManutencoesAbertas(Long quantidadeManutencoesAbertas) {
        this.quantidadeManutencoesAbertas = quantidadeManutencoesAbertas;
    }

    public Long getQuantidadeManutencoesFechadas() {
        return quantidadeManutencoesFechadas;
    }

    public void setQuantidadeManutencoesFechadas(Long quantidadeManutencoesFechadas) {
        this.quantidadeManutencoesFechadas = quantidadeManutencoesFechadas;
    }


    public Long getQuantidadeSucesso() {
        return quantidadeSucesso;
    }

    public void setQuantidadeSucesso(Long quantidadeSucesso) {
        this.quantidadeSucesso = quantidadeSucesso;
    }

    public Long getQuantidadeFalha() {
        return quantidadeFalha;
    }

    public void setQuantidadeFalha(Long quantidadeFalha) {
        this.quantidadeFalha = quantidadeFalha;
    }

    public Long getQuantidadeAdiamento() {
        return quantidadeAdiamento;
    }

    public void setQuantidadeAdiamento(Long quantidadeAdiamento) {
        this.quantidadeAdiamento = quantidadeAdiamento;
    }
}
