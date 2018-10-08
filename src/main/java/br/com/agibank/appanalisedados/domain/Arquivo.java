package br.com.agibank.appanalisedados.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Arquivo {
    public Arquivo() {
        this.clientes = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.vendedores = new ArrayList<>();
        this.erros = new ArrayList<>();
    }

    public Arquivo(List<String[]> arquivo) {
        this();
        if (arquivo == null || arquivo.size() == 0) {
            return;
        }

        for (int i = 0; i < arquivo.size(); i++) {
            String[] linhaArquivo = arquivo.get(i);

            String erro = this.validarLinhaArquivo(linhaArquivo);

            if (erro != null && erro != "") {
                this.adicionarErro(i, erro);
                continue;
            }

            String layout = linhaArquivo[0];

            if (layout.equals(Cliente.LAYOUT)) {
                Cliente cliente = new Cliente(linhaArquivo);
                this.adicionarCliente(cliente, i);
            }

            if (layout.equals(Vendedor.LAYOUT)) {
                Vendedor vendedor = new Vendedor(linhaArquivo);
                this.adicionarVendedor(vendedor, i);
            }

            if (layout.equals(Venda.LAYOUT)) {
                Venda venda = new Venda(linhaArquivo);
                this.adicionarVenda(venda, i);
            }
        }
    }

    private List<Cliente> clientes;
    private List<Venda> vendas;
    private List<Vendedor> vendedores;
    private List<String> erros;

    public void adicionarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void adicionarCliente(Cliente cliente, int linha) {
        if (cliente == null) {
            return;
        }

        if (cliente.getErroImportacao() != null && cliente.getErroImportacao() != "") {
            this.adicionarErro(linha, cliente.getErroImportacao());
        } else {
            this.clientes.add(cliente);
        }
    }

    public void adicionarVenda(Venda venda) {
        this.vendas.add(venda);

    }

    public void adicionarVenda(Venda venda, int linha) {
        if (venda == null) {
            return;
        }

        if (venda.getErrosImportacao() != null && venda.getErrosImportacao().size() > 0) {
            this.adicionarErro(linha, String.join(",", venda.getErrosImportacao()));
        } else {
            this.vendas.add(venda);
        }
    }

    public void adicionarVendedor(Vendedor vendedor) {
        this.vendedores.add(vendedor);
    }

    public void adicionarVendedor(Vendedor vendedor, int linha) {
        if (vendedor == null) {
            return;
        }

        if (vendedor.getErroImportacao() != null && vendedor.getErroImportacao() != "") {
            this.adicionarErro(linha, vendedor.getErroImportacao());
        } else {
            this.vendedores.add(vendedor);
        }
    }

    public void adicionarErro(int linha, String erro) {
        int numeroLinha = linha + 1;

        this.erros.add("Erro na linha " + numeroLinha + " - " + erro);
    }

    public int obterQuantidadeClientes() {
        if (!this.possuiClientes()) {
            return 0;
        }

        Map<String, Long> clientesDistincts = clientes.stream()
                .collect(Collectors.groupingBy(p -> ((Cliente) p).getCnpj(), Collectors.counting()));

        return clientesDistincts.size();
    }

    public int obterQuantidadeVendedores() {
        if (!this.possuiVendedores()) {
            return 0;
        }

        Map<String, Long> vendedorDistinct = vendedores.stream()
                .collect(Collectors.groupingBy(p -> ((Vendedor) p).getCpf(), Collectors.counting()));

        return vendedorDistinct.size();
    }

    public int obterVendaMaisCara() {
        if (!this.possuiVendas()) {
            return 0;
        }

        Venda vendaMaisCara = vendas.stream()
                .max(Comparator.comparingDouble(p -> p.obterValorTotal()))
                .get();

        return vendaMaisCara.getCodigo();
    }

    public Vendedor obterPiorVendedor() {
        if (!this.possuiVendedores()) {
            return null;
        }

        if (!this.possuiVendas()) {
            return this.vendedores.stream().findFirst().get();
        }

        Vendedor vendedorSemVenda = this.obterVendedorSemVenda();

        if (vendedorSemVenda != null) {
            return vendedorSemVenda;
        }

        Venda vendaMaisBarata = vendas.stream()
                .min(Comparator.comparingDouble(p -> p.obterValorTotal()))
                .get();

        Vendedor piorVendedor = this.vendedores.stream()
                .filter(x -> x.getNome() == vendaMaisBarata.getVendedor())
                .findFirst()
                .get();

        return piorVendedor;
    }

    public List<String> obterRelatorio() {
        List<String> result = new ArrayList<>();

        int quantidadeClientes = this.obterQuantidadeClientes();
        int quantidadeVendedores = this.obterQuantidadeVendedores();
        int vendaMaisCaraId = this.obterVendaMaisCara();

        result.add("Quantidade de clientes no arquivo de entrada: " + quantidadeClientes);
        result.add("Quantidade de vendedores no arquivo de entrada: " + quantidadeVendedores);
        result.add("ID da venda mais cara: " + vendaMaisCaraId);

        Vendedor piorVendedor = this.obterPiorVendedor();

        if (piorVendedor != null) {
            result.add("O pior vendedor é: " + piorVendedor.getNome());
        }

        if (this.erros != null && this.erros.size() > 0) {
            result.add("Erros na importação do arquivo: " + String.join(",", this.erros));
        }

        return result;
    }

    private String validarLinhaArquivo(String[] linhaArquivo) {
        if (linhaArquivo == null || linhaArquivo.length == 0) {
            return "Linha em branco";
        }

        String layout = linhaArquivo[0];

        if (!this.layoutConhecido(layout)) {
            return "Layout " + layout + " não é um layout válido";
        }

        return null;
    }

    private boolean layoutConhecido(String layout) {
        List<String> layoutValidos = new ArrayList<>();

        layoutValidos.add(Venda.LAYOUT);
        layoutValidos.add(Vendedor.LAYOUT);
        layoutValidos.add(Cliente.LAYOUT);

        return layoutValidos.contains(layout);
    }

    private Vendedor obterVendedorSemVenda() {
        Set<String> nomeVendedoresComVenda = this.vendas.stream()
                .map(Venda::getVendedor)
                .collect(Collectors.toSet());

        List<Vendedor> vendedoresSemVenda = this.vendedores.stream()
                .filter(vendedor -> !nomeVendedoresComVenda.contains(vendedor.getNome()))
                .collect(Collectors.toList());

        if (vendedoresSemVenda == null || vendedoresSemVenda.size() == 0) {
            return null;
        }

        return vendedoresSemVenda.stream().findFirst().get();
    }

    private Boolean possuiVendas() {
        return this.vendas != null && this.vendas.size() > 0;
    }

    private Boolean possuiVendedores() {
        return this.vendedores != null && this.vendedores.size() > 0;
    }

    private Boolean possuiClientes() {
        return this.clientes != null && this.clientes.size() > 0;
    }
}
