package com.er7system.er7bank.domain.repository.mapper;

import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.model.Endereco;
import com.er7system.er7bank.domain.model.TipoCliente;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteMapper  implements RowMapper<Cliente> {

    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        var cliente = new Cliente();
        var endereco = new Endereco();

        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        cliente.setTipo(TipoCliente.valueOf(rs.getString("tipo")));

        endereco.setLogradouro(rs.getString("logradouro"));
        endereco.setNumero(rs.getString("numero"));
        endereco.setComplemento(rs.getString("complemento"));
        endereco.setBairro(rs.getString("bairro"));
        endereco.setCep(rs.getString("cep"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setUf(rs.getString("uf"));

        cliente.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
        cliente.setDataAtualizacao(rs.getTimestamp("data_atualizacao").toLocalDateTime());

        cliente.setEndereco(endereco);
        return cliente;
    }
}
