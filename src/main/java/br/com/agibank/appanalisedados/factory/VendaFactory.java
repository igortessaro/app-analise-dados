package br.com.agibank.appanalisedados.factory;

import br.com.agibank.appanalisedados.domain.Item;
import br.com.agibank.appanalisedados.domain.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendaFactory {
    private static final int QUANTIDADECOLUNAS = 4;
    private static final int COLUNA_ITENS = 2;
    private static final int COLUNA_CODIGO = 1;
    private static final int COLUNA_VENDEDOR = 3;

    @Autowired
    private ItemFactory itemFactory;

    public Venda create(String[] linhaArquivo){
        String erro = this.validarLinhaArquivo(linhaArquivo);

        if (erro != null && erro != "") {
            return new Venda(erro);
        }

        List<Item> itens = this.itemFactory.createItens(linhaArquivo[COLUNA_ITENS]);

        if (itens == null || itens.size() == 0) {
            return new Venda("Venda não possui itens.");
        }

        Integer codigo = Integer.parseInt(linhaArquivo[COLUNA_CODIGO]);
        String vendedor = linhaArquivo[COLUNA_VENDEDOR];

        return new Venda(codigo, vendedor, itens);
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
