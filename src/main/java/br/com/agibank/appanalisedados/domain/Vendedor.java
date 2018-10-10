package br.com.agibank.appanalisedados.domain;

public class Vendedor {
    public static final String LAYOUT = "001";

    public Vendedor(String cpf, String nome, Double salario, String erroImportacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.salario = salario;
        this.erroImportacao = erroImportacao;
    }

    public Vendedor(String cpf, String nome, Double salario) {
        this(cpf, nome, salario, null);
    }

    public Vendedor(String erroImportacao){
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
