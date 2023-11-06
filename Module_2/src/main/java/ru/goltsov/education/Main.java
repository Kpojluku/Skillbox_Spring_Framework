package ru.goltsov.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.goltsov.education.config.AppConfig;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

}
