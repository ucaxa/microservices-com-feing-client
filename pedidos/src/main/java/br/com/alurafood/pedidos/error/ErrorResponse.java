package br.com.alurafood.pedidos.error;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private String mensagem;
    private int status;
    private LocalDateTime data;

    // Construtor, getters e setters
    public ErrorResponse(String mensagem, int status, LocalDateTime data) {
        this.mensagem = mensagem;
        this.status = status;
        this.data =data;
    }


}
