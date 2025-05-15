package com.er7system.er7bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {

    private Integer id;
    private String codigo;
    private LocalDateTime data;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private Conta conta;

    public Transacao(Conta conta, BigDecimal valor, TipoTransacao tipo) {
        this.conta = conta;
        this.data = LocalDateTime.now();
        this.valor = valor;
        this.tipoTransacao = tipo;
    }

    public Transacao(String codigo, BigDecimal valor, LocalDateTime data, TipoTransacao tipoTransacao) {
        this(null, valor, tipoTransacao);
        this.data = data;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() {
        return tipoTransacao;
    }

    public String getDescricao() {
        return getTipo().getDescricao();
    }

    public Conta getConta() {
        return conta;
    }

    public TipoOperacao getMovimentacao() {
        return tipoTransacao.getTipoOperacao();
    }

}
