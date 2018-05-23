package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

public class ResultException extends SkipException {

    public ResultException(String skipMessage) {
        super(skipMessage);
    }

    public ResultException(String skipMessage, Throwable cause) {
        super(skipMessage, cause);
    }
}
