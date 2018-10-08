package br.com.agibank.appanalisedados.schedule;

import br.com.agibank.appanalisedados.service.ProcessadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessadorArquivoScheduler {
    @Autowired
    private ProcessadorService processadorService;

    Logger logger = LoggerFactory.getLogger(ProcessadorArquivoScheduler.class);

    @Scheduled(initialDelayString = "${app.job.interval}", fixedRateString = "#{${app.job.interval} * 1000}")
    public void executarProcessamento(){
        try {
            this.logger.info("Procurando arquivos para importação");
            this.processadorService.processarArquivosAguardandoProcessamento();
        }
        catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
    }
}
