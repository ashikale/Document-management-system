package com.qdb.in.exception;

public class NoSuchFileExistsException extends RuntimeException{
	
	
	private String message;
	
	public NoSuchFileExistsException(String msg) {
		super(msg);
        this.message = msg;
	}

}
