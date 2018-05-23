package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithNewSeasonException extends SkipException {

    public NoSeriesWithNewSeasonException(String skipMessage) {
        super(skipMessage);
    }
}
