package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.filters.FilterUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LiveStream {

    private static JSONObject liveStreamFeedObject;
    private static JSONArray itemsArray;

    private static void setLiveStreamFeedObject() {
        String liveStreamEndpointUrl = LiveStreamScreen.getLiveStreamDataSourceUrl();
        liveStreamFeedObject = FilterUtils.getJSONFeed(liveStreamEndpointUrl);
    }

    private static void setItemsArray() {
        if (liveStreamFeedObject == null) {
            setLiveStreamFeedObject();
        }
        JSONObject dataObj = (JSONObject) liveStreamFeedObject.get("data");
        itemsArray = (JSONArray) dataObj.get("items");
    }

    @SuppressWarnings("unchecked")
    public static String getLiveTvScheduleFeedUrl() {
        if (itemsArray == null) {
            setItemsArray();
        }
        String liveTvScheduleFeedUrl = "";
        for (JSONObject itemObj : (Iterable<JSONObject>) itemsArray) {
            if (itemObj.get("entityType").toString().equals("tvschedule")) {
                liveTvScheduleFeedUrl = (String) itemObj.get("url");
                break;
            }
        }
        return liveTvScheduleFeedUrl;
    }
}
