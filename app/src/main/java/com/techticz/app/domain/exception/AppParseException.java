package com.techticz.app.domain.exception;

import com.techticz.app.constant.AppErrors;

/**
 * Created by YATRAONLINE\gyanendra.sirohi on 3/9/17.
 */

public class AppParseException extends AppException {
    public AppParseException(Exception e) {
        AppErrors error = AppErrors.PARSE_ERROR;
        error.setMessage(e.getMessage());
        new AppParseException(error);
    }

    public AppParseException(AppErrors error) {
        super(error);
    }
}
