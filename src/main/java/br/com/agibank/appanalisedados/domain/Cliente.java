package br.com.agibank.appanalisedados.domain;

import lombok.*;

@Data
@AllArgsConstructor
public class Cliente {
    public static final String LAYOUT = "002";
    private static final int QUANTIDADECOLUNAS = 4;

    private String cnpj;
    private String nome;
    private String areaAtuacao;
    private String erroImportacao;

    public Cliente(String[] linhaArquivo) {
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != ""){
            this.erroImportacao = erro;
            return;
        }

        try{
            this.cnpj = linhaArquivo[1];
            this.nome = linhaArquivo[2];
            this.areaAtuacao = linhaArquivo[3];
        }catch (Exception ex){
            this.erroImportacao = "Erro ao converter valores.";
        }
    }

    public static Cliente build(String cnpj, String nome, String areaAtuacao){
        return new Cliente(cnpj, nome, areaAtuacao, null);
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
