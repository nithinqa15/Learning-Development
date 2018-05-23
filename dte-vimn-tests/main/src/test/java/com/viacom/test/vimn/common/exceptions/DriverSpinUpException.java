package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class DriverSpinUpException extends SkipException {

    public DriverSpinUpException(String skipMessage) {
        super(skipMessage);
    }

    public DriverSpinUpException(String skipMessage, Throwable cause) {
        super(skipMessage, cause);
    }
}
