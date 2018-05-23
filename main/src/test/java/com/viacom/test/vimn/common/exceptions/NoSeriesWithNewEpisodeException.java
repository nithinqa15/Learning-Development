package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithNewEpisodeException extends SkipException {

    public NoSeriesWithNewEpisodeException(String skipMessage) {
        super(skipMessage);
    }
}
