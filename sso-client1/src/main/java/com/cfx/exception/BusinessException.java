package com.cfx.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -8138602623241348983L;
	private String errorMessage = null;

	public BusinessException() {
		super();
	}

	public BusinessException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		if (errorMessage != null) {
			return errorMessage;
		}
		if (super.getMessage() != null)
			return super.getMessage();
		return errorMessage;
	}
}