package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.exception.ClienteNaoEncontradoException;
import com.er7system.er7bank.domain.repository.ClienteRepositoryImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepositoryImpl clienteRepositoryImpl;

    public ClienteService(ClienteRepositoryImpl clienteRepositoryImpl) {
        this.clienteRepositoryImpl = clienteRepositoryImpl;
    }

    public Cliente criar(Cliente cliente) {

        return clienteRepositoryImpl.save(cliente);
    }

    public Cliente buscar(Long idCliente) {
        return clienteRepositoryImpl.findById(idCliente).orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));
    }

    public List<Cliente> listar() {
        return clienteRepositoryImpl.findAll();
    }

    public Cliente atualizar(Long idCliente, Cliente cliente) {
        Cliente clienteDB = buscar(idCliente);
        BeanUtils.copyProperties(cliente, clienteDB, "id");
        return clienteRepositoryImpl.save(clienteDB);
    }

    // TODO: Alterar remocao fisica para remocao logica
    public void deletar(Long idCliente) {
        clienteRepositoryImpl.deleteById(idCliente);
    }
}
