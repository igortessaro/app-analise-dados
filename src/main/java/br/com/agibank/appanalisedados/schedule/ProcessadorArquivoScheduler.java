package br.com.agibank.appanalisedados.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessadorArquivoScheduler {
    @Scheduled(initialDelayString = "${app.job.interval}", fixedRateString = "#{${app.job.interval} * 1000}")
    public void executarProcessamento(){

    }
}
