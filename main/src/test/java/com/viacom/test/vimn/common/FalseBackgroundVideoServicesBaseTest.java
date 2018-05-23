package com.viacom.test.vimn.common;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class FalseBackgroundVideoServicesBaseTest extends BaseTest {

    protected static SplashChain splashChain;
    protected static ChromecastChain chromecastChain;
    protected static AlertsChain alertsChain;
    protected static Home home;
    protected static Clips clips;
    protected static AutoPlayChain autoPlayChain;
    protected static HomePropertyFilter homePropertyFilter;
    protected static VideoPlayer videoPlayer;
    protected static Series series;
    protected static LongformResult firstEpisode;

    protected static String seriesTitle;
    protected static String episodeTitle;
    protected static String clipTitle;
    protected static Integer numberOfSwipes;
    protected static Boolean backgroundServiceVideoNotEnabled = false;
    protected static Boolean shortFormEnabled = false;
    protected static String backgroundEnabledExceptionMessage;

    protected void initializePageObjects() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        home = new Home();
        clips = new Clips();
        autoPlayChain = new AutoPlayChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        videoPlayer = new VideoPlayer();
        series = new Series();

    }

    protected void falseBackgroundVideoServicesSetupTest() {

        initializePageObjects();

        backgroundServiceVideoNotEnabled = MainConfig.isBackgroundVideoServiceNotEnabled();
        shortFormEnabled = MainConfig.isShortFormEnabled();

        PropertyResult propertyResult = homePropertyFilter
                .withEpisodes()
                .withClips()
                .withPublicEpisodes()
                .withBackgroundVideo()
                .propertyFilter()
                .getFirstProperty();

        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        
        episodeTitle = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode().getEpisodeTitle();

        clipTitle = propertyResult
                .getClips()
                .getFirstClip()
                .getClipTitle();

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

        backgroundEnabledExceptionMessage = "Background Video is enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                " so skipping test";

    }
}
