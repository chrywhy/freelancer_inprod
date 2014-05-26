package com.inprod.exception;

/**
 *
 * @author albert wang
 */
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AppException(String msg, Throwable e) {
        super(msg, e);
    }

    public AppException(String msg) {
        super(msg);
    }
}
