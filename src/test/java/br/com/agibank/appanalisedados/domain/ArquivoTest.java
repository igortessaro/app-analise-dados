package br.com.agibank.appanalisedados.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArquivoTest {
    @Test
    public void arquivo_sem_dados(){
        Arquivo arquivo = new Arquivo();

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertNull(piorVendedor);
        assertEquals(quantidadeClientes, 0);
        assertEquals(quantidadeVendedores, 0);
        assertEquals(idVendaMaisCara, 0);
    }

    @Test
    public void arquivo_com_apenas_tres_clientes(){
        Arquivo arquivo = new Arquivo();
        arquivo.adicionarCliente(this.criarCliente("12345678901", "Maria Santos", "Marketing Digital"));
        arquivo.adicionarCliente(this.criarCliente("09876543210", "José Silva", "Engenheiro Civil"));
        arquivo.adicionarCliente(this.criarCliente("54637281902", "João Freitas", "Clinico Geral"));

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertNull(piorVendedor);
        assertEquals(quantidadeClientes, 3);
        assertEquals(quantidadeVendedores, 0);
        assertEquals(idVendaMaisCara, 0);
    }

    @Test
    public void arquivo_com_apenas_tres_vendedores(){
        Arquivo arquivo = new Arquivo();

        Vendedor criarPiorVendedor = this.criarVendedor("12345678901", "Maria Santos", 1000D);
        arquivo.adicionarVendedor(criarPiorVendedor);
        arquivo.adicionarVendedor(this.criarVendedor("09876543210", "José Silva", 1500D));
        arquivo.adicionarVendedor(this.criarVendedor("54637281902", "João Freitas", 10000.34D));

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertEquals(piorVendedor, criarPiorVendedor);
        assertEquals(quantidadeClientes, 0);
        assertEquals(quantidadeVendedores, 3);
        assertEquals(idVendaMaisCara, 0);
    }

    @Test
    public void arquivo_completo(){
        Arquivo arquivo = new Arquivo();

        String nomeMariaSantos = "Maria Santos";
        String nomeJoseSilva = "José Silva";
        String nomeJoaoFreitas = "João Freitas";

        Vendedor criarPiorVendedor = this.criarVendedor("12345678901", nomeMariaSantos, 1000D);
        arquivo.adicionarVendedor(criarPiorVendedor);
        arquivo.adicionarVendedor(this.criarVendedor("09876543210", nomeJoseSilva, 1500D));
        arquivo.adicionarVendedor(this.criarVendedor("54637281902", nomeJoaoFreitas, 10000.34D));

        arquivo.adicionarCliente(this.criarCliente("12345678901", "Beltrano Santos", "Marketing Digital"));
        arquivo.adicionarCliente(this.criarCliente("09876543210", "Fulano Silva", "Engenheiro Civil"));
        arquivo.adicionarCliente(this.criarCliente("54637281902", "Ciclano Freitas", "Clinico Geral"));

        Venda venda1 = this.criarVenda(1, nomeMariaSantos);
        Venda venda2 = this.criarVenda(2, nomeJoseSilva);
        Venda venda3 = this.criarVenda(3, nomeJoaoFreitas);

        venda1.adicionarItem(this.criarItem(4, 10,1D));

        venda2.adicionarItem(this.criarItem(1, 10,100D));
        venda2.adicionarItem(this.criarItem(2, 30,2.5D));
        venda2.adicionarItem(this.criarItem(3, 40,3.10D));

        venda3.adicionarItem(this.criarItem(1, 34,10D));
        venda3.adicionarItem(this.criarItem(2, 33,1.50D));
        venda3.adicionarItem(this.criarItem(3, 40,0.10D));

        arquivo.adicionarVenda(venda1);
        arquivo.adicionarVenda(venda2);
        arquivo.adicionarVenda(venda3);

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertEquals(piorVendedor, criarPiorVendedor);
        assertEquals(quantidadeClientes, 3);
        assertEquals(quantidadeVendedores, 3);
        assertEquals(idVendaMaisCara, 2);
    }

    @Test
    public void arquivo_com_vendedor_sem_venda(){
        Arquivo arquivo = new Arquivo();

        String nomeMariaSantos = "Maria Santos";
        String nomeJoseSilva = "José Silva";
        String nomeJoaoFreitas = "João Freitas";

        Vendedor criarPiorVendedor = this.criarVendedor("12345678901", nomeMariaSantos, 1000D);
        arquivo.adicionarVendedor(criarPiorVendedor);
        arquivo.adicionarVendedor(this.criarVendedor("09876543210", nomeJoseSilva, 1500D));
        arquivo.adicionarVendedor(this.criarVendedor("54637281902", nomeJoaoFreitas, 10000.34D));

        arquivo.adicionarCliente(this.criarCliente("12345678901", "Beltrano Santos", "Marketing Digital"));
        arquivo.adicionarCliente(this.criarCliente("09876543210", "Fulano Silva", "Engenheiro Civil"));
        arquivo.adicionarCliente(this.criarCliente("54637281902", "Ciclano Freitas", "Clinico Geral"));

        Venda venda2 = this.criarVenda(2, nomeJoseSilva);
        Venda venda3 = this.criarVenda(3, nomeJoaoFreitas);

        venda2.adicionarItem(this.criarItem(1, 10,100D));
        venda2.adicionarItem(this.criarItem(2, 30,2.5D));
        venda2.adicionarItem(this.criarItem(3, 40,3.10D));

        venda3.adicionarItem(this.criarItem(1, 34,10D));
        venda3.adicionarItem(this.criarItem(2, 33,1.50D));
        venda3.adicionarItem(this.criarItem(3, 40,0.10D));

        arquivo.adicionarVenda(venda2);
        arquivo.adicionarVenda(venda3);

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertEquals(piorVendedor, criarPiorVendedor);
        assertEquals(quantidadeClientes, 3);
        assertEquals(quantidadeVendedores, 3);
        assertEquals(idVendaMaisCara, 2);
    }

    @Test
    public void arquivo_importar_template_teste(){
        List<String[]> arquivoTemplat = new ArrayList<>();
        arquivoTemplat.add(new String[]{"001", "1234567891234", "Diego", "50000"});
        arquivoTemplat.add(new String[]{"001", "3245678865434", "Renato", "40000.99"});
        arquivoTemplat.add(new String[]{"002", "2345675434544345", "Jose da Silva", "Rural"});
        arquivoTemplat.add(new String[]{"002", "2345675433444345", "Eduardo Pereira", "Rural"});
        arquivoTemplat.add(new String[]{"003", "10", "[1-10-100,2-30-2.50,3-40-3.10]", "Diego"});
        arquivoTemplat.add(new String[]{"003", "08", "[1-34-10,2-33-1.50,3-40-0.10]", "Renato"});

        //001ç1234567891234çDiegoç50000
        //001ç3245678865434çRenatoç40000.99
        //002ç2345675434544345çJose da SilvaçRural
        //002ç2345675433444345çEduardo PereiraçRural
        //003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
        //003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato

        Arquivo arquivo = new Arquivo(arquivoTemplat);

        Vendedor criarPiorVendedor = this.criarVendedor("3245678865434", "Renato", 40000.99D);

        Vendedor piorVendedor = arquivo.obterPiorVendedor();
        int quantidadeClientes = arquivo.obterQuantidadeClientes();
        int quantidadeVendedores = arquivo.obterQuantidadeVendedores();
        int idVendaMaisCara = arquivo.obterVendaMaisCara();

        assertEquals(piorVendedor, criarPiorVendedor);
        assertEquals(quantidadeClientes, 2);
        assertEquals(quantidadeVendedores, 2);
        assertEquals(idVendaMaisCara, 10);
    }

    private Cliente criarCliente(String cnpj, String nome, String areaAtuacao){
        return Cliente.create(cnpj, nome, areaAtuacao);
    }

    private Vendedor criarVendedor(String cpf, String nome, Double salario){
        return Vendedor.build(cpf, nome, salario);
    }

    private Item criarItem(int codigo, int quantidade, Double valor){
        return new Item(codigo, quantidade, valor);
    }

    private Venda criarVenda(int codigo, String vendedor){
        return Venda.build(codigo, vendedor);
    }
}