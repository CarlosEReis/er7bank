package com.er7system.er7bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public abstract class Conta {

    private Integer numero;
    protected BigDecimal saldo = BigDecimal.ZERO;
    private LocalDateTime ultimaMovimentacao = LocalDateTime.now();
    private Cliente cliente;
    private TipoConta tipoConta;

    /*
    @OrderBy("id DESC")
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
    private List<Transacao> transacoes;*/

    //private List<String> chavesPix;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Conta(){}

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        //this.transacoes = new ArrayList<>();
        //this.chavesPix = new ArrayList<>();
    }

    public abstract void sacar(BigDecimal valor);
    public abstract void depositar(BigDecimal valor);
    public abstract void transferir(BigDecimal valor, Conta contaDestino);
    public abstract void aplicaTaxaBase();

    public void setNumero(int i) {
        this.numero = i;
    }

    public Integer getNumero() {
        return numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getUltimaMovimentacao() {
        return ultimaMovimentacao;
    }

    public void atualizaDataMovimentacao() {
        ultimaMovimentacao = LocalDateTime.now();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    /*public List<Transacao> getTransacoes() {
        return transacoes;
    }*/

    /*public List<String> getChavesPix() {
        return chavesPix;
    }*/

    public void pix(BigDecimal valor, String chave) {}

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void debitar(BigDecimal valor) {
        sacar(valor);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void setUltimaMovimentacao(LocalDateTime ultimaMovimentacao) {
        this.ultimaMovimentacao = ultimaMovimentacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
