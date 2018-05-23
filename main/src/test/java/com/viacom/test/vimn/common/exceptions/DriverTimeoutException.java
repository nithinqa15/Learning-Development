package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class DriverTimeoutException extends SkipException {

    public DriverTimeoutException(String skipMessage) {
        super(skipMessage);
    }

    public DriverTimeoutException(String skipMessage, Throwable cause) {
        super(skipMessage, cause);
    }
}
