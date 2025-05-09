package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.error.ErrorResponse;
import br.com.alurafood.pedidos.service.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

        @Autowired
        private PedidoService service;

        @GetMapping()
        public List<PedidoDto> listarTodos() {
            return service.obterTodos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> listarPorId(@PathVariable @NotNull Long id) {
            try {
                PedidoDto dto = service.obterPorId(id);
                return ResponseEntity.ok(dto);
            } catch (EntityNotFoundException ex) {
                ErrorResponse erro = new ErrorResponse(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
            }
          //  PedidoDto dto = service.obterPorId(id);

           // return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<PedidoDto> realizaPedido(@RequestBody @Valid PedidoDto dto, UriComponentsBuilder uriBuilder) {
            PedidoDto pedidoRealizado = service.criarPedido(dto);

            URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable Long id, @RequestBody StatusDto status){
           PedidoDto dto = service.atualizaStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pago")
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull Long id) {
            service.aprovaPagamentoPedido(id);

            return ResponseEntity.ok().build();

        }

    @DeleteMapping("/{id}")
    public ResponseEntity<PedidoDto> remover(@PathVariable @NotNull Long id) {
        service.excluirPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/porta")
    public String retornaPorta(@Value("${local.server.port}") String porta){
        return String.format("Requisição respondida pela instância executando na porta %s", porta);
    }



}
