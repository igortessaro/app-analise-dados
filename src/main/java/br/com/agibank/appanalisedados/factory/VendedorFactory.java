package br.com.agibank.appanalisedados.factory;

import br.com.agibank.appanalisedados.domain.Vendedor;
import org.springframework.stereotype.Component;

@Component
public class VendedorFactory {
    private static final int QUANTIDADECOLUNAS = 4;

    private static final int COLUNA_CPF = 1;
    private static final int COLUNA_NOME = 2;
    private static final int COLUNA_SALARIO = 3;

    public Vendedor create(String[] linhaArquivo){
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != "") {
            return new Vendedor(erro);
        }

        String cpf = linhaArquivo[COLUNA_CPF];
        String nome =  linhaArquivo[COLUNA_NOME];
        Double salario = Double.parseDouble(linhaArquivo[COLUNA_SALARIO]);

        return new Vendedor(cpf, nome, salario);
    }

    private String validarLinhaArquivo(String[] linhaArquivo) {
        if (linhaArquivo == null || linhaArquivo.length == 0) {
            return "Linha em branco";
        }

        if (linhaArquivo.length != this.QUANTIDADECOLUNAS) {
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }
}
