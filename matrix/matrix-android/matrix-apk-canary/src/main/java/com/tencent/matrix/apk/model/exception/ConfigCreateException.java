package com.tencent.matrix.apk.model.exception;

public class ConfigCreateException extends RuntimeException {
    public ConfigCreateException(String message) {
        super(message);
    }

    public ConfigCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
