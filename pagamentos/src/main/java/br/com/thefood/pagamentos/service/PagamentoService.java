package br.com.thefood.pagamentos.service;

//import br.com.thefood.pagamentos.http.PedidoClient;
import br.com.thefood.pagamentos.dto.PagamentoDto;
import br.com.thefood.pagamentos.http.PedidoClient;
import br.com.thefood.pagamentos.model.Pagamento;
import br.com.thefood.pagamentos.model.Status;
import br.com.thefood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
   private PedidoClient pedido;


    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        Page<Pagamento> pagamentosPage = pagamentoRepository.findAll(paginacao);
        return pagamentosPage.map(pagamento -> modelMapper.map(pagamento, PagamentoDto.class));
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        var pagamento =  modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        System.out.println("O pagamnento que tá chegando na requisição é " + pagamento.getCodigo());
        repository.save(pagamento);
       return modelMapper.map(pagamento, PagamentoDto.class);
       }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        var pagamento =  modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        repository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());
    }


    public void alteraStatus(Long id) {
        Optional<Pagamento> pagamento = repository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());

    }


}
