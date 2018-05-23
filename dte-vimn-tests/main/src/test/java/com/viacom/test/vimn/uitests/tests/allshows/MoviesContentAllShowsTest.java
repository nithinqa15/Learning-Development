package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoMovieContentException;
import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class MoviesContentAllShowsTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	AllShows allShows;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    AllShowsPropertyFilter propertyFilter;
    LoginChain loginChain;
    
    //Declare data
    String firstMovieTitle;
    Integer numberOfSwips;
    Boolean hasTrailer;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MoviesContentAllShowsTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	home = new Home();
    	series = new Series();
    	allShows = new AllShows();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        loginChain = new LoginChain();
        propertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
        
        PropertyResults movieResults = propertyFilter.withMovie().propertyFilter();
        PropertyResult firstMovie = movieResults.getFirstProperty();
        firstMovieTitle = firstMovie.getPropertyTitle();
        Logger.logConsoleMessage("First Movie title on AllShowsScreen : " + firstMovieTitle);
        hasTrailer = firstMovie.hasTrailer();
        Logger.logConsoleMessage("First Movie title has trailer : " + hasTrailer);
        numberOfSwips = firstMovie.getNumOfSwipes();
        
    }
    
    @TestCaseId("47600")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS, GroupProps.MOVIES_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
	public void moviesContentAllShowsAndroidTest() {

		if (firstMovieTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			loginChain.loginIfNot();

			allShows.allShowsBtn().waitForVisible().tap();
			allShows.scrollDownToSeriesTile(firstMovieTitle);
			allShows.seriesTile(firstMovieTitle).waitForVisible().tap();

			if (hasTrailer) {
				series.movieBtn().waitForVisible();
				series.trailerBtn().waitForVisible();
			} else {
				series.movieBtn().waitForNotPresentOrVisible();
				series.trailerBtn().waitForNotPresentOrVisible();
			}
		} else {
			String message = "There are no movie content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in allShowsfeed url : " + FeedFactory.getAllShowsFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoMovieContentException(message);
		}
	}
    
    @TestCaseId("47600")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS, GroupProps.MOVIES_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void moviesContentAllShowsiOSTest() {
        
    	if (firstMovieTitle != null) {
    		
        	splashChain.splashAtRest();
        	chromecastChain.dismissChromecast();
        	alertschain.dismissAlerts();
            
        	allShows.allShowsBtn().waitForVisible().tap();
        	allShows.scrollDownToSeriesTile(firstMovieTitle);
        	allShows.seriesTile(firstMovieTitle).waitForVisible().tap();
            
            if (hasTrailer) {
            	series.movieBtn().waitForVisible();
            	series.trailerBtn().waitForVisible();
            } else {
            	series.movieBtn().waitForNotPresentOrVisible();
            	series.trailerBtn().waitForNotPresentOrVisible();
            }
    	} else {
			String message = "There are no movie content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in allShowsfeed url : " + FeedFactory.getAllShowsFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoMovieContentException(message);
    	}
    }
}
