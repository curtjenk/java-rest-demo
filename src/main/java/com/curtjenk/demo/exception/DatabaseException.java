package com.curtjenk.demo.exception;

/**
 * 
 */

public class DatabaseException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public DatabaseException() {
        this("");
    }

    /**
     * @param message
     */
    public DatabaseException(String message) {
        this(message, (Throwable) null);
    }

    /**
     * @param cause
     */
    public DatabaseException(Throwable cause) {
        this("", cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DatabaseException(String message, Throwable cause) {
        this(message, cause, false, true);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
