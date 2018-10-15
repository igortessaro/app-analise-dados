package br.com.agibank.appanalisedados.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiretorioServiceTest {
    @Autowired
    private DiretorioService service;

    @Test
    public void diretorio_de_entrada_existente() {
        String diretorioEntrada = this.service.getDiretorioEntrada();

        Assert.assertNotNull(diretorioEntrada);
        Assert.assertTrue(this.service.diretorioExiste(diretorioEntrada));
    }

    @Test
    public void diretorio_de_saida_existente() {
        String diretorioSaida = this.service.getDiretorioSaida();

        Assert.assertNotNull(diretorioSaida);
        Assert.assertTrue(this.service.diretorioExiste(diretorioSaida));
    }

    @Test
    public void valida_diretorio_inexistente() {
        Boolean result = this.service.diretorioExiste("k:/teste");

        Assert.assertFalse(result);
    }

    @Test
    public void valida_diretorio_existente() {
        Boolean result = this.service.diretorioExiste("c:/");

        Assert.assertTrue(result);
    }

    @Test
    public void obter_arquivos_repositorio_vazio(){
        String diretorioEntrada = this.service.getDiretorioEntrada();

        File[] arquivos = new File(diretorioEntrada).listFiles();

        for (File arquivo : arquivos) {
            arquivo.delete();
        }

        List<File> arquivosTeste = this.service.obterArquivos(diretorioEntrada, ".txt");

        Assert.assertNotNull(arquivosTeste);
        Assert.assertTrue(arquivosTeste.size() == 0);
    }

    @Test
    public void obter_arquivos_repositorio_de_entrada(){
        String diretorioEntrada = this.service.getDiretorioEntrada();

        File[] arquivos = new File(diretorioEntrada).listFiles();

        String arquivoExtensao = this.service.getExtensaoArquivoLeitura();

        for (File arquivo : arquivos) {
            arquivo.delete();
        }

        try {
            this.criarArquivo(diretorioEntrada, "teste", ".txt");
            this.criarArquivo(diretorioEntrada, "teste1", arquivoExtensao);
            this.criarArquivo(diretorioEntrada, "teste2", arquivoExtensao);
        }
        catch (Exception e){
            Assert.fail();
        }

        List<File> arquivosTeste = this.service.obterArquivos(diretorioEntrada, ".txt");

        Assert.assertNotNull(arquivosTeste);
        Assert.assertTrue(arquivosTeste.size() == 1);
    }

    @Test
    public void obter_arquivos_repositorio_de_entrada_aguardando_processamento(){
        String diretorioEntrada = this.service.getDiretorioEntrada();

        File[] arquivos = new File(diretorioEntrada).listFiles();

        String arquivoExtensao = this.service.getExtensaoArquivoLeitura();

        for (File arquivo : arquivos) {
            arquivo.delete();
        }

        try {
            this.criarArquivo(diretorioEntrada, "teste", ".txt");
            this.criarArquivo(diretorioEntrada, "teste1", arquivoExtensao);
            this.criarArquivo(diretorioEntrada, "teste2", arquivoExtensao);
        }
        catch (Exception e){
            Assert.fail();
        }

        List<File> arquivosTeste = this.service.obterArquivosAguardandoProcessamento();

        Assert.assertNotNull(arquivosTeste);
        Assert.assertTrue(arquivosTeste.size() == 2);
    }

    private void criarArquivo(String repositorio, String arquivoNome, String arquivoExtensao) throws IOException {
        FileWriter arq = new FileWriter(repositorio + "/" + arquivoNome + arquivoExtensao);
        arq.close();
    }
}