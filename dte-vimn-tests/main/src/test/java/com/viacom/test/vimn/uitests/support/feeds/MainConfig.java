package com.viacom.test.vimn.uitests.support.feeds;

import com.viacom.test.vimn.common.filters.FilterUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// This class gets your information regarding the Main Config
public class MainConfig {

	private static JSONObject mainFeedObject;
	private static JSONObject appConfigurationObject;

	private static void setAppConfigurationObject() {
		if (mainFeedObject == null) {
			String mainFeedURL = FeedFactory.getAppMainFeedURL();
			mainFeedObject = FilterUtils.getJSONFeed(mainFeedURL);
		}
		JSONObject dataObj = (JSONObject) mainFeedObject.get("data");
		appConfigurationObject = (JSONObject) dataObj.get("appConfiguration");
	}

	private static JSONObject getAppConfigurationObject() {
		if (appConfigurationObject == null) {
			setAppConfigurationObject();
		}
		return appConfigurationObject;
	}
	
	/**********************************************************************************************
	 * Determines if app has Short Form content available or not
	 *
	 * @param URL
	 *          - {@link String} - The data feed url to query.
	 * @author Gabriel Fioretti
	 * @version 1.0.0 August 22, 2017
	 * @return Boolean
	 ***********************************************************************************************/
	public static Boolean isShortFormEnabled() {
		boolean shortFormObj = (boolean) getAppConfigurationObject().get("shortForm");
		return shortFormObj;
	}
	
	/**********************************************************************************************
	 * Determines if app has Pagination enable or not
	 *
	 * @param URL
	 *          - {@link String} - The data feed url to query.
	 * @author Pritesh Valand
	 * @version 1.0.0 Sep 28, 2017
	 * @return Boolean
	 ***********************************************************************************************/
	public static Boolean isPaginationEnabled() {
		String pageSize = getAppConfigurationObject().get("pageSize").toString();
		return pageSize.equals("0");
	}
	
	/**********************************************************************************************
	 * Determines if app has Background Video enabled
	 *
	 * @param URL
	 *          - {@link String} - The data feed url to query.
	 * @author Gabriel Fioretti
	 * @version 1.0.0 August 22, 2017
	 * @return Boolean
	 ***********************************************************************************************/
	public static Boolean isBackgroundVideoServiceEnabled() {
		String backgroundServiceVideoEnabled = getAppConfigurationObject().get("backgroundServiceVideoEnabled").toString();
		return Boolean.parseBoolean(backgroundServiceVideoEnabled);
	}

	public static Boolean isBackgroundVideoServiceNotEnabled() {
		boolean backgroundServiceVideoEnabled = (boolean) getAppConfigurationObject().get("backgroundServiceVideoEnabled");
		return !backgroundServiceVideoEnabled;
	}
	
	public static String balaLatestUpdatedTimeStamp() {
		String balaLatestUpdatedTimeStamp = getAppConfigurationObject().get("balaLatestUpdatedTimeStamp").toString();
		return balaLatestUpdatedTimeStamp;
	}

	public static Boolean isMoatEnabled() {
		String moatEnabled = getAppConfigurationObject().get("moatEnabled").toString();
		return	Boolean.parseBoolean(moatEnabled);
	}

	public static Boolean isPrerollsForClipsEnabled() {
		String prerollsForClips = getAppConfigurationObject().get("clipPrerollsEnabled").toString();
		return Boolean.parseBoolean(prerollsForClips);
	}
	
	public static Boolean isAllShowsEnabled() {
		String allShowsEnabled = getAppConfigurationObject().get("allShowsEnabled").toString();
		return Boolean.parseBoolean(allShowsEnabled);
	}
	
	public static Boolean isSearchServiceEnabled() {
		boolean searchEnabled ;
		boolean searchServiceEnabled = (boolean) getAppConfigurationObject().get("searchServiceEnabled");
		boolean searchPredictiveServiceEnabled = (boolean) getAppConfigurationObject().get("searchPredictiveServiceEnabled");
		if (searchServiceEnabled && searchPredictiveServiceEnabled) {
			searchEnabled = true;
		} else {
			searchEnabled = false;
		}
		return searchEnabled;
	}
	
	public static Boolean isDisplayLiveTVForAllUsersEnabled() {
		String displayLiveTVForAllUsers = getAppConfigurationObject().get("displayLiveTVForAllUsers").toString();
		return Boolean.parseBoolean(displayLiveTVForAllUsers);
	}

	/**********************************************************************************************
	 * Determines if app has Short Form content available or not
	 *
	 * @param URL
	 *          - {@link String} - The data feed url to query.
	 * @author Pritesh Valand
	 * @version 1.0.0 Oct 31, 2017
	 * @return Boolean
	 ***********************************************************************************************/
	public static Boolean isApptentiveEnabled() {
		boolean apptentiveObj = (boolean) getAppConfigurationObject().get("apptentiveEnabled");
		return apptentiveObj;
	}
    
    public static Boolean isMarketingEnabled() {
        boolean marketingEnabledObj = (boolean) getAppConfigurationObject().get("marketingEnabled");
        return marketingEnabledObj;
    }

	@SuppressWarnings("unchecked")
	private static String getScreenUrl(String screenName) {
		String screenUrl = "";
		JSONArray screensArr = (JSONArray) getAppConfigurationObject().get("screens");
		for (JSONObject obj : (Iterable<JSONObject>) screensArr) {
			JSONObject screenObj = (JSONObject) obj.get("screen");
			String nameObj = (String) screenObj.get("name");
			if (nameObj.equals(screenName)) {
				screenUrl = screenObj.get("url").toString();
				break;
			}
		}
		return screenUrl;
	}

	public static String getHomeScreenUrl() {
		return getScreenUrl("home");
	}
	
	public static String getAllShowsScreenUrl() {
		return getScreenUrl("browse");
	}

	public static String getLiveStreamScreenUrl() {
		return getScreenUrl("live");
	}
	
	public static String getSearchServiceUrl() {
		return getAppConfigurationObject().get("searchServiceUrl").toString();
	}
	
	public static String getSearchPredictiveServiceUrl() {
		return getAppConfigurationObject().get("searchPredictiveServiceUrl").toString();
	}

}
