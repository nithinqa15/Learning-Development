package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class InternetConnectionException extends SkipException {

    public InternetConnectionException(String message) {
        super(message);
    }
}
