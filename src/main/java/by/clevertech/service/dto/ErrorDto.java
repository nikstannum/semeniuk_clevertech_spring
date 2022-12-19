package by.clevertech.service.dto;

import by.clevertech.exception.RestExceptionAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An entity that represents output as the result of handling exceptions that
 * have occurred.
 * <p>
 * Raised in the {@link RestExceptionAdvice} class based on the exception that
 * was caught.
 * 
 * @author Nikita Semeniuk
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String errorType;
    private String errorMessage;

}
