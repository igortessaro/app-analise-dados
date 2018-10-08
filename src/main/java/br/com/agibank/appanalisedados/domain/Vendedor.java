package br.com.agibank.appanalisedados.domain;

public class Vendedor {
    public static final String LAYOUT = "001";
    private static final int QUANTIDADECOLUNAS = 4;

    public Vendedor(String[] linhaArquivo) {
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != "") {
            this.erroImportacao = erro;
            return;
        }

        try {
            this.cpf = linhaArquivo[1];
            this.nome = linhaArquivo[2];
            this.salario = Double.parseDouble(linhaArquivo[3]);
        } catch (Exception ex) {
            this.erroImportacao = "Erro ao converter valores.";
        }
    }

    public Vendedor(String cpf, String nome, Double salario, String erroImportacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.salario = salario;
        this.erroImportacao = erroImportacao;
    }

    private String cpf;
    private String nome;
    private Double salario;
    private String erroImportacao;

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getErroImportacao() {
        return erroImportacao;
    }

    public static Vendedor build(String cpf, String nome, Double salario) {
        return new Vendedor(cpf, nome, salario, null);
    }

    public String validarLinhaArquivo(String[] linhaArquivo) {
        if (linhaArquivo == null || linhaArquivo.length == 0) {
            return "Linha em branco";
        }

        if (linhaArquivo.length != this.QUANTIDADECOLUNAS) {
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Vendedor.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Vendedor other = (Vendedor) obj;

        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }

        if ((this.cpf == null) ? (other.cpf != null) : !this.cpf.equals(other.cpf)) {
            return false;
        }

        return true;
    }
}
