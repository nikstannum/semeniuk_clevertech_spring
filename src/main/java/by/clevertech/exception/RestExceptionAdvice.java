package by.clevertech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import by.clevertech.service.dto.error.ErrorDto;

@RestControllerAdvice("by.clevertech.controller.rest")
public class RestExceptionAdvice {

	private static final String SERVER_ERROR = "Server error";
	private static final String CLIENT_ERROR = "Client error";
	private static final String DEFAULT_MESSAGE = "Unknown error";

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDto error(ClientException e) {
		return new ErrorDto(CLIENT_ERROR, e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDto error(RuntimeException e) {
		return new ErrorDto(SERVER_ERROR, DEFAULT_MESSAGE);
	}

}
