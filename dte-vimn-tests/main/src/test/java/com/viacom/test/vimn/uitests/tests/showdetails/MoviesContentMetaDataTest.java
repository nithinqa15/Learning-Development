package com.viacom.test.vimn.uitests.tests.showdetails;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoMovieContentException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MoviesContentMetaDataTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    VideoPlayer videoPlayer;
    LoginChain loginChain;
    
    //Declare data
    String firstMovieTitle;
    String duration;
    String movieMetaData;
    Integer numberOfSwips;
    Boolean hasTrailer;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MoviesContentMetaDataTest(String runParams) {
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
        videoPlayer = new VideoPlayer();
        loginChain = new LoginChain();
        
        PropertyResults movieResults = propertyFilter.withMovie().propertyFilter();
        PropertyResult firstMovie = movieResults.getFirstProperty();
        firstMovieTitle = firstMovie.getPropertyTitle();
        Logger.logConsoleMessage("First Movie title on homescreen : " + firstMovieTitle);
        hasTrailer = firstMovie.hasTrailer();
        Logger.logConsoleMessage("First Movie title has trailer : " + hasTrailer);
        numberOfSwips = firstMovie.getNumOfSwipes();
        Logger.logConsoleMessage(firstMovieTitle + " Content Rating : " + firstMovie.getTVPGContentRating());
        Logger.logConsoleMessage(firstMovieTitle + " Duration : " + firstMovie.getDuration());
        Logger.logConsoleMessage(firstMovieTitle + " AirDate : " + firstMovie.getOriginalAirDateString());
        duration = firstMovie.getDuration().split(":")[0] + "hr " + firstMovie.getDuration().split(":")[1] + "min";
        movieMetaData = firstMovie.getOriginalAirDateString().split("-")[0] + " | "+ duration +  " | "+firstMovie.getTVPGContentRating();
        Logger.logConsoleMessage(firstMovieTitle + " MetaData : " + movieMetaData);
        
    }
    
    // BROKEN until https://jira.mtvi.com/browse/VAA-6405 gets fixed
    @TestCaseId("47608")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.MOVIES_CONTENT })
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void moviesContentMetadataAndroidTest() {   

		if (firstMovieTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			loginChain.loginIfNot();

			home.flickRightToSeriesThumb(firstMovieTitle, numberOfSwips);

			if (hasTrailer) {
				home.trailerBtn().waitForVisible();
				home.movieBtn().waitForVisible();
				home.movieBtn().waitForVisible().tap();
				series.movieBtn().waitForVisible();
				series.trailerBtn().waitForVisible();
				videoPlayer.progressSpinner().waitForNotPresent();
				videoPlayer.progressSpinner().waitForPlayerLoad();
				series.movieMetaDataText(movieMetaData).waitForVisible();
			} else {
				home.movieBtn().waitForVisible();
				home.movieBtn().waitForVisible().tap();
				series.movieBtn().waitForNotPresentOrVisible();
				series.trailerBtn().waitForNotPresentOrVisible();
				videoPlayer.progressSpinner().waitForNotPresent();
				videoPlayer.progressSpinner().waitForPlayerLoad();
				series.movieMetaDataText(movieMetaData).waitForVisible();
			}
		} else {
			String message = "There are no movie content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoMovieContentException(message);
		}
	}
    
    @TestCaseId("47608")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.MOVIES_CONTENT })
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void moviesContentMetaDataiOSTest() {

	if (firstMovieTitle != null) {
		
    	splashChain.splashAtRest();
    	chromecastChain.dismissChromecast();
    	alertschain.dismissAlerts();
    	loginChain.loginIfNot();
        
        home.flickRightToSeriesThumb(firstMovieTitle, numberOfSwips);
        
        if (hasTrailer) {
        	home.trailerBtn().waitForVisible();
        	home.movieBtn().waitForVisible();
        	home.movieBtn().waitForVisible().tap();
        	series.movieBtn().waitForVisible();
        	series.trailerBtn().waitForVisible();
        	videoPlayer.progressSpinner().waitForNotPresent();
        	videoPlayer.progressSpinner().waitForPlayerLoad();
        	series.movieMetaDataText(movieMetaData).waitForVisible();
        } else {
        	home.movieBtn().waitForVisible();
        	home.movieBtn().waitForVisible().tap();
        	series.movieBtn().waitForNotPresentOrVisible();
        	series.trailerBtn().waitForNotPresentOrVisible();
         	videoPlayer.progressSpinner().waitForNotPresent();
        	videoPlayer.progressSpinner().waitForPlayerLoad();
        	series.movieMetaDataText(movieMetaData).waitForVisible();
        }
	} else {
		String message = "There are no movie content on: " + TestRun.getAppName() + " " + TestRun.getLocale()
				+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
		Logger.logMessage(message);
		throw new NoMovieContentException(message);
	}
  }
}
