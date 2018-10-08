package br.com.agibank.appanalisedados.schedule;

import br.com.agibank.appanalisedados.service.ProcessadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessadorArquivoScheduler {
    @Autowired
    private ProcessadorService processadorService;

    @Scheduled(initialDelayString = "${app.job.interval}", fixedRateString = "#{${app.job.interval} * 1000}")
    public void executarProcessamento(){
        this.processadorService.processarArquivosAguardandoProcessamento();
    }
}
