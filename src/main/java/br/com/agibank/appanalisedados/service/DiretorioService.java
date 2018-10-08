package br.com.agibank.appanalisedados.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiretorioService {
    @Value("${app.data.directory.in}")
    private String diretorioEntrada;

    @Value("${app.data.directory.out}")
    private String diretorioSaida;

    @Value("${app.data.file.extension.reader}")
    private String extensaoArquivoLeitura;

    @Value("${app.data.file.extension.write}")
    private String extensaoArquivoDevolucao;

    Logger logger = LoggerFactory.getLogger(ProcessadorService.class);

    public String obterExtensaoArquivoEntrada(){
        return this.extensaoArquivoLeitura;
    }

    public String obterDiretorioEntrada(){
        return this.diretorioEntrada;
    }

    public String obterDiretorioSaida(){
        return this.diretorioSaida;
    }

    public Boolean diretorioExiste(final String path){
        File diretorio = new File(path);

        return diretorio.exists();
    }

    public List<File> obterArquivosAguardandoProcessamento(){
        return this.obterArquivos(this.obterDiretorioEntrada(), this.extensaoArquivoLeitura);
    }

    public void escreverArquivoSaida(List<String> dados, String nome){
        byte[] bytes = String.join("\n", dados).getBytes();

        Path outPath = Paths.get(this.diretorioSaida, nome + this.extensaoArquivoDevolucao);

        try {
            Files.write(outPath, bytes);
        }
        catch (Exception ex){
            logger.error("Erro ao escrever no arquivo '"+ nome +"': "+ex.getMessage(), ex);
        }
    }

    public void deletarArquivoProcessado(String nome){

    }

    public List<File> obterArquivos(final String diretorio, final String arquivoExtensao){
        File diretorioArquivos = new File(diretorio);

        if(!diretorioArquivos.exists()){
            return new ArrayList<File>();
        }

        List<File> arquivos = Arrays.stream(diretorioArquivos.listFiles())
                .filter(arquivo -> arquivo.toString().endsWith(arquivoExtensao))
                .map(a -> (File)a)
                .collect(Collectors.toList());

        return arquivos;
    }
}
