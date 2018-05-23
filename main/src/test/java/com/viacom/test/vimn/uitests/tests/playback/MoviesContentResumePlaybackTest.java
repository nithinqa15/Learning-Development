package com.viacom.test.vimn.uitests.tests.playback;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoMovieContentException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class MoviesContentResumePlaybackTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	AllShows allShows;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    AutoPlayChain autoPlayChain;
    HomePropertyFilter propertyFilter;
    VideoPlayer videoPlayer;
    LoginChain loginChain;
    
    //Declare data
    String firstMovieTitle;
    Integer numberOfSwips;
    Boolean hasTrailer;
    Boolean hasTrailerOnly;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MoviesContentResumePlaybackTest(String runParams) {
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
        autoPlayChain = new AutoPlayChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        videoPlayer = new VideoPlayer();
        loginChain = new LoginChain();
        
        PropertyResults movieResults = propertyFilter.withMovie().propertyFilter();
        PropertyResult firstMovie = movieResults.getFirstProperty();
        firstMovieTitle = firstMovie.getPropertyTitle();
        Logger.logConsoleMessage("First Movie title on AllShowsScreen : " + firstMovieTitle);
        hasTrailer = firstMovie.hasTrailer();
        hasTrailerOnly = firstMovie.hasTrailersOnly();
        Logger.logConsoleMessage("First Movie title has trailer : " + hasTrailer);
        numberOfSwips = firstMovie.getNumOfSwipes();
        
    }
    
    @TestCaseId("47609")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK, GroupProps.MOVIES_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void moviesContentPlaybackAndroidTest() {
        
    	//TO-DO
        
    }
    
    @TestCaseId("47609")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK, GroupProps.MOVIES_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void moviesContentPlaybackiOSTest() {
        
    	if (firstMovieTitle != null && !hasTrailerOnly) {
    		
    		ProxyUtils.disableAds();
    		
        	splashChain.splashAtRest();
        	chromecastChain.dismissChromecast();
        	alertschain.dismissAlerts();
        	autoPlayChain.enableAutoPlay();
        	loginChain.loginIfNot();
            
            home.flickRightToSeriesThumb(firstMovieTitle, numberOfSwips);
            home.movieBtn().waitForVisible().tap();
            
            if (series.movieBtn().isVisible()) {
            	series.movieBtn().waitForVisible().tap();
            }
            
            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            videoPlayer.playerCtr().waitForPresent().tapCenter();
            series.backBtn().waitForVisible().tap();
            
            home.progressBar(firstMovieTitle).waitForVisible();
            
            home.movieBtn().waitForVisible().tap();
            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.playFromBeginningBtn().waitForNotPresentOrVisible();
            	
    	} else {
			String message = "There is no movie content or has movie content with trailer only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoMovieContentException(message);
    	}
    }
}