package com.viacom.test.vimn.common;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResults;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.core.util.Logger;

public class AdsBaseTest extends BaseTest {

    //Declare page objects/actions
    protected static HomePropertyFilter homePropertyFilter;
    protected static SplashChain splashChain;
    protected static AutoPlayChain autoPlayChain;
    protected static LoginChain loginChain;
    protected static ChromecastChain chromecastChain;
    protected static Home home;
    protected static VideoPlayer videoPlayer;
    protected static Ads ads;
    protected static Series series;

    //Declare data
    protected String seriesTitle;
    protected Integer numberOfSwipes;
    protected String firstPrivateEpisode;
    protected String nextPrivateEpisode;
    protected String episodeTitle;
    protected static LongformResult firstEpisode;

    protected void initializePageObjects() {
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        loginChain = new LoginChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        videoPlayer = new VideoPlayer();
        ads = new Ads();
        series = new Series();
    }

    protected void adsSetupTest() {
        initializePageObjects();
        try {
            PropertyResult propertyResult = homePropertyFilter
            		.withEpisodes()
                    .withPublicEpisodes()
                    .withPrivateEpisodes()
                    .propertyFilter()
                    .getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            Logger.logMessage("Series Title: " + seriesTitle);
            numberOfSwipes = propertyResult.getNumOfSwipes();
            Logger.logMessage("Number of Swipes " + numberOfSwipes);

            LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
            episodeTitle = episodeResult.getEpisodeTitle();
            Logger.logMessage("Episode Title " + episodeTitle);

            LongformResults longformResults = propertyResult
                    .getLongformFilter()
                    .withPrivateEpisodesOnly()
                    .episodesFilter();
            firstPrivateEpisode = longformResults.getFirstEpisode().getEpisodeTitle();
            Logger.logMessage("First Private Episode Title " + firstPrivateEpisode);
            nextPrivateEpisode = longformResults.get(1).getEpisodeTitle();
            Logger.logMessage("Second Private Episode Title " + nextPrivateEpisode);

            firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
        } catch (Exception e) {
        	Logger.logConsoleMessage("<-------- Meta data collection Error for Ads Test " + e + " ------>");
        }
    }
}
