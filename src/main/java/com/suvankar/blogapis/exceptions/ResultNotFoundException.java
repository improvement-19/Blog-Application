package com.suvankar.blogapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	int fieldValue;
	public ResultNotFoundException(String resourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
}
