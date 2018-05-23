package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class ContentException extends SkipException {

    public ContentException(String message) {
        super(message);
    }
}
