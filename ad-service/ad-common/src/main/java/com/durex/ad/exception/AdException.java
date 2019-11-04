package com.durex.ad.exception;

/**
 * @author gelong
 * @date 2019/11/5 0:09
 */
public class AdException extends Exception {

    public AdException() {
        super();
    }

    public AdException(String message) {
        super(message);
    }

    public AdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdException(Throwable cause) {
        super(cause);
    }

    protected AdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
