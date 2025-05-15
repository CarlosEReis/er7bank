package com.er7system.er7bank.domain.repository;

import com.er7system.er7bank.domain.model.TipoTransacao;
import com.er7system.er7bank.domain.model.Transacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TransacaoRepositoryImpl implements TransacaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransacaoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Transacao transacao) {
        String sql = "INSERT INTO transacao (conta_numero, valor, data, descricao, tipo) VALUES (?, ?, ?, ?, ?) RETURNING id";
        jdbcTemplate.queryForObject(sql,
            Long.class,
            transacao.getConta().getNumero(),
            transacao.getValor(),
            Timestamp.valueOf(transacao.getData()),
            transacao.getTipo().name(),
            transacao.getTipo().getTipoOperacao().name()
        );
    }

    @Override
    public List<Transacao> findByContaId(Long idConta) {
        String sql = "SELECT * FROM transacao t WHERE t.conta_numero = ? ORDER BY id DESC";
        return jdbcTemplate.query(sql,
            (rs, rowNum) -> new Transacao(
                rs.getString("codigo"),
                rs.getBigDecimal("valor"),
                rs.getTimestamp("data").toLocalDateTime(),
                TipoTransacao.valueOf(rs.getString("descricao"))
            ),
            idConta
        );
    }
}
