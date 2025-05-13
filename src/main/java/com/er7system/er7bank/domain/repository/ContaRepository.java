package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaRepository {

    List<Conta> findByClienteId(Long clienteId);
    Conta save(Conta conta);
    Optional<Conta> findById(Integer idConta);
    List<Conta> findAll();
}
