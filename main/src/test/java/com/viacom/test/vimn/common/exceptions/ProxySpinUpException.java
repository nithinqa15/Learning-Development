package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class ProxySpinUpException extends SkipException {

    public ProxySpinUpException(String skipMessage) {
        super(skipMessage);
    }

    public ProxySpinUpException(String skipMessage, Throwable cause) {
        super(skipMessage, cause);
    }
}
