package by.clevertech.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import by.clevertech.service.dto.ErrorDto;

@RestControllerAdvice(basePackages = { "by.clevertech.rest", "by.clevertech.repository", "by.clevertech.service" })
public class RestExceptionAdvice {
    private static final Logger log = LogManager.getLogger(RestExceptionAdvice.class);

    private static final String MSG_SERVER_ERROR = "Server error";
    private static final String MSG_CLIENT_ERROR = "Client error";
    private static final String DEFAULT_MESSAGE = "Unknown error";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto error(EntityNotFoundException e) {
        log.error(e.getMessage());
        return new ErrorDto(MSG_CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto error(ClientException e) {
        log.error(e.getMessage());
        return new ErrorDto(MSG_CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(ClevertechException e) {
        log.error(e.getMessage());
        return new ErrorDto(MSG_CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto error(Exception e) {
        log.error(e.getMessage());
        return new ErrorDto(MSG_SERVER_ERROR, DEFAULT_MESSAGE);
    }

}
