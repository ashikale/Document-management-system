package com.qdb.in.bean;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiException implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statusCode;
	
	private String message;

}
