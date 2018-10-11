package br.com.agibank.appanalisedados.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Venda {
    public static final String LAYOUT = "003";

    public Venda() {
        this.itens = new ArrayList<>();
        this.errosImportacao = new ArrayList<>();
    }

    public Venda(String erroImportacao){
        this();
        this.errosImportacao.add(erroImportacao);
    }

    public Venda(Integer codigo, String vendedor) {
        this();
        this.codigo = codigo;
        this.vendedor = vendedor;
    }

    public Venda(Integer codigo, String vendedor, List<Item> itens) {
        this(codigo, vendedor);

        this.itens = itens;

        List<String> errosItens = itens.stream()
                .map(i -> i.getErroImportacao())
                .filter(e -> e != null)
                .collect(Collectors.toList());

        if(errosItens != null && errosItens.size() > 0){
            this.errosImportacao = errosItens;
        }
    }

    private Integer codigo;
    private String vendedor;
    private List<Item> itens;
    private List<String> errosImportacao;

    public Integer getCodigo() {
        return codigo;
    }

    public String getVendedor() {
        return vendedor;
    }

    public List<String> getErrosImportacao() {
        return errosImportacao;
    }

    public Double obterValorTotal() {
        if (this.itens == null || this.itens.size() == 0) {
            return 0D;
        }

        Double result = itens.stream().mapToDouble(p -> p.obterValorTotal()).sum();

        return result;
    }

    public void adicionarItem(Item item) {
        this.itens.add(item);
    }
}
