package br.com.starterpack.exception;

public class UnauthorizedException extends StarterPackValidationException {

	private ErrorResponse errorResponse;

	private static final long serialVersionUID = 4611270908576901798L;

	public UnauthorizedException() {
		super();
	}

	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(String s) {
		super(s);
	}

	public UnauthorizedException(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
