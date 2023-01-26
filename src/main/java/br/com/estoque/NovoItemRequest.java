package br.com.estoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NovoItemRequest(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotBlank String categoria,
        @Positive @NotNull Integer quantidade,
        @Positive @NotNull BigDecimal preco
) {
    public Estoque toModel(){
        return new Estoque(this.nome, this.descricao,Categoria.valueOf(this.categoria),this.quantidade,this.preco);
    }
}
