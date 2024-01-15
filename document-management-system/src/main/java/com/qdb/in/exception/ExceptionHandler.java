package com.qdb.in.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.qdb.in.bean.ApiException;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiException resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ApiException message = ApiException.builder().statusCode("444").message(ex.getMessage()).build();

		return message;
	}

	

	@org.springframework.web.bind.annotation.ExceptionHandler(value = { NoSuchFileExistsException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ApiException fileNotFoundException(NoSuchFileExistsException ex, WebRequest request) {
		ApiException message = ApiException.builder().statusCode("445").message(ex.getMessage()).build();

		return message;
	}
}


