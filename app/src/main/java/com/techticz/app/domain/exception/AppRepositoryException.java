package com.techticz.app.domain.exception;

import com.techticz.app.constant.AppErrors;

/**
 * Created by gssirohi on 5/7/16.
 */
public class AppRepositoryException extends RuntimeException {
    private AppErrors error;

    public AppRepositoryException(AppErrors error) {
        super(error.getMessage());
        this.error = error;
    }

    public AppErrors getError() {
        return error;
    }
}
