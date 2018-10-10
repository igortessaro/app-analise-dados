package br.com.agibank.appanalisedados.factory;

import br.com.agibank.appanalisedados.domain.Arquivo;
import br.com.agibank.appanalisedados.domain.Cliente;
import br.com.agibank.appanalisedados.domain.Venda;
import br.com.agibank.appanalisedados.domain.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArquivoFactory {
    @Autowired
    private ClienteFactory clienteFactory;

    @Autowired
    private VendedorFactory vendedorFactory;

    @Autowired
    private VendaFactory vendaFactory;

    public Arquivo create(List<String[]> arquivo){
        if (arquivo == null || arquivo.size() == 0) {
            return null;
        }

        Arquivo result = new Arquivo();

        for (int i = 0; i < arquivo.size(); i++) {
            String[] linhaArquivo = arquivo.get(i);

            String erro = this.validarLinhaArquivo(linhaArquivo);

            if (erro != null && erro != "") {
                result.adicionarErro(i, erro);
                continue;
            }

            String layout = linhaArquivo[0];

            if (layout.equals(Cliente.LAYOUT)) {
                Cliente cliente = this.clienteFactory.create(linhaArquivo);
                result.adicionarCliente(cliente, i);
            }

            if (layout.equals(Vendedor.LAYOUT)) {
                Vendedor vendedor = this.vendedorFactory.create(linhaArquivo);
                result.adicionarVendedor(vendedor, i);
            }

            if (layout.equals(Venda.LAYOUT)) {
                Venda venda = this.vendaFactory.create(linhaArquivo);
                result.adicionarVenda(venda, i);
            }
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
}
