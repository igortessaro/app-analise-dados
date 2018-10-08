package br.com.agibank.appanalisedados;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"br.com.agibank.appanalisedados"})
@EnableScheduling
public class AppAnaliseDadosApplication {
    private static Logger logger = LoggerFactory.getLogger(AppAnaliseDadosApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciado serviço.");
	    SpringApplication.run(AppAnaliseDadosApplication.class, args);
	}
}
