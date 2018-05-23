package com.viacom.test.vimn.common.exceptions;


import org.testng.SkipException;

public class NoEventContentException extends SkipException{

    public NoEventContentException(String skipMessage) {
        super(skipMessage);
    }
}
