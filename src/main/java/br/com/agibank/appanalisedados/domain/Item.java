package br.com.agibank.appanalisedados.domain;

public class Item {
    public Item(String erroImportacao){
        this.erroImportacao = erroImportacao;
    }

    public Item(int codigo, int quantidade, Double valor) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    private Integer codigo;
    private Integer quantidade;
    private Double valor;
    private String erroImportacao;

    public String getErroImportacao() {
        return erroImportacao;
    }

    public Double obterValorTotal() {
        return this.valor * this.quantidade;
    }
}
