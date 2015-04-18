package com.douChat.logic.exception;

public class AlreadyLoginException extends Exception {

	private static final long serialVersionUID = 1062914162472104384L;

	public AlreadyLoginException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlreadyLoginException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AlreadyLoginException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AlreadyLoginException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlreadyLoginException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
