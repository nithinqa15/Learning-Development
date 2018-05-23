package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithClipsOnlyException extends SkipException {

    public NoSeriesWithClipsOnlyException(String skipMessage) {
        super(skipMessage);
    }
}
