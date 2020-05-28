package br.com.starterpack.exception;

public class StarterPackException extends IllegalArgumentException {

    public StarterPackException() {
        super();
    }

    public StarterPackException(String message, Throwable cause) {
        super(message, cause);
    }

    public StarterPackException(String s) {
        super(s);
    }

    public StarterPackException(Throwable cause) {
        super(cause);
    }

}
