package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
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

public class MoviesContentNoSupportedRibbonsTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String firstMovieTitle;
    Integer numberOfSwips;
    Boolean hasTrailer;
    Boolean ribbon;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MoviesContentNoSupportedRibbonsTest(String runParams) {
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
        
        PropertyResults movieResults = propertyFilter.withMovie().propertyFilter();
        PropertyResult firstMovie = movieResults.getFirstProperty();
        firstMovieTitle = firstMovie.getPropertyTitle();
        Logger.logConsoleMessage("First Movie title on homescreen : " + firstMovieTitle);
        hasTrailer = firstMovie.hasTrailer();
        Logger.logConsoleMessage("First Movie title has trailer : " + hasTrailer);
        numberOfSwips = firstMovie.getNumOfSwipes();
        ribbon = firstMovie.hasRibbonObject();
        
    }
    
    @TestCaseId("47601")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.MOVIES_CONTENT })
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void moviesContentNoSupportedRibbonsAndroidTest() {

		if (ribbon) {
			String message = "There is a ribbon object present for movie content on:" + TestRun.getAppName() + " "
					+ TestRun.getLocale() + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL()
					+ " so test failed";
			Logger.logMessage(message);
			Assert.assertTrue(false, message);

		} else {
			splashChain.splashAtRest();
			alertschain.dismissAlerts();

			home.flickRightToSeriesThumb(firstMovieTitle, numberOfSwips);

			if (hasTrailer) {
				home.trailerBtn().waitForVisible();
				home.movieBtn().waitForVisible();
				home.newMovieBtn().waitForNotPresentOrVisible();
			} else {
				home.movieBtn().waitForVisible();
				home.newMovieBtn().waitForNotPresentOrVisible();
			}
		}
	}
    
    @TestCaseId("47601")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN , GroupProps.MOVIES_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void moviesContentNoSupportedRibbonsiOSTest() {
        
    	if (ribbon) {
    		String message = "There is a ribbon object present for movie content on:" + TestRun.getAppName() + " " + TestRun.getLocale()
				+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so test failed";
    			Logger.logMessage(message);
    		Assert.assertTrue(false, message);
    		
    	} else {
        	splashChain.splashAtRest();
        	chromecastChain.dismissChromecast();
        	alertschain.dismissAlerts();
        	
        	home.flickRightToSeriesThumb(firstMovieTitle, numberOfSwips);
        	
            if (hasTrailer) {
            	home.trailerBtn().waitForVisible();
            	home.movieBtn().waitForVisible();
            	home.newMovieBtn().waitForNotPresentOrVisible();
            } else {
            	home.movieBtn().waitForVisible();
            	home.newMovieBtn().waitForNotPresentOrVisible();
            }
    	}
    }
}
