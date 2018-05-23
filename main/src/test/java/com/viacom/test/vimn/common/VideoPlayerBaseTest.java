package com.viacom.test.vimn.common;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.actionchains.*;
import com.viacom.test.vimn.uitests.pageobjects.*;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class VideoPlayerBaseTest extends BaseTest {

    //Declare page objects/actions
    protected static SplashChain splashChain;
    protected static LoginChain loginChain;
    protected static AutoPlayChain autoPlayChain;
    protected static Series series;
    protected static Home home;
    protected static VideoPlayer videoPlayer;
    protected static ChromecastChain chromecastChain;
    protected static ProgressBar progressBar;
    protected static AlertsChain alertsChain;
    protected static HomePropertyFilter homePropertyFilter;
    protected static Ads ads;

    //Declare data
    protected String seriesTitle;
    protected String episodeTitle;
    protected Integer numberOfSwipes;
    protected static String VALUE = Config.AttributeProps.VALUE;
    protected static String TEXT = Config.AttributeProps.TEXT;
    protected static LongformResult firstEpisode;

    protected void initializePageObjects() {
        splashChain = new SplashChain();
        loginChain = new LoginChain();
        autoPlayChain = new AutoPlayChain();
        series = new Series();
        home = new Home();
        progressBar = new ProgressBar();
        videoPlayer = new VideoPlayer();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        ads = new Ads();
    }

    protected void videoPlayerSetupTest() {
    	
        initializePageObjects();
        PropertyResult propertyResult = homePropertyFilter
        		    .withEpisodes()
                .withPublicEpisodes()
                .propertyFilter()
                .getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        
        LongformResult episodeResult = propertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
    }
}
