package br.com.agibank.appanalisedados.domain;

public class Cliente {
    public static final String LAYOUT = "002";

    public Cliente(String cnpj, String nome, String areaAtuacao, String erroImportacao) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
        this.erroImportacao = erroImportacao;
    }

    public Cliente(String cnpj, String nome, String areaAtuacao) {
        this(cnpj, nome, areaAtuacao, null);
    }

    public Cliente(String erroImportacao){
        this(null, null, null, erroImportacao);
    }

    private String cnpj;
    private String nome;
    private String areaAtuacao;
    private String erroImportacao;

    public String getCnpj() {
        return cnpj;
    }

    public String getErroImportacao() {
        return erroImportacao;
    }
}
