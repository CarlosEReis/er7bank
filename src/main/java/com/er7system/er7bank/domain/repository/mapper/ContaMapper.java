package com.er7system.er7bank.domain.repository.mapper;

import com.er7system.er7bank.domain.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaMapper implements RowMapper<Conta> {

    @Override
    public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoConta tipoConta = TipoConta.valueOf(rs.getString("tipo_conta"));

        if (TipoConta.CORRENTE.equals(tipoConta)){
            ContaCorrente conta = new ContaCorrente();
            conta.setTaxaManutencao(rs.getBigDecimal("taxa_manutencao"));
            rowMapperPropertiesCommon(rs, conta, tipoConta);
            return conta;
        }
        if (TipoConta.POUPANCA.equals(tipoConta)){
            ContaPoupanca conta = new ContaPoupanca();
            conta.setTaxaRendimento(rs.getFloat("taxa_rendimento"));
            rowMapperPropertiesCommon(rs, conta, tipoConta);
            return conta;
        }
        throw new IllegalArgumentException("Tipo de conta inválido: " + tipoConta);
    }

    private void rowMapperPropertiesCommon(ResultSet rs, Conta conta, TipoConta tipoConta) throws SQLException {
        Cliente cliente = new Cliente();
        conta.setNumero(rs.getInt("numero"));
        conta.setSaldo(rs.getBigDecimal("saldo"));
        conta.setUltimaMovimentacao(rs.getTimestamp("ultima_movimentacao").toLocalDateTime());
        conta.setTipoConta(tipoConta);
        conta.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
        conta.setDataAtualizacao(rs.getTimestamp("data_atualizacao").toLocalDateTime());
        cliente.setId(rs.getLong("cliente_id"));
        conta.setCliente(cliente);
    }
}
