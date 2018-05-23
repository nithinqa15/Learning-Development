package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class ProviderException extends SkipException {

    public ProviderException(String skipMessage) {
        super(skipMessage);
    }
}
