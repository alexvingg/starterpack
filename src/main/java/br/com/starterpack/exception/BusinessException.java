package br.com.starterpack.exception;

public class BusinessException extends IllegalArgumentException {

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
