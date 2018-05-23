package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class NoMovieContentException extends SkipException {

    public NoMovieContentException(String skipMessage) {
        super(skipMessage);
    }
}
