package br.com.agibank.appanalisedados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"br.com.agibank.appanalisedados"})
@EnableScheduling
public class AppAnaliseDadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppAnaliseDadosApplication.class, args);
	}
}
