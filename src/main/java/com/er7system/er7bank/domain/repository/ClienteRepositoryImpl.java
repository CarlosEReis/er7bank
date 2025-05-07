package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.exception.ClienteNaoEncontradoException;
import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.repository.mapper.ClienteMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClienteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente save(Cliente cliente) {
        return (cliente.getId() == null) ? persist(cliente) : merge(cliente);
    }

    private Cliente persist(Cliente cliente) {
        String sql = """
        INSERT INTO cliente
            (nome, tipo, cpf, data_nascimento,
             logradouro, numero, complemento, bairro, cep, cidade, uf,
             data_criacao, data_atualizacao) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id""";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Long id = jdbcTemplate.queryForObject(sql, Long.class,
            cliente.getNome(),
            cliente.getTipo().toString(),
            cliente.getCpf(),
            cliente.getDataNascimento(),
                cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getNumero(),
                cliente.getEndereco().getComplemento(),
                cliente.getEndereco().getBairro(),
                cliente.getEndereco().getCep(),
                cliente.getEndereco().getCidade(),
                cliente.getEndereco().getUf(),
                timestamp,
                timestamp);

        cliente.setId(id);
        cliente.setDataCriacao(timestamp.toLocalDateTime());
        cliente.setDataAtualizacao(timestamp.toLocalDateTime());
        return cliente;
    }


    private Cliente merge(Cliente cliente){
        String sql = """
            UPDATE cliente SET
               nome = ?, tipo = ?, cpf = ?, data_nascimento = ?,
               logradouro = ?, numero = ?, complemento = ?, bairro = ?, cep = ?, cidade = ?, uf = ?,
               data_atualizacao = ?
            WHERE id = ?""";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        int linhasEfetadas = jdbcTemplate.update(sql,
                cliente.getNome(),
                cliente.getTipo().toString(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getNumero(),
                cliente.getEndereco().getComplemento(),
                cliente.getEndereco().getBairro(),
                cliente.getEndereco().getCep(),
                cliente.getEndereco().getCidade(),
                cliente.getEndereco().getUf(),
                timestamp,
                cliente.getId());

        if (linhasEfetadas == 0)
            throw new ClienteNaoEncontradoException(cliente.getId());

        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long idCliente) {
        String sql = "SELECT * FROM cliente c WHERE c.id = ?";
        return jdbcTemplate.query(sql, new ClienteMapper(), idCliente).stream().findFirst();
    }

    @Override
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, new ClienteMapper());
    }

    @Override
    public void deleteById(Long idCliente) {
        String  sql = "DELETE FROM cliente WHERE id = ?";
        int linhasEfetadas = jdbcTemplate.update(sql, idCliente);
        if (linhasEfetadas == 0)
            throw new ClienteNaoEncontradoException(idCliente);
    }
}
