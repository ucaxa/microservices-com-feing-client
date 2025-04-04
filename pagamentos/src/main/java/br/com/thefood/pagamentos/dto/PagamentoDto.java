package br.com.thefood.pagamentos.dto;

import br.com.thefood.pagamentos.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDto {
    Long id;
    @NotNull
    @Positive
    BigDecimal valor;
    @NotBlank
    @Size(max=100) String nome;
    @NotBlank  @Size(max=19) String numero;
    @NotBlank  @Size(max=7)  String expiracao;
    @NotBlank  @Size(min=3, max=3)  String codigo;
    Status status;
    @NotNull  Long pedidoId;
    @NotNull  Long formaDePagamentoId;
}
