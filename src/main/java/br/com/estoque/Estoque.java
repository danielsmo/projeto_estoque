package br.com.estoque;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantidade;

    @Column(nullable = false)
    @Positive
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    @Positive
    private BigDecimal valorTotal;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate registradoEm;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    public Estoque(@NotBlank String nome, @NotBlank String descricao, @NotNull Categoria categoria,
                   @NotNull Integer quantidade, @NotNull BigDecimal valorUnitario) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = calculaValorTotal();
    }


    private BigDecimal calculaValorTotal(){
        return this.valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public void adicionaQuantidade(Integer qtd){
        this.quantidade += qtd;
        this.valorTotal = calculaValorTotal();

    }

    public void retiraQuantidade(Integer qtd) {
        if (qtd > this.quantidade) {
            throw new ApiErrorException("Quantidade maior que estoque");
        }

        quantidade -= qtd;
        this.valorTotal = calculaValorTotal();

        if (this.quantidade <= 10){
            System.out.println("Estoque abaixo de 10 peças, favor repor imediatamente");
        }
    }
}
