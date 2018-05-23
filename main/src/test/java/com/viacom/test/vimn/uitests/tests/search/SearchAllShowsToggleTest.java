package com.viacom.test.vimn.uitests.tests.search;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Search;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SearchAllShowsToggleTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    AllShows allShows;
    Home home;
    AlertsChain alertsChain;
    Search search;

    // Declare Data
    boolean searchServicedEnabled = false;
    boolean AllShowsEnabled = false;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SearchAllShowsToggleTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        allShows = new AllShows();
        home = new Home();
        search = new Search();
        alertsChain = new AlertsChain();
        
        searchServicedEnabled = MainConfig.isSearchServiceEnabled();
        AllShowsEnabled = MainConfig.isAllShowsEnabled();
    }

    @TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.BROKEN, GroupProps.ALL_SHOWS, GroupProps.Search })
    @Parameters({ParamProps.ANDROID, ParamProps.PARAMOUNT_APP})
    public void searchAllShowsToggleAndroidTest() {

    	//TO-DO
    }
    
    @TestCaseId("") // Not available yet
    @Features(GroupProps.Search)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS, GroupProps.Search })
    @Parameters({ParamProps.IOS, ParamProps.PARAMOUNT_APP}) //Only tested on Paramount yet
    public void searchAllShowsToggleiOSTest() {
    	
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
    	
    	Logger.logMessage("Search serviced enabled: " + searchServicedEnabled);
    	Logger.logMessage("All Shows enabled: " + AllShowsEnabled );
        
        if (searchServicedEnabled && AllShowsEnabled) { //Scenario-1, Search icon on home & second screen will be search field with all shows content
        	allShows.allShowsBtn().waitForNotPresentOrVisible();
        	home.searchIcon().waitForVisible().tap();
            search.searchFieldBtn().waitForVisible();
            search.allSearchResultsBtn().waitForVisible();
        } else if (!searchServicedEnabled && !AllShowsEnabled) { //Scenario-4, NO icon on home
        	home.searchIcon().waitForNotPresentOrVisible();
        	allShows.allShowsBtn().waitForNotPresentOrVisible();
        } else if (!searchServicedEnabled && AllShowsEnabled) { //Scenario-2, All shows icon on home & second screen will be only all shows content (No search field present)
        	home.searchIcon().waitForNotPresentOrVisible();
        	allShows.allShowsBtn().waitForVisible().tap();
        	search.searchFieldBtn().waitForNotPresentOrVisible();
        } else if (searchServicedEnabled && !AllShowsEnabled) { //Scenario-3, Search icon on home & second screen will be only search field (All shows content not present)
        	allShows.allShowsBtn().waitForNotPresentOrVisible();
        	home.searchIcon().waitForVisible().tap();
        	search.searchFieldBtn().waitForVisible();
        	search.allSearchResultsBtn().waitForNotPresentOrVisible();
        }
    }
}