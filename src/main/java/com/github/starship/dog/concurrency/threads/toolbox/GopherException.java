package com.github.starship.dog.concurrency.threads.toolbox;

public class GopherException extends RuntimeException {
    public GopherException() {
        super();
    }

    public GopherException(String message) {
        super(message);
    }

    public GopherException(String message, Throwable cause) {
        super(message, cause);
    }

    public GopherException(Throwable cause) {
        super(cause);
    }

    protected GopherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
