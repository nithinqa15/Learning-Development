package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithClipsException extends SkipException {

    public NoSeriesWithClipsException(String skipMessage) {
        super(skipMessage);
    }
}