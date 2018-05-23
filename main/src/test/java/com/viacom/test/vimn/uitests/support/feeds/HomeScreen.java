package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.filters.FilterUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HomeScreen {

    private static JSONObject homeScreenObject;
    private static JSONArray modulesArray;

    private static void setHomeScreenObject() {
        String homeScreenUrl = MainConfig.getHomeScreenUrl();
        homeScreenObject = FilterUtils.getJSONFeed(homeScreenUrl);
    }

    private static void setModulesArray() {
        if (homeScreenObject == null) {
            setHomeScreenObject();
        }
        JSONObject dataObj = (JSONObject) homeScreenObject.get("data");
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

    public static String getFeaturedSeriesUrl() {
        return getFeedEndpointUrl("featured_list");
    }

    public static String getPromoSeriesUrl() {
        return getFeedEndpointUrl("featured_list"); // Promolistfeed & Featuredfeed are same in API 1.9
    }
}
