package br.com.agibank.appanalisedados.factory;

import br.com.agibank.appanalisedados.domain.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ItemFactory {
    private static final int COLUNA_CODIGO = 0;
    private static final int COLUNA_QUANTIDADE = 1;
    private static final int COLUNA_VALOR = 2;
    private static final String SEPARADOR_ATRIBUTOS_ITEM = "-";
    private static final String SEPARADOR_ITENS = ",";
    private static final String SEPARADOR_INICIO = "[";
    private static final String SEPARADOR_FIM = "]";
    private static final int QUANTIDADECOLUNAS = 3;

    public Item create(String linhaArquivo){
        String[] item = linhaArquivo.split(SEPARADOR_ATRIBUTOS_ITEM);

        String erro = this.validarLinhaArquivo(item);

        if (erro != null && erro != "") {
            return new Item(erro);
        }

        Integer codigo = Integer.parseInt(item[COLUNA_CODIGO]);
        Integer quantidade = Integer.parseInt(item[COLUNA_QUANTIDADE]);
        Double valor = Double.parseDouble(item[COLUNA_VALOR]);

        return new Item(codigo, quantidade, valor);
    }

    public List<Item> createItens(String itensLinha){
        List<Item> result = new ArrayList<>();

        String[] itens = this.obterItensSplit(itensLinha);

        if (itens == null || itens.length == 0) {
            return result;
        }

        Arrays.stream(itens)
                .forEach(itemLinha ->
                {
                    Item entity = this.create(itemLinha);
                    result.add(entity);
                });

        return result;
    }

    private String[] obterItensSplit(String itensLinha) {
        if (itensLinha == null || itensLinha == "") {
            return null;
        }

        itensLinha = itensLinha.replace(SEPARADOR_INICIO, "").replace(SEPARADOR_FIM, "");
        String[] itens = itensLinha.split(SEPARADOR_ITENS);

        return itens;
    }

    public String validarLinhaArquivo(String[] linhaArquivo) {
        if (linhaArquivo == null || linhaArquivo.length == 0) {
            return "Registro não possui itens.";
        }

        if (linhaArquivo.length != this.QUANTIDADECOLUNAS) {
            return "Quantidade de colunas divergentes.";
        }

        return null;
    }
}
