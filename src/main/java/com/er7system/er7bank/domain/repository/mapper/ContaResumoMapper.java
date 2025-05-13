package com.er7system.er7bank.domain.repository.mapper;

import com.er7system.er7bank.domain.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaResumoMapper implements RowMapper<Conta> {

    @Override
    public Conta mapRow(ResultSet rs, int rowNum) throws SQLException {
        TipoConta tipoConta = TipoConta.valueOf(rs.getString("tipo_conta"));

        if (TipoConta.CORRENTE.equals(tipoConta)){
            ContaCorrente contaC = new ContaCorrente();
            contaC.setTaxaManutencao(rs.getBigDecimal("taxa_manutencao"));
            rowMapperPropertiesCommon(rs, contaC);
            return contaC;
        }
        if (TipoConta.POUPANCA.equals(tipoConta)){
            ContaPoupanca contaP = new ContaPoupanca();
            contaP.setTaxaRendimento(rs.getFloat("taxa_rendimento"));
            rowMapperPropertiesCommon(rs, contaP);
            return contaP;
        }
        throw new IllegalArgumentException("Tipo de conta inválido: " + tipoConta);
    }

    private static void rowMapperPropertiesCommon(ResultSet rs, Conta conta) throws SQLException {
        conta.setNumero(rs.getInt("numero"));
        conta.setSaldo(rs.getBigDecimal("saldo"));
        conta.setUltimaMovimentacao(rs.getTimestamp("ultima_movimentacao").toLocalDateTime());
        conta.setTipoConta(TipoConta.valueOf(rs.getString("tipo_conta")));
        conta.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());

        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("cliente_id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setTipo(TipoCliente.valueOf(rs.getString("tipo")));
        conta.setCliente(cliente);
    }
}
