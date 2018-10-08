package br.com.agibank.appanalisedados.domain;

public class Item {
    private static final int QUANTIDADECOLUNAS = 3;

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

    private Integer codigo;
    private Integer quantidade;
    private Double valor;
    private String erroImportacao;

    public String getErroImportacao() {
        return erroImportacao;
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
