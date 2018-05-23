package com.viacom.test.vimn.common.exceptions;

import com.viacom.test.core.util.Logger;
import org.testng.SkipException;

@SuppressWarnings("serial")
public class LiveTVNotEnabledForAllUsersException extends SkipException {

    public LiveTVNotEnabledForAllUsersException(String skipMessage) {
        super(skipMessage);
        Logger.logMessage(skipMessage);
    }
}
