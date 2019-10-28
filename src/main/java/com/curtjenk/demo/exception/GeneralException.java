package com.curtjenk.demo.exception;

public class GeneralException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public GeneralException() {
        this("");
    }

    /**
     * @param message
     */
    public GeneralException(String message) {
        this(message, (Throwable) null);
    }

    /**
     * @param cause
     */
    public GeneralException(Throwable cause) {
        this("", cause);
    }

    /**
     * @param message
     * @param cause
     */
    public GeneralException(String message, Throwable cause) {
        this(message, cause, false, true);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public GeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
