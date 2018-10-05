package br.com.agibank.appanalisedados.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vendedor {
    public static final String LAYOUT = "001";
    private static final int QUANTIDADECOLUNAS = 4;

    private String cpf;
    private String nome;
    private Double salario;
    private String erroImportacao;

    public Vendedor(String[] linhaArquivo) {
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != ""){
            this.erroImportacao = erro;
            return;
        }

        try{
            this.cpf = linhaArquivo[1];
            this.nome = linhaArquivo[2];
            this.salario = Double.parseDouble(linhaArquivo[3]);
        }catch (Exception ex){
            this.erroImportacao = "Erro ao converter valores.";
        }
    }

    public static Vendedor build(String cpf, String nome, Double salario){
        return new Vendedor(cpf, nome, salario, null);
    }

    public String validarLinhaArquivo(String[] linhaArquivo) {
        if(linhaArquivo == null || linhaArquivo.length == 0){
            return "Linha em branco";
        }

        if(linhaArquivo.length != this.QUANTIDADECOLUNAS){
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }
}
