package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.filters.FilterUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LiveStreamScreen {

    private static JSONObject liveStreamScreenObject;
    private static JSONArray liveStreamModulesArray;

    private static void setLiveStreamObject() {
        String liveStreamScreenUrl = MainConfig.getLiveStreamScreenUrl();
        liveStreamScreenObject = FilterUtils.getJSONFeed(liveStreamScreenUrl);
    }

    private static void setLiveStreamModulesArray()  {
        if (liveStreamScreenObject == null) {
            setLiveStreamObject();
        }
        JSONObject dataObj = (JSONObject) liveStreamScreenObject.get("data");
        JSONObject screenObj = (JSONObject) dataObj.get("screen");
        liveStreamModulesArray = (JSONArray) screenObj.get("modules");
    }

    @SuppressWarnings("unchecked")
    public static String getLiveStreamDataSourceUrl() {
        if (liveStreamModulesArray == null) {
            setLiveStreamModulesArray();
        }
        String liveStreamDataSourceUrl = "";
        for (JSONObject obj : (Iterable<JSONObject>) liveStreamModulesArray) {
            JSONObject moduleObj = (JSONObject) obj.get("module");
            liveStreamDataSourceUrl = (String) moduleObj.get("dataSource");
            if (liveStreamDataSourceUrl != null) {
                break;
            }
        }
        return liveStreamDataSourceUrl;
    }

}
