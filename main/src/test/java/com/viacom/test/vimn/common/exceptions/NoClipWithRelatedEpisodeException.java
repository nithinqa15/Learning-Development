package com.viacom.test.vimn.common.exceptions;

import org.testng.SkipException;

/**
 * Created by fioreg on 10.08.17.
 */
@SuppressWarnings("serial")
public class NoClipWithRelatedEpisodeException extends SkipException {

    public NoClipWithRelatedEpisodeException(String skipMessage) {
        super(skipMessage);
    }
}
