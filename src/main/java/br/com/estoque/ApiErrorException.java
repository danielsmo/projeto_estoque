package br.com.estoque;

public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String quantidadeMaiorQueEstoque) {
        super(quantidadeMaiorQueEstoque);
    }
}
