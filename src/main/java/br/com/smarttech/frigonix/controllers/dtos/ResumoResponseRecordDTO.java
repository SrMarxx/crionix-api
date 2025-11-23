package br.com.smarttech.frigonix.controllers.dtos;

public record ResumoResponseRecordDTO(
    Equipamentos equipamentos,
    ManutencoesPendentes pendentes,
    ManutencoesConcluidas concluidas,
    TaxaFalha falhas
) {
    public record Equipamentos(
            Integer quantidadeAtual,
            Double vsMesAnterior
    ){}
    public record ManutencoesPendentes(
            Integer quantidadePendentes,
            Integer quantidadeUrgentes
    ){}
    public record ManutencoesConcluidas(
            Integer quantidadeAtual,
            Double vsMesAnterior
    ){}
    public record TaxaFalha(
            Double quantidadeFalha,
            Double vsMesAnterior
    ){}
}
