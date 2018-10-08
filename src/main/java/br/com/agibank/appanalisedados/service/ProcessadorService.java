package br.com.agibank.appanalisedados.service;

import br.com.agibank.appanalisedados.domain.Arquivo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessadorService {
    @Autowired
    private DiretorioService diretorioService;

    private String separador = "ç";

    Logger logger = LoggerFactory.getLogger(ProcessadorService.class);

    public void processarArquivosAguardandoProcessamento(){
        List<File> arquivos = this.diretorioService.obterArquivosAguardandoProcessamento();

        if(arquivos == null || arquivos.size() == 0){
            return;
        }

        arquivos.parallelStream().forEach(this::processarArquivo);
    }

    private void processarArquivo(File file) {
        List<String[]> arquivo = this.converterArquivo(file);
        Arquivo arquivoDominio = new Arquivo(arquivo);

        List<String> relatorio = arquivoDominio.obterRelatorio();
        
        this.diretorioService.escreverArquivoSaida(relatorio, file.getName());

        file.delete();
    }

    private List<String[]> converterArquivo(File file){
        try {
            FileReader arquivo = new FileReader(file);
            BufferedReader lerArquivo = new BufferedReader(arquivo);

            List<String[]> result = new ArrayList<>();

            String linha = lerArquivo.readLine();

            if(linha != null){
                String[] primeiraLinhaSplit = linha.split(this.separador);
                result.add(primeiraLinhaSplit);
            }

            while (linha != null) {
                linha = lerArquivo.readLine();

                if(linha != null) {
                    String[] linhaSplit = linha.split(this.separador);
                    result.add(linhaSplit);
                }
            }

            arquivo.close();

            return result;
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            return  new ArrayList<>();
        }
    }
}
