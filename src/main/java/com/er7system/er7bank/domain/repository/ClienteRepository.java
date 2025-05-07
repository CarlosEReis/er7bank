package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long idCliente);
    List<Cliente> findAll();
    void deleteById(Long idCliente);
}
