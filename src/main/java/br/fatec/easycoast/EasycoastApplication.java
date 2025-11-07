package br.fatec.easycoast;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.fatec.easycoast.dtos.employee.OwnerRequest;
import br.fatec.easycoast.services.EmployeeService;
import br.fatec.easycoast.services.FileStorageService;
import br.fatec.easycoast.services.InitializationService;

@SpringBootApplication
public class EasycoastApplication {
	@Value("${easycoast.owner-initial-login}")
	private String login;

	@Value("${easycoast.owner-initial-password}")
	private String pwd;

	@Value("${spring.profiles.active}")
	private String buildStage;

	public static void main(String[] args) {
		SpringApplication.run(EasycoastApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileStorageService fileStorageService, EmployeeService employeeService, InitializationService initializationService) {
		return (args) -> {
			if (!initializationService.isOwnerInitialized() || buildStage.equals("dev")) {
				employeeService.saveOwner(new OwnerRequest(
					"Owner", 
					null, 
					login, 
					pwd));
			}
      
			fileStorageService.init();
		};
	}
}
