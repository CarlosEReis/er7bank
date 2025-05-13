package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.exception.ClienteNaoEncontradoException;
import com.er7system.er7bank.domain.exception.ContaNaoEncontradaException;
import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.model.Conta;
import com.er7system.er7bank.domain.model.ContaCorrente;
import com.er7system.er7bank.domain.model.ContaPoupanca;
import com.er7system.er7bank.domain.repository.mapper.ContaMapper;
import com.er7system.er7bank.domain.repository.mapper.ContaResumoMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaRepositoryImpl implements ContaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ClienteRepositoryImpl clienteRepositoryImpl;

    public ContaRepositoryImpl(JdbcTemplate jdbcTemplate, ClienteRepositoryImpl clienteRepositoryImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.clienteRepositoryImpl = clienteRepositoryImpl;
    }

    @Override
    public List<Conta> findByClienteId(Long clienteId) {
        String sql = "SELECT * FROM conta WHERE cliente_id = ?";
        return jdbcTemplate.query(sql, new ContaMapper(), clienteId);
    }

    @Override
    public Conta save(Conta conta) {
        return (conta.getNumero() == null) ? persist(conta) : merge(conta);
    }

    private Conta merge(Conta conta) {
        String sql = """
                UPDATE conta SET
                    saldo = ?, cliente_id = ?, data_atualizacao = ?, ultima_movimentacao = ?, tipo_conta = ?, taxa_manutencao = ?, taxa_rendimento = ?
                WHERE numero = ?""";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        int linhasEfetadas = jdbcTemplate.update(sql,
                conta.getSaldo(),
                conta.getCliente().getId(),
                timestamp,
                timestamp,
                conta.getTipoConta().toString(),
                (conta instanceof ContaCorrente c) ? c.getTaxaManutencao() : null,
                (conta instanceof ContaPoupanca c) ? c.getTaxaRendimento() : null,
                conta.getNumero());

        if (linhasEfetadas == 0)
            throw new ContaNaoEncontradaException(conta.getNumero());
        return conta;
    }

    private Conta persist(Conta conta) {
        String sql = """
                INSERT INTO conta
                    (saldo, cliente_id, data_criacao, data_atualizacao, ultima_movimentacao, dtype, tipo_conta, taxa_manutencao, taxa_rendimento)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?  ) RETURNING numero""";

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        Integer numero = jdbcTemplate.queryForObject(sql, Integer.class,
                conta.getSaldo(),
                conta.getCliente().getId(),
                timestamp,
                timestamp,
                conta.getUltimaMovimentacao(),
                conta.getClass().getSimpleName(),
                conta.getTipoConta().toString(),
                (conta instanceof ContaCorrente c) ? c.getTaxaManutencao() : null,
                (conta instanceof ContaPoupanca c) ? c.getTaxaRendimento() : null);

        conta.setNumero(numero);
        return conta;
    }

    @Override
    public Optional<Conta> findById(Integer idConta) {
        String sql = "SELECT * FROM conta WHERE numero = ?";
        return jdbcTemplate.query(sql, new ContaMapper(), idConta)
            .stream()
            .findFirst()
            .map(this::adicionaCliente);
    }

    @Override
    public List<Conta> findAll() {
        String sql ="SELECT co.numero, co.saldo, co.ultima_movimentacao, co.tipo_conta, co.data_criacao, co.cliente_id, co.taxa_manutencao, co.taxa_rendimento, cl.nome, cl.cpf, cl.tipo FROM conta co left join cliente cl on co.cliente_id  = cl.id";
        return jdbcTemplate.query(sql, new ContaResumoMapper());
    }

    private Conta adicionaCliente(Conta conta) {
        Cliente cliente = clienteRepositoryImpl.findById(conta.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        conta.setCliente(cliente);
        return conta;
    }
}
