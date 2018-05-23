package com.viacom.test.vimn.uitests.tests.smokesuite;

import com.viacom.test.vimn.common.filters.FilterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.MainConfigEndPointExpectedFile;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ConfigEndPointTest extends BaseTest {

	// Declare page objects/actions
	private static JSONObject mainFeedObject;
	private static JSONObject homeScreenObject;
	private static JSONObject AllShowsScreenObject;
	private static String brandName;
	private static String geoCodeFromTestNGConfig;
	private static String geoCodeFromLocalDataConfig;
	private static String getCountryFromLocalDataConfig;
	private static String testMainFeedurl;
	private static String HomeScreenURLEndPoint;
	private static String FeaturedURLEndPoint;
	private static String BrowseScreenURLEndPoint;
	private static String AllShowsListURLEndPoint;
	private static String liveScreenURLEndPoint;
	private Settings settings;
	private static List<String> totalRegionAvailableFromLocalDataConfig;
	private static Map<String, String> expectedMainFeedAppValuesMap;
	private static Map<String, String> actualMainFeedAppValuesMap;
	private static MainConfigEndPointExpectedFile expectedFile;
	private static JSONArray modulesArray;
	SplashChain splashChain;
	AlertsChain alertsChain;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ConfigEndPointTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		new BrandDataFactory();
		settings = new Settings();
		splashChain = new SplashChain();
		alertsChain = new AlertsChain();
		expectedFile = new MainConfigEndPointExpectedFile();
	}
	
	@TestCaseId("55736")
	@Features(GroupProps.CONFIG_END_POINT)
	@Test(groups = { GroupProps.FULL, GroupProps.CONFIG_END_POINT, GroupProps.SMOKE})
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void configEndPointAndroidTest() {

		SoftAssert softAssert = new SoftAssert();

		// Construct brand name to chceck status in local data config
		if (TestRun.isCCDomesticApp() || TestRun.isCCINTLApp()) {
			brandName = "isCC";
		} else if (TestRun.isMTVDomesticApp() || TestRun.isMTVINTLApp()) {
			brandName = "isMTV";
		} else if (TestRun.isBETDomesticApp() || TestRun.isBETINTLApp()) {
			brandName = "isBET";
		} else {
			brandName = "is" + TestRun.getAppName();
		}
		Logger.logConsoleMessage("BrandName check in LocaleDataConfig: " + brandName);

		// Use this code for construct main feed for other region
		geoCodeFromTestNGConfig = LocaleDataFactory.getIsoGeoCode();
		Logger.logConsoleMessage("Geo code from TestNG: " + geoCodeFromTestNGConfig);

		// To get the all regions from local data config to test
		totalRegionAvailableFromLocalDataConfig = LocaleDataFactory.getRegionList(brandName);
		
		if (TestRun.isCCDomesticApp() || TestRun.isMTVDomesticApp() || TestRun.isBETDomesticApp()) {
			List<String> retainOnlyUSList = new ArrayList<String>();
			retainOnlyUSList.add(0, TestRun.getLocale());
			totalRegionAvailableFromLocalDataConfig.retainAll(retainOnlyUSList);
		} else if (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp() || TestRun.isBETINTLApp()) {
			totalRegionAvailableFromLocalDataConfig.remove("en_us");
		}

		for (String region : totalRegionAvailableFromLocalDataConfig) {
			geoCodeFromLocalDataConfig = LocaleDataFactory.getGeoCode(region);
			Logger.logConsoleMessage("Geo code from LocalDataConfig: " + geoCodeFromLocalDataConfig);
			
			getCountryFromLocalDataConfig = LocaleDataFactory.getCountry(region);
			Logger.logConsoleMessage("Country from LocalDataConfig: " + getCountryFromLocalDataConfig);

			// Validate end point for main config
			testMainFeedurl = FeedFactory.getAppMainFeedURL().replace(geoCodeFromTestNGConfig,
					geoCodeFromLocalDataConfig);
			FilterUtils.checkStatusCode(testMainFeedurl, "MainConfig");

			expectedMainFeedAppValuesMap = expectedFile.expectedMainConfigParamValueMap(geoCodeFromLocalDataConfig);

			actualMainFeedAppValuesMap = new HashMap<String, String>();
			JSONObject appConfigurationObj = getAppConfigurationObject(testMainFeedurl);

			for (String key : expectedMainFeedAppValuesMap.keySet()) {
				try {
					actualMainFeedAppValuesMap.put(key, appConfigurationObj.get(key).toString());
				} catch (Exception e) {
					Logger.logMessage("Testing key not found in Main config : " + key);
					softAssert.assertTrue(false, "Testing key" + " -- " + key + " -- not found in Main config : " + testMainFeedurl);
				}
			}

			for (String key : expectedMainFeedAppValuesMap.keySet()) {
				String actualValue = actualMainFeedAppValuesMap.get(key);
				String expectedValue = expectedMainFeedAppValuesMap.get(key);
				Logger.logMessage("Testing the key: " + key);
				Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
				softAssert.assertEquals(actualValue, expectedValue,
						key + " Key failed for " + getCountryFromLocalDataConfig + "("+geoCodeFromLocalDataConfig+")"+ " : \n" + testMainFeedurl);
			}

			// Validate end point for home screen
			HomeScreenURLEndPoint = getHomeScreenUrl(testMainFeedurl);
			FeaturedURLEndPoint = getFeaturedListUrl();
			if (HomeScreenURLEndPoint == null && FeaturedURLEndPoint == null) {
				Logger.logConsoleMessage("Homescreen end point not available on: \n" + testMainFeedurl);
				softAssert.assertTrue(false, "Homescreen end point not available on: " + testMainFeedurl);
			} else {
				FilterUtils.checkStatusCode(HomeScreenURLEndPoint, "HomeScreen");
				FilterUtils.checkStatusCode(FeaturedURLEndPoint, "FeaturedList");
			}

			// Validate end point for browse screen
			BrowseScreenURLEndPoint = getAllShowsScreenUrl(testMainFeedurl);
			AllShowsListURLEndPoint = getAllshowsListUrl();
			if (BrowseScreenURLEndPoint == null && AllShowsListURLEndPoint == null) {
				Logger.logConsoleMessage("Browsescreen end point not available on: \n" + testMainFeedurl);
				softAssert.assertTrue(false, "Browsescreen end point not available on: " + testMainFeedurl);
			} else {
				FilterUtils.checkStatusCode(BrowseScreenURLEndPoint, "BrowseScreen");
				FilterUtils.checkStatusCode(AllShowsListURLEndPoint, "AllShowsList");
			}

			// Validate end point for live screen
			if (!BrandDataFactory.hasLiveTvEnabled() || geoCodeFromLocalDataConfig.equalsIgnoreCase("GB")) { //Live TV not available in UK) {
				Logger.logConsoleMessage("LiveTV not availablec for " + TestRun.getAppName() + " in :" + getCountryFromLocalDataConfig + "("+geoCodeFromLocalDataConfig+")");
			} else {
				liveScreenURLEndPoint = getLiveStreamScreenUrl(testMainFeedurl);
				if (liveScreenURLEndPoint == null) {
					Logger.logConsoleMessage("Livescreen end point not available on: \n" + testMainFeedurl);
					softAssert.assertTrue(false, "Livescreen end point not available on: " + testMainFeedurl);
				} else {
					FilterUtils.checkStatusCode(liveScreenURLEndPoint, "LiveScreen");
				}
			}
			settings.settingsBtn().waitForVisible(); // Used for keep live appium session
		}
		softAssert.assertAll();
	}

	@TestCaseId("55736")
	@Features(GroupProps.CONFIG_END_POINT)
	@Test(groups = { GroupProps.FULL, GroupProps.CONFIG_END_POINT, GroupProps.SMOKE})
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void configEndPointiOSTest() {
	 	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();

		SoftAssert softAssert = new SoftAssert();

		// Construct brand name to chceck status in local data config
		if (TestRun.isCCDomesticApp() || TestRun.isCCINTLApp()) {
			brandName = "isCC";
		} else if (TestRun.isMTVDomesticApp() || TestRun.isMTVINTLApp()) {
			brandName = "isMTV";
		} else if (TestRun.isBETDomesticApp() || TestRun.isBETINTLApp()) {
			brandName = "isBET";
		} else {
			brandName = "is" + TestRun.getAppName();
		}
		Logger.logConsoleMessage("BrandName check in LocaleDataConfig: " + brandName);

		// Use this code for construct main feed for other region
		geoCodeFromTestNGConfig = LocaleDataFactory.getIsoGeoCode();
		Logger.logConsoleMessage("Geo code from TestNG: " + geoCodeFromTestNGConfig);

		// To get the all regions from local data config to test
		totalRegionAvailableFromLocalDataConfig = LocaleDataFactory.getRegionList(brandName);
		
		if (TestRun.isCCDomesticApp() || TestRun.isMTVDomesticApp() || TestRun.isBETDomesticApp()) {
			List<String> retainOnlyUSList = new ArrayList<String>();
			retainOnlyUSList.add(0, TestRun.getLocale());
			totalRegionAvailableFromLocalDataConfig.retainAll(retainOnlyUSList);
		} else if (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp() || TestRun.isBETINTLApp()) {
			totalRegionAvailableFromLocalDataConfig.remove("en_us");
		}

		for (String region : totalRegionAvailableFromLocalDataConfig) {
			geoCodeFromLocalDataConfig = LocaleDataFactory.getGeoCode(region);
			Logger.logConsoleMessage("Geo code from LocalDataConfig: " + geoCodeFromLocalDataConfig);
			
			getCountryFromLocalDataConfig = LocaleDataFactory.getCountry(region);
			Logger.logConsoleMessage("Country from LocalDataConfig: " + getCountryFromLocalDataConfig);

			// Validate end point for main config
			testMainFeedurl = FeedFactory.getAppMainFeedURL().replace(geoCodeFromTestNGConfig,
					geoCodeFromLocalDataConfig);
			FilterUtils.checkStatusCode(testMainFeedurl, "MainConfig");

			expectedMainFeedAppValuesMap = expectedFile.expectedMainConfigParamValueMap(geoCodeFromLocalDataConfig);

			actualMainFeedAppValuesMap = new HashMap<String, String>();
			JSONObject appConfigurationObj = getAppConfigurationObject(testMainFeedurl);

			for (String key : expectedMainFeedAppValuesMap.keySet()) {
				try {
					actualMainFeedAppValuesMap.put(key, appConfigurationObj.get(key).toString());
				} catch (Exception e) {
					Logger.logMessage("Testing key not found in Main config : " + key);
					softAssert.assertTrue(false, "Testing key" + " -- " + key + " -- not found in Main config : " + testMainFeedurl);
				}
			}

			for (String key : expectedMainFeedAppValuesMap.keySet()) {
				String actualValue = actualMainFeedAppValuesMap.get(key);
				String expectedValue = expectedMainFeedAppValuesMap.get(key);
				Logger.logMessage("Testing the key: " + key);
				Logger.logMessage("Expected Value: " + expectedValue + " || Actual Value: " + actualValue);
				softAssert.assertEquals(actualValue, expectedValue,
						key + " Key failed for " + getCountryFromLocalDataConfig + "("+geoCodeFromLocalDataConfig+")"+ " : \n" + testMainFeedurl);
			}

			// Validate end point for home screen
			HomeScreenURLEndPoint = getHomeScreenUrl(testMainFeedurl);
			FeaturedURLEndPoint = getFeaturedListUrl();
			if (HomeScreenURLEndPoint == null && FeaturedURLEndPoint == null) {
				Logger.logConsoleMessage("Homescreen end point not available on: \n" + testMainFeedurl);
				softAssert.assertTrue(false, "Homescreen end point not available on: " + testMainFeedurl);
			} else {
				FilterUtils.checkStatusCode(HomeScreenURLEndPoint, "HomeScreen");
				FilterUtils.checkStatusCode(FeaturedURLEndPoint, "FeaturedList");
			}

			// Validate end point for browse screen
			BrowseScreenURLEndPoint = getAllShowsScreenUrl(testMainFeedurl);
			AllShowsListURLEndPoint = getAllshowsListUrl();
			if (BrowseScreenURLEndPoint == null && AllShowsListURLEndPoint == null) {
				Logger.logConsoleMessage("Browsescreen end point not available on: \n" + testMainFeedurl);
				softAssert.assertTrue(false, "Browsescreen end point not available on: " + testMainFeedurl);
			} else {
				FilterUtils.checkStatusCode(BrowseScreenURLEndPoint, "BrowseScreen");
				FilterUtils.checkStatusCode(AllShowsListURLEndPoint, "AllShowsList");
			}

			// Validate end point for live screen
			if (!BrandDataFactory.hasLiveTvEnabled() || geoCodeFromLocalDataConfig.equalsIgnoreCase("GB")) { //Live TV not available in UK)
				Logger.logConsoleMessage("LiveTV not availablec for " + TestRun.getAppName() + " in :" + getCountryFromLocalDataConfig + "("+geoCodeFromLocalDataConfig+")");
			} else {
				liveScreenURLEndPoint = getLiveStreamScreenUrl(testMainFeedurl);
				if (liveScreenURLEndPoint == null) { 
					Logger.logConsoleMessage("Livescreen end point not available on: \n" + testMainFeedurl);
					softAssert.assertTrue(false, "Livescreen end point not available on: " + testMainFeedurl);
				} else {
					FilterUtils.checkStatusCode(liveScreenURLEndPoint, "LiveScreen");
				}
			}
			settings.settingsBtn().waitForVisible(); // Used for keep live appium session
		}
		softAssert.assertAll();
	}

	private static JSONObject getAppConfigurationObject(String mainFeedURL) {
		mainFeedObject = FilterUtils.getJSONFeed(mainFeedURL);
		JSONObject dataObj = (JSONObject) mainFeedObject.get("data");
		return (JSONObject) dataObj.get("appConfiguration");
	}

	@SuppressWarnings("unchecked")
	private static String getScreenUrl(String mainFeedURL, String screenName) {
		String screenUrl = null;
		JSONArray screensArr = (JSONArray) getAppConfigurationObject(mainFeedURL).get("screens");
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

	public static String getHomeScreenUrl(String mainFeedURL) {
		return getScreenUrl(mainFeedURL, "home");
	}

	@SuppressWarnings("unchecked")
	private static String getFeaturedListEndpointUrl(String aliasName) {
		String url = "";
		homeScreenObject = FilterUtils.getJSONFeed(HomeScreenURLEndPoint);
		JSONObject dataObj = (JSONObject) homeScreenObject.get("data");
		JSONObject screenObj = (JSONObject) dataObj.get("screen");
		modulesArray = (JSONArray) screenObj.get("modules");
		
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

	public static String getFeaturedListUrl() {
		return getFeaturedListEndpointUrl("featured_list");
	}
	
	public static String getAllShowsScreenUrl(String mainFeedURL) {
		return getScreenUrl(mainFeedURL, "browse");
	}
	
	@SuppressWarnings("unchecked")
	private static String getAllShowsListEndpointUrl(String aliasName) {
		String url = "";
		AllShowsScreenObject = FilterUtils.getJSONFeed(BrowseScreenURLEndPoint);
		JSONObject dataObj = (JSONObject) AllShowsScreenObject.get("data");
		JSONObject screenObj = (JSONObject) dataObj.get("screen");
		modulesArray = (JSONArray) screenObj.get("modules");
		
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
	
	public static String getAllshowsListUrl() {
		return getAllShowsListEndpointUrl("browse");
	}
	
	public static String getLiveStreamScreenUrl(String mainFeedURL) {
		return getScreenUrl(mainFeedURL, "live");
	}
}
