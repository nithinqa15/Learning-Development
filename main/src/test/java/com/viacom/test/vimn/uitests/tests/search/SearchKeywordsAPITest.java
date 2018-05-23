package com.viacom.test.vimn.uitests.tests.search;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import java.util.ArrayList;
import java.util.Arrays;

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

public class SearchKeywordsAPITest extends BaseTest {

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
    JSONObject Messages;
    JSONObject errorMessage;
    String regPredectiveSerachURL;
    JSONObject statusObject;
    ArrayList<String> Searchkeyword;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SearchKeywordsAPITest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        settings = new Settings();
        alertsChain = new AlertsChain();
        
        //Collect series with keyword key //https://jira.mtvi.com/browse/PX-1045 - Keywords key is optional field - Confirmed with Sid
    	AllShowsPropertyFilter propertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
		allContentTypes = propertyFilter.withKeywords().propertyFilter();
		for (PropertyResult property : allContentTypes) {
			Logger.logMessage("Series Title: " + property.getPropertyTitle() +" -- " + "Keywords: " + property.getKeywords());
		}
    }

    @TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.FULL, GroupProps.Search })
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void searchKeywordsAPIAndroidTest() {
    	
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
    	
    	SoftAssert softAssert = new SoftAssert();
    	Logger.logMessage("SearchURL: " + MainConfig.getSearchServiceUrl());
    	
    	for (PropertyResult property : allContentTypes) {
    		Logger.logMessage("Checked keywords result for: " + property.getPropertyTitle());
    		
    			Searchkeyword = new ArrayList<String>((Arrays.asList(property.getKeywords().split(","))));
    		
    			for (String keyword : Searchkeyword) {
    				
    				Logger.logMessage("============================================"); //Separator for each result
    				regSearchURL = MainConfig.getSearchServiceUrl().replace("{searchTerm}", keyword.replace(" ", "%20"))
    	    			.replace("{types}", searchTypes); //URl doesn't contains space so used %20
    	    		
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
    	    			} else {
    	    				Messages =(JSONObject) FilterUtils.getJSONFeed(regSearchURL).get("messages"); //https://jira.mtvi.com/browse/PX-1046
    	    				if (Messages.containsKey("warnings") ? true : false) {
    	    					// Number of items returned by SOLR [X] is different from number of items returned by ARC [Y]
    	    					Logger.logMessage("Search Result successful with Warning message: " + Messages.get("warnings"));
    	    					softAssert.assertTrue(!Messages.containsKey("warnings"), regSearchURL + " " + "Search Result successful with Warning message: " + Messages.get("warnings"));
    	    				}
    	    			}
    	    		} catch (Exception e) {
    	    			e.printStackTrace();
    	    		}
    			}
    		settings.settingsBtn().waitForVisible(); // Used for keep live appium session
    	}
    	softAssert.assertAll();
    }
    
	@TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.FULL, GroupProps.Search })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS })
    public void searchKeywordsAPIiOSTest() {
    	
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
    	
    	SoftAssert softAssert = new SoftAssert();
    	Logger.logMessage("SearchURL: " + MainConfig.getSearchServiceUrl());
    	
    	for (PropertyResult property : allContentTypes) {
    		Logger.logMessage("Checked keywords result for: " + property.getPropertyTitle());
    		
    			Searchkeyword = new ArrayList<String>((Arrays.asList(property.getKeywords().split(","))));
    		
    			for (String keyword : Searchkeyword) {
    				
    				Logger.logMessage("============================================"); //Separator for each result
    				regSearchURL = MainConfig.getSearchServiceUrl().replace("{searchTerm}", keyword.replace(" ", "%20"))
    	    			.replace("{types}", searchTypes); //URl doesn't contains space so used %20
    	    		
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
    	    			} else {
    	    				Messages =(JSONObject) FilterUtils.getJSONFeed(regSearchURL).get("messages"); //https://jira.mtvi.com/browse/PX-1046
    	    				if (Messages.containsKey("warnings") ? true : false) {
    	    					// Number of items returned by SOLR [X] is different from number of items returned by ARC [Y]
    	    					Logger.logMessage("Search Result successful with Warning message: " + Messages.get("warnings"));
    	    					softAssert.assertTrue(!Messages.containsKey("warnings"), regSearchURL + " " + "Search Result successful with Warning message: " + Messages.get("warnings"));
    	    				}
    	    			}
    	    		} catch (Exception e) {
    	    			e.printStackTrace();
    	    		}
    			}
    		settings.settingsBtn().waitForVisible(); // Used for keep live appium session
    	}
    	softAssert.assertAll();
    }
}