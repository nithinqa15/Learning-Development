package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class EpisodeOrderException extends SkipException {

    public EpisodeOrderException(String skipMessage) {
        super(skipMessage);
    }
}
