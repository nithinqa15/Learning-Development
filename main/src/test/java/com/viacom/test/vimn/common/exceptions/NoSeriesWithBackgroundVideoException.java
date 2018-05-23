package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithBackgroundVideoException extends SkipException {

    public NoSeriesWithBackgroundVideoException(String skipMessage) {
        super(skipMessage);
    }
}
