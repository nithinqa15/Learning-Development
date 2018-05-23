package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class FightContentSupportedRibbonsTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String firstFightTitle;
    Integer numberOfSwips;
    Boolean hasExtras;
    Boolean hasPlaylist;
    Boolean ribbon;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightContentSupportedRibbonsTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	home = new Home();
    	series = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        
        PropertyResults fightResults = propertyFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on homescreen : " +  firstFightTitle);
        hasExtras = firstFight.hasClips();
        Logger.logConsoleMessage("First Fight title has extras : " + hasExtras);
        hasPlaylist = firstFight.hasPlaylist();
        Logger.logConsoleMessage("First Fight title has event : " + hasPlaylist);
        numberOfSwips = firstFight.getNumOfSwipes();
        ribbon = firstFight.hasRibbonObject();
        
    }
    
    @TestCaseId("48077")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void fightContentSupportedRibbonsAndroidTest() {

		if (!ribbon) {
			String message = "There is a no ribbon object present for fight content on:" + TestRun.getAppName() + " "
					+ TestRun.getLocale() + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL()
					+ " so test failed";
			Logger.logMessage(message);
			throw new NoEventContentException(message);
		} else {
			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();

			home.flickRightToSeriesThumb(firstFightTitle, numberOfSwips);

			if (hasExtras && hasPlaylist) { // Condition check for Event & Extras both present
				home.specialBtn().waitForVisible();
				home.watchExtrasBtn().waitForVisible();
				home.newSpecialBtn().waitForVisible();
			} else if (hasExtras && !hasPlaylist) { // Condition check for Extras only
				home.watchExtrasBtn().waitForVisible();
				home.newSpecialBtn().waitForVisible();
			} else { // Event only
				home.specialBtn().waitForVisible();
				home.newSpecialBtn().waitForVisible();
			}
		}
	}
    
    @TestCaseId("48077")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void fightContentSupportedRibbonsiOSTest() {
        
    	if (!ribbon) {
    		String message = "There is a no ribbon object present for fight content on:" + TestRun.getAppName() + " " + TestRun.getLocale()
				+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so test failed";
    			Logger.logMessage(message);
    			throw new NoEventContentException(message);
    	} else {
        	splashChain.splashAtRest();
        	chromecastChain.dismissChromecast();
        	alertschain.dismissAlerts();
        	
        	home.flickRightToSeriesThumb(firstFightTitle, numberOfSwips);
        	
            if (hasExtras && hasPlaylist) { //Condition check for Event & Extras both present
            	home.specialBtn().waitForVisible();
            	home.watchExtrasBtn().waitForVisible();
            	home.newSpecialBtn().waitForVisible();
            } else if (hasExtras && !hasPlaylist){ //Condition check for Extras only
            	home.watchExtrasBtn().waitForVisible();
            	home.newSpecialBtn().waitForVisible();
            } else { // Event only
            	home.specialBtn().waitForVisible();
            	home.newSpecialBtn().waitForVisible();
            }
    	}
    }
}
