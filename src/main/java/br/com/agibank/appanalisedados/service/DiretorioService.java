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

    public String obterExtensaoArquivoEntrada() {
        return this.extensaoArquivoLeitura;
    }

    public String obterDiretorioEntrada() {
        return this.diretorioEntrada;
    }

    public String obterDiretorioSaida() {
        return this.diretorioSaida;
    }

    public Boolean diretorioExiste(final String path) {
        File diretorio = new File(path);

        return this.diretorioExiste(diretorio);
    }

    public List<File> obterArquivosAguardandoProcessamento() {
        return this.obterArquivos(this.diretorioEntrada, this.extensaoArquivoLeitura);
    }

    public void escreverArquivoSaida(List<String> dados, String nome) {
        if (!this.diretorioExiste(this.diretorioSaida)) {
            this.criarDiretorio(this.diretorioSaida);
        }

        byte[] bytes = String.join("\n", dados).getBytes();

        Path outPath = Paths.get(this.diretorioSaida, nome + this.extensaoArquivoDevolucao);

        try {
            Files.write(outPath, bytes);
        } catch (Exception ex) {
            logger.error("Erro ao escrever no arquivo '" + nome + "': " + ex.getMessage(), ex);
        }
    }

    public List<File> obterArquivos(final String diretorio, final String arquivoExtensao) {
        File diretorioArquivos = new File(diretorio);

        if (!this.diretorioExiste(diretorioArquivos)) {
            this.criarDiretorio(diretorio);
            return new ArrayList<File>();
        }

        List<File> arquivos = Arrays.stream(diretorioArquivos.listFiles())
                .filter(arquivo -> arquivo.toString().endsWith(arquivoExtensao))
                .collect(Collectors.toList());

        return arquivos;
    }

    private Boolean diretorioExiste(final File diretorio) {
        Boolean result = diretorio.exists();

        if (!result) {
            this.logger.warn("Diretório '" + diretorio.getPath() + "' não encontrado.");
        }

        return result;
    }

    private Boolean criarDiretorio(final String path) {
        File diretorio = new File(path);
        return this.criarDiretorio(diretorio);
    }

    private Boolean criarDiretorio(final File diretorio) {
        Boolean result = diretorio.mkdirs();

        if (result) {
            this.logger.info("Diretório '" + diretorio.getPath() + "' criado com sucesso.");
        } else {
            this.logger.info("Não foi possível criar o diretório '" + diretorio.getPath() + "'.");
        }

        return result;
    }
}
