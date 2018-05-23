package com.viacom.test.vimn.common.exceptions;

import com.viacom.test.core.util.Logger;
import org.testng.SkipException;

@SuppressWarnings("serial")
public class BackgroundVideoEnabledException extends SkipException {

    public BackgroundVideoEnabledException(String skipMessage) {
        super(skipMessage);
        Logger.logMessage(skipMessage);
    }
}
