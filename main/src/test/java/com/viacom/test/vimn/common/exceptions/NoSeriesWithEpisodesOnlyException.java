package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoSeriesWithEpisodesOnlyException extends SkipException {

    public NoSeriesWithEpisodesOnlyException(String skipMessage) {
        super(skipMessage);
    }
}
