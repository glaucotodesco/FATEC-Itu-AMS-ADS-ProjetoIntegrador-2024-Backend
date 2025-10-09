package br.fatec.easycoast;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.fatec.easycoast.services.FileStorageService;

@SpringBootApplication
public class EasycoastApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasycoastApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileStorageService fileStorageService) {
		return (args) -> {
			fileStorageService.init();
		};
	}
}
