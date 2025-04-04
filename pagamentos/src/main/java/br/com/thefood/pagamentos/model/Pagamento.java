package br.com.thefood.pagamentos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long pedidoId;
    private Long formaDePagamentoId;



  /*   public Pagamento(PagamentoDto pagamentoDto){
         this.valor= pagamentoDto.valor();
         this.nome=pagamentoDto.nome();
         this.numero=pagamentoDto.numero();
         this.expiracao=pagamentoDto.expiracao();
         this.codigo =pagamentoDto.codigo();
         this.status =pagamentoDto.status();
         this.pedidoId = pagamentoDto.pedidoId();
         this.formaDePagamentoId =pagamentoDto.formaDePagamentoId();
     }*/


}


