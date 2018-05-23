package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoNewSeriesException extends SkipException {

    public NoNewSeriesException(String skipMessage) {
        super(skipMessage);
    }
}
