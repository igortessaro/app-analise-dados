package br.com.agibank.appanalisedados.factory;

import br.com.agibank.appanalisedados.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteFactory {
    private static final int QUANTIDADECOLUNAS = 4;
    private static final int COLUNA_CNPJ = 1;
    private static final int COLUNA_NOME = 2;
    private static final int COLUNA_AREA_ATUACAO = 3;

    public Cliente create(String[] linhaArquivo){
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != "") {
            return  new Cliente(erro);
        }

        String cnpj = linhaArquivo[COLUNA_CNPJ];
        String nome = linhaArquivo[COLUNA_NOME];
        String areaAtuacao = linhaArquivo[COLUNA_AREA_ATUACAO];

        return new Cliente(cnpj, nome, areaAtuacao);
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
}
