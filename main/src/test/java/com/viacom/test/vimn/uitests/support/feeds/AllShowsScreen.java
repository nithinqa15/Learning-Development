package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.filters.FilterUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AllShowsScreen {

    private static JSONObject allShowsScreenObject;
    private static JSONArray modulesArray;

    private static void setAllShowsScreenObject() {
        String allShowsScreenUrl = MainConfig.getAllShowsScreenUrl();
        allShowsScreenObject = FilterUtils.getJSONFeed(allShowsScreenUrl);
    }

    private static void setModulesArray() {
        if (allShowsScreenObject == null) {
            setAllShowsScreenObject();
        }
        JSONObject dataObj = (JSONObject) allShowsScreenObject.get("data");
        JSONObject screenObj = (JSONObject) dataObj.get("screen");
        modulesArray = (JSONArray) screenObj.get("modules");
    }

    @SuppressWarnings("unchecked")
    private static String getFeedEndpointUrl(String aliasName) {
        if (modulesArray == null) {
            setModulesArray();
        }
        String url = "";
        for (JSONObject obj : (Iterable<JSONObject>) modulesArray) {
            JSONObject moduleObj = (JSONObject) obj.get("module");
            String alias = (String) moduleObj.get("alias");
            if (alias.equals(aliasName)) {
                url = (String) moduleObj.get("dataSource");
                break;
            }
        }
        return url;
    }

    public static String getAllShowsUrl() {
        return getFeedEndpointUrl("browse");
    }
}
