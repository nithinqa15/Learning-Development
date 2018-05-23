package com.viacom.test.vimn.common.megabeacon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

import de.sstoehr.harreader.model.HarEntry;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MegabeaconLogUtils {

    private final static String MEGABEACON_PATTERN = ".*mb.mtvnservices.com/data/collect/v1/\\?__t=vmn_bento.*";

    /**
     * Returns a list of all Megabeacon calls
     *
     * @return list of HarEntry
     */
    private static List<HarEntry> getAllCalls() {
        return ProxyRESTManager.getLogEntries().stream().filter(MegabeaconLogUtils::filterByUrl)
                .collect(Collectors.toList());
    }


    /**
     * Returns a list of Megabeacon calls matching a unique key-value respective to the
     * Megabeacon call under test.
     *
     * @param unique identifier key-value that's present on the call
     * @return list of Megabeacon Data section HashMaps
     */
    private static List<HashMap<String, String>> getActualMaps(String Key, String Value) {
        return getAllCalls().stream().map(MegabeaconLogUtils::jsonDataSectionToMap)
                .filter(map -> map.containsKey(Key) && map.containsValue(Value))
                .collect(Collectors.toList());
    }

    public static void waitForCall(String key, String value, int timeoutSec) {
        Logger.logMessage("Waiting for Megabeacon call key=" + key + " value=" + value);
        new FluentWait<>("").pollingEvery(1, TimeUnit.SECONDS).withTimeout(Integer.parseInt(ConfigProps.SERVER_COMMAND_TIMEOUT), TimeUnit.SECONDS)
                .withMessage("No Megabeacon calls key=" + key + " value=" + value + " found")
                .until(stub -> MegabeaconLogUtils.getActualMaps(key, value).size() > 0);
    }

    public static HashMap<String, String> getActualMap(String Key, String Value) {
        List<HashMap<String, String>> foundCalls = getActualMaps(Key, Value);

        if (foundCalls.isEmpty()) {
            throw new AssertionError("No Megabeacon calls found for Key=" + Key + " Value=" + Value);
        }

        return getActualMaps(Key, Value).get(0);
    }

    private static boolean filterByUrl(HarEntry entry) {
        String request = ProxyLogUtils.decodeUrl(entry.getRequest().getUrl());
        return request.matches(MEGABEACON_PATTERN);
    }

    private static HashMap<String, String> jsonDataSectionToMap(HarEntry entry) {
        try {
            @SuppressWarnings({ "unchecked", "rawtypes" })
			HashMap<String, Object> events = ((ArrayList<HashMap>) new ObjectMapper()
                    .readValue(entry.getRequest().getPostData().getText(), HashMap.class).get("events")).get(0);
            @SuppressWarnings("unchecked")
			HashMap<String, String> dataSection = (HashMap<String, String>) events.get("data");
            return dataSection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
