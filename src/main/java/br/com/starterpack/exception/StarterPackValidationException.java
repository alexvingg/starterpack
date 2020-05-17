package br.com.starterpack.exception;

public class StarterPackValidationException extends IllegalArgumentException {

	private static final long serialVersionUID = -7593358302635838120L;

	public StarterPackValidationException() {
		super();
	}

	public StarterPackValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public StarterPackValidationException(String s) {
		super(s);
	}

	public StarterPackValidationException(Throwable cause) {
		super(cause);
	}

}
