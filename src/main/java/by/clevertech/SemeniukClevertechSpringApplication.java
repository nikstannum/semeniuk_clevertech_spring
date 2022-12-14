package by.clevertech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
public class SemeniukClevertechSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SemeniukClevertechSpringApplication.class, args);
	}
}
