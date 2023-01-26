package br.com.estoque;

import java.math.BigDecimal;

public class ItemResponse {

    public final String nome;
    public final String descricao;
    public final String categoria;
    public final Integer quantidade;

    public final BigDecimal valorUnitario;

    public final BigDecimal valorTotal;

    public ItemResponse(Estoque estoque) {
        this.nome = estoque.getNome();
        this.descricao = estoque.getDescricao();
        this.categoria = estoque.getDescricao();
        this.quantidade = estoque.getQuantidade();
        this.valorUnitario = estoque.getValorUnitario();
        this.valorTotal = estoque.getValorTotal();
    }
}
