package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class ShortFormNotEnabledException extends SkipException {

    public ShortFormNotEnabledException(String skipMessage) {
        super(skipMessage);
    }
}
