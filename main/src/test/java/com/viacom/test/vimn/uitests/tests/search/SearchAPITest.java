package com.viacom.test.vimn.uitests.tests.search;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.FilterUtils;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SearchAPITest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    Settings settings;
    AlertsChain alertsChain;

    // Declare Data
    String searchTypes="series,movie,fight,event,playlist";
    PropertyResults allContentTypes;
    String regSearchURL;
    String responseText;
    String responseCode;
    JSONObject errorMessage;
    String regPredectiveSerachURL;
    JSONObject statusObject;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SearchAPITest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        settings = new Settings();
        alertsChain = new AlertsChain();
        
    	AllShowsPropertyFilter propertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
		allContentTypes = propertyFilter.propertyFilter();
    }

    @TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.FULL, GroupProps.Search })
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void searchAPIAndroidTest() {
    	
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
    	
    	SoftAssert softAssert = new SoftAssert();
    	Logger.logMessage("SearchURL: " + MainConfig.getSearchServiceUrl());
    	Logger.logMessage("predectiveSerachURL: " + MainConfig.getSearchPredictiveServiceUrl());
    	
    	for (PropertyResult seriesTitle : allContentTypes) {
    		Logger.logMessage("Checked search result for: " + seriesTitle.getPropertyTitle());
    		regSearchURL = MainConfig.getSearchServiceUrl().replace("{searchTerm}", seriesTitle.getPropertyTitle().replace(" ", "%20"))
    			.replace("{types}", searchTypes); //URl doesn't contains space so used %20
    		regPredectiveSerachURL = MainConfig.getSearchPredictiveServiceUrl().replace("{searchTerm}", seriesTitle.getPropertyTitle().replace(" ", "")
    				.substring(0, 3)).replace("{types}", searchTypes); //Search type ahead check with first three character of series title 
    		
    		try { // Validate search result
    			statusObject = (JSONObject) FilterUtils.checkSearchResult(regSearchURL);
    			if (statusObject==null) { //Broken url
    				softAssert.assertEquals(statusObject, "OK", regSearchURL);
    			}
    			
    			responseText = statusObject.get("text").toString();
    			responseCode = statusObject.get("code").toString();
    			Logger.logMessage(regSearchURL + " ==== "+ responseCode + "," + responseText);
    			
    			if (responseText.equalsIgnoreCase("EMPTY RESULT")) { //valid url with empty result
    				errorMessage = (JSONObject) FilterUtils.getJSONFeed(regSearchURL).get("messages");
    				softAssert.assertEquals(responseText, "OK", regSearchURL);
    				softAssert.assertEquals(responseCode, "100");
    				Logger.logMessage(" Error message: " + errorMessage);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		try { //Validate predictive search result
    			statusObject = (JSONObject) FilterUtils.checkSearchResult(regPredectiveSerachURL);
    			if (statusObject==null) { //Broken url
    				softAssert.assertEquals(statusObject, "OK", regPredectiveSerachURL);
    			}
    			
    			responseText = statusObject.get("text").toString();
    			responseCode = statusObject.get("code").toString();
    			Logger.logMessage(regPredectiveSerachURL + " ==== "+ responseCode + "," + responseText);
    			
    			if (responseText.equalsIgnoreCase("EMPTY RESULT")) { //valid url with empty result
    				errorMessage = (JSONObject) FilterUtils.getJSONFeed(regPredectiveSerachURL).get("messages");
    				softAssert.assertEquals(responseText, "OK", regPredectiveSerachURL);
    				softAssert.assertEquals(responseCode, "100");
    				Logger.logMessage(" Error message: " + errorMessage);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		settings.settingsBtn().waitForVisible(); // Used for keep live appium session
    	}
    	softAssert.assertAll();
    }
    
	@TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.FULL, GroupProps.Search })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS })
    public void searchAPIiOSTest() {
    	
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
    	
    	SoftAssert softAssert = new SoftAssert();
    	Logger.logMessage("SearchURL: " + MainConfig.getSearchServiceUrl());
    	Logger.logMessage("predectiveSerachURL: " + MainConfig.getSearchPredictiveServiceUrl());
    	
    	for (PropertyResult seriesTitle : allContentTypes) {
    		Logger.logMessage("Checked search result for: " + seriesTitle.getPropertyTitle());
    		regSearchURL = MainConfig.getSearchServiceUrl().replace("{searchTerm}", seriesTitle.getPropertyTitle().replace(" ", "%20"))
    			.replace("{types}", searchTypes); //URl doesn't contains space so used %20
    		regPredectiveSerachURL = MainConfig.getSearchPredictiveServiceUrl().replace("{searchTerm}", seriesTitle.getPropertyTitle().replace(" ", "")
    				.substring(0, 3)).replace("{types}", searchTypes); //Search type ahead check with first three character of series title 
    		
    		try { // Validate search result
    			statusObject = (JSONObject) FilterUtils.checkSearchResult(regSearchURL);
    			if (statusObject==null) { //Broken url
    				softAssert.assertEquals(statusObject, "OK", regSearchURL);
    			}
    			
    			responseText = statusObject.get("text").toString();
    			responseCode = statusObject.get("code").toString();
    			Logger.logMessage(regSearchURL + " ==== "+ responseCode + "," + responseText);
    			
    			if (responseText.equalsIgnoreCase("EMPTY RESULT")) { //valid url with empty result
    				errorMessage = (JSONObject) FilterUtils.getJSONFeed(regSearchURL).get("messages");
    				softAssert.assertEquals(responseText, "OK", regSearchURL);
    				softAssert.assertEquals(responseCode, "100");
    				Logger.logMessage(" Error message: " + errorMessage);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		try { //Validate predictive search result
    			statusObject = (JSONObject) FilterUtils.checkSearchResult(regPredectiveSerachURL);
    			if (statusObject==null) { //Broken url
    				softAssert.assertEquals(statusObject, "OK", regPredectiveSerachURL);
    			}
    			
    			responseText = statusObject.get("text").toString();
    			responseCode = statusObject.get("code").toString();
    			Logger.logMessage(regPredectiveSerachURL + " ==== "+ responseCode + "," + responseText);
    			
    			if (responseText.equalsIgnoreCase("EMPTY RESULT")) { //valid url with empty result
    				errorMessage = (JSONObject) FilterUtils.getJSONFeed(regPredectiveSerachURL).get("messages");
    				softAssert.assertEquals(responseText, "OK", regPredectiveSerachURL);
    				softAssert.assertEquals(responseCode, "100");
    				Logger.logMessage(" Error message: " + errorMessage);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		settings.settingsBtn().waitForVisible(); // Used for keep live appium session
    	}
    	softAssert.assertAll();
    }
}