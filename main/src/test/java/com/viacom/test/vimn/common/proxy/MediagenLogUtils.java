package com.viacom.test.vimn.common.proxy;


import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;
import de.sstoehr.harreader.model.HarEntry;

import java.util.Iterator;
import java.util.List;

public class MediagenLogUtils {

    public boolean hasMediagenCall(String mgid) {

        String mediagenUrlPattern = Config.StaticProps.MEDIAGEN_URL;
        List<HarEntry> logEntries = ProxyRESTManager.getLogEntries();
        Iterator<HarEntry> iterator = logEntries.iterator();
        while (iterator.hasNext()) {
            HarEntry entry = iterator.next();
            try {
                if (entry.getRequest().getUrl().contains(mediagenUrlPattern) && entry.getRequest().getUrl().contains(mgid)) {
                    Logger.logMessage("Mediagen URL: " + entry.getRequest().getUrl());
                    return true;
                }
            } catch (NullPointerException e) {
                // ignore as bmp throws null pointer exception in the event a request/response doesn't
                // have the piece of data in the har we're checking for... known bmp bug...
            }
        }
        return false;
    }
}
