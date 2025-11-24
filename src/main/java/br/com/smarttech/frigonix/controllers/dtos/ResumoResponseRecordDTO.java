package br.com.smarttech.frigonix.controllers.dtos;

public record ResumoResponseRecordDTO(
    Equipamentos equipamentos,
    ManutencoesPendentes pendentes,
    ManutencoesConcluidas concluidas,
    TaxaFalha falhas
) {
    public record Equipamentos(
            Long quantidadeAtual,
            Double vsMesAnterior
    ){}
    public record ManutencoesPendentes(
            Long quantidadePendentes,
            Long quantidadeUrgentes
    ){}
    public record ManutencoesConcluidas(
            Long quantidadeAtual,
            Double vsMesAnterior
    ){}
    public record TaxaFalha(
            Long quantidadeFalha,
            Double vsMesAnterior
    ){}
}
