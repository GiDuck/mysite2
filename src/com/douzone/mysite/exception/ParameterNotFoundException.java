package com.douzone.mysite.exception;

public class ParameterNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterNotFoundException() {
		super("지정한 파라미터가 존재하지 않습니다.");
	}

	public ParameterNotFoundException(String message) {
		super("지정한 파라미터가 존재하지 않습니다. " + message);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

}
