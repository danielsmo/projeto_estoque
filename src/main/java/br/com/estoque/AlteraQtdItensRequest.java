package br.com.estoque;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AlteraQtdItensRequest(
        @NotNull @Positive Integer quantidade) {
}
