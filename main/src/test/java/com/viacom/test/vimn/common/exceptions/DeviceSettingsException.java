package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

@SuppressWarnings("serial")
public class DeviceSettingsException extends SkipException {

    public DeviceSettingsException(String skipMessage) {
        super(skipMessage);
    }
}
