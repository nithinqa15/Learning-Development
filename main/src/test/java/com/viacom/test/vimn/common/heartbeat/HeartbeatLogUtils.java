package com.viacom.test.vimn.common.heartbeat;

import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarQueryParam;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HeartbeatLogUtils {

    private final static String HEARTBEAT_PATTERN = ".*mtv.hb.omtrdc.net/.*";

    /**
     * Returns a list of all Megabeacon calls
     *
     * @return list of HarEntry
     */
    private static List<HarEntry> getAllCalls() {
        return ProxyRESTManager.getLogEntries().stream().filter(HeartbeatLogUtils::filterByUrl)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of Heartbeat calls matching a unique key-value respective to the
     * Heartbeat call under test.
     *
     * @param unique identifier key-value that's present on the call
     * @return list of Heartbeat Data section HashMaps
     */
    private static List<HashMap<String, String>> getActualMaps(String Key, String Value) {
        return getAllCalls().stream().map(HeartbeatLogUtils::callToMap)
                .filter(map -> map.containsKey(Key) && map.containsValue(Value))
                .collect(Collectors.toList());
    }

    public static void waitForCall(String key, String value, int timeoutSec) {
        Logger.logMessage("Waiting for Heartbeat call key=" + key + " value=" + value);
        new FluentWait<>("").pollingEvery(1, TimeUnit.SECONDS).withTimeout(Integer.parseInt(ConfigProps.SERVER_COMMAND_TIMEOUT), TimeUnit.SECONDS)
                .withMessage("No Heartbeat calls key=" + key + " value=" + value + " found")
                .until(stub -> HeartbeatLogUtils.getActualMaps(key, value).size() > 0);
    }

    public static HashMap<String, String> getActualMap(String Key, String Value) {
        List<HashMap<String, String>> foundCalls = getActualMaps(Key, Value);

        if (foundCalls.isEmpty()) {
            throw new AssertionError("No Heartbeat calls found for Key=" + Key + " Value=" + Value);
        }

        return getActualMaps(Key, Value).get(0);
    }

    private static HashMap<String, String> callToMap(HarEntry entry) {
        List<HarQueryParam> paramList = entry.getRequest().getQueryString();
        HashMap<String, String> paramMap = new HashMap<>();
        paramList.forEach(queryParam -> paramMap.put(queryParam.getName(), queryParam.getValue()));
        return paramMap;
    }

    private static boolean filterByUrl(HarEntry entry) {
        String request = ProxyLogUtils.decodeUrl(entry.getRequest().getUrl());
        return request.matches(HEARTBEAT_PATTERN);
    }
}
