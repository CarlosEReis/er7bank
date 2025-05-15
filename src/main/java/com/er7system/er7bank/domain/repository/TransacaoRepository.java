package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Transacao;

import java.util.List;

public interface TransacaoRepository {

    void save(Transacao transacao);

    List<Transacao> findByContaId(Long idConta);
}
