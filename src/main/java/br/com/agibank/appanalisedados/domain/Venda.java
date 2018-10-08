package br.com.agibank.appanalisedados.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Venda {
    public static final String LAYOUT = "003";
    private static final int QUANTIDADECOLUNAS = 4;

    public Venda(){
        this.itens = new ArrayList<>();
        this.errosImportacao = new ArrayList<>();
    }

    public Venda(int codigo, String vendedor){
        this();
        this.codigo = codigo;
        this.vendedor = vendedor;
    }

    private Integer codigo;
    private String vendedor;
    private List<Item> itens;
    private List<String> errosImportacao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public List<String> getErrosImportacao() {
        return errosImportacao;
    }

    public void setErrosImportacao(List<String> errosImportacao) {
        this.errosImportacao = errosImportacao;
    }

    public Venda(String[] linhaArquivo) {
        this();
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != ""){
            this.errosImportacao.add(erro);
            return;
        }

        String[] itens = this.obterItensSplit(linhaArquivo[2]);

        if (itens == null || itens.length == 0){
            this.errosImportacao.add("Venda não possui itens.");
            return;
        }

        Arrays.stream(itens).forEach(this::adicionarItem);

        if (this.errosImportacao != null && this.errosImportacao.size() > 0){
            return;
        }

        try{
            this.codigo = Integer.parseInt(linhaArquivo[1]);
            this.vendedor = linhaArquivo[3];
        }catch (Exception ex){
            this.errosImportacao.add("Erro ao converter valores.");
        }
    }

    public Double obterValorTotal(){
        if(this.itens == null || this.itens.size() == 0){
            return 0D;
        }

        Double result = itens.stream().mapToDouble(p -> p.obterValorTotal()).sum();

        return result;
    }

    public void adicionarItem(Item item){
        this.itens.add(item);
    }

    public static Venda build(int codigo, String vendedor){
        return new Venda(codigo, vendedor);
    }

    private String validarLinhaArquivo(String[] linhaArquivo) {
        if(linhaArquivo == null || linhaArquivo.length == 0){
            return "Linha em branco";
        }

        if(linhaArquivo.length != this.QUANTIDADECOLUNAS){
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }

    private String[] obterItensSplit(String itensLinha) {
        if(itensLinha == null || itensLinha == ""){
            return null;
        }

        itensLinha = itensLinha.replace("[", "").replace("]","");
        String[] itens = itensLinha.split(",");

        return itens;
    }

    private void adicionarItem(String itemSource){
        if(itemSource == null || itemSource == ""){
            this.errosImportacao.add("Item vazio");
            return;
        }

        Item item = new Item(itemSource);

        if(item.getErroImportacao() == null || item.getErroImportacao() == "") {
            this.adicionarItem(item);
        }else{
            this.errosImportacao.add(item.getErroImportacao());
        }
    }
}
