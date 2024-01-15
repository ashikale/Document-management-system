package com.qdb.in.exception;

public class ResourceNotFoundException extends RuntimeException{
	
private String message;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
        this.message = msg;
	}

}
