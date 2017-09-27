package com.techticz.app.domain.exception;

import com.techticz.app.constant.AppErrors;

/**
 * Created by gssirohi on 5/7/16.
 */
public class AppException extends RuntimeException {
    private AppErrors error;

    public AppException(AppErrors error) {
        super(error.getMessage());
        this.error = error;
    }

    public AppException() {
    }

    public AppErrors getError() {
        return error;
    }
}
