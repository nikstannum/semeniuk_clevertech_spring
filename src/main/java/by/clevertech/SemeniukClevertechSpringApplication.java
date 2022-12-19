package by.clevertech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import by.clevertech.service.dto.CheckOutDto;

/**
 * The SemeniukClevertechSpringApplication implements a simple an application
 * that receives a set of parameters through the RESTFUL interface, meaning the
 * product id and the discount card id, and based on these data returns the
 * generated {@link CheckOutDto} in the form of a JSON object.
 * 
 * @author Nikita Semeniuk
 *
 */
@SpringBootApplication
public class SemeniukClevertechSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SemeniukClevertechSpringApplication.class, args);
    }
}
