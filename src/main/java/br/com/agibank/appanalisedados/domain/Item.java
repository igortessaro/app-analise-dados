package br.com.agibank.appanalisedados.domain;

public class Item {
    private static final int QUANTIDADECOLUNAS = 3;
    private Integer codigo;
    private Integer quantidade;
    private Double valor;
    private String erroImportacao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getErroImportacao() {
        return erroImportacao;
    }

    public void setErroImportacao(String erroImportacao) {
        this.erroImportacao = erroImportacao;
    }

    public Item(String linhaArquivo) {
        String[] item = linhaArquivo.split("-");

        String erro = this.validarLinhaArquivo(item);

        if (erro != null && erro != ""){
            this.erroImportacao = erro;
            return;
        }

        try{
            this.codigo = Integer.parseInt(item[0]);
            this.quantidade = Integer.parseInt(item[1]);
            this.valor = Double.parseDouble(item[2]);
        }catch (Exception ex){
            this.erroImportacao = "Erro ao converter valores.";
            return;
        }
    }

    public Item(int codigo, int quantidade, Double valor) {
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Double obterValorTotal(){
        return this.valor * this.quantidade;
    }

    public String validarLinhaArquivo(String[] linhaArquivo) {
        if(linhaArquivo == null || linhaArquivo.length == 0){
            return "Registro não possui itens.";
        }

        if(linhaArquivo.length != this.QUANTIDADECOLUNAS){
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }
}
