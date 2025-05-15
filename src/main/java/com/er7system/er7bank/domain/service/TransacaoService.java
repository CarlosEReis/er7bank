package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.model.Conta;
import com.er7system.er7bank.domain.model.TipoTransacao;
import com.er7system.er7bank.domain.model.Transacao;
import com.er7system.er7bank.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }

    public void registrarTransferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        registrar(new Transacao(contaOrigem, valor, TipoTransacao.TRANSFERENCIA_ENVIADA));
        registrar(new Transacao(contaDestino, valor, TipoTransacao.TRANSFERENCIA_RECEBIDA));
    }

    public List<Transacao> buscaTransacoesPorIdConta(Long idConta) {
        return transacaoRepository.findByContaId(idConta);
    }

    public void registrar(Transacao transacao) {
        transacaoRepository.save(transacao);
    }
}
