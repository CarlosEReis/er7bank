package com.er7system.er7bank.domain.model;

public enum TipoTransacao {

    TRANSFERENCIA_RECEBIDA("Transferência Recebida", TipoOperacao.ENTRADA),
    TRANSFERENCIA_ENVIADA("Transferência Enviada", TipoOperacao.SAIDA),
    DEPOSITO_CAIXA_ELETRONICO("Depósito no caixa eletrônico Realizado", TipoOperacao.ENTRADA),
    SAQUE_CAIXA_ELETRONICO("Saque no caixa eletrônico Realizado", TipoOperacao.SAIDA),
    PAGAMENTO_FATURA("Pagamento de Fatura", TipoOperacao.SAIDA),
    COMPRA_DEBITO("Compra no Débito", TipoOperacao.SAIDA),
    COMPRA_CREDITO("Compra no Crédito", TipoOperacao.SAIDA),
    PIX_RECEBIDO("PIX Recebido", TipoOperacao.ENTRADA),
    PIX_ENVIADO("PIX Enviado", TipoOperacao.SAIDA),
    PAGAMENTO_PIX("Pagamento no PIX", TipoOperacao.SAIDA );

    private final String descricao;
    private final TipoOperacao tipoOperacao;

    TipoTransacao(String descricao, TipoOperacao tipoOperacao) {
        this.descricao = descricao;
        this.tipoOperacao = tipoOperacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }
}