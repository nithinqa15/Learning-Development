package com.viacom.test.vimn.common;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class TrueBackgroundVideoServicesBaseTest extends BaseTest {

    protected static SplashChain splashChain;
    protected static ChromecastChain chromecastChain;
    protected static AlertsChain alertsChain;
    protected static Home home;
    protected static Clips clips;
    protected static AutoPlayChain autoPlayChain;
    protected static HomePropertyFilter homePropertyFilter;
    protected static VideoPlayer videoPlayer;
    protected static Series series;
    protected static String seriesTitle;
    protected static String clipTitle;
    protected static Integer numberOfSwipes;
    protected static Boolean backgroundServiceVideoEnabled = false;
    protected static String backgroundServiceVideoDisabledExceptionMessage;
    protected static String episodeTitle;
    protected static LongformResult firstEpisode;

    protected void initializePageObjects() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        autoPlayChain = new AutoPlayChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        videoPlayer = new VideoPlayer();

    }

    protected void trueBackgroundVideoServicesSetupTest() {

        initializePageObjects();

        backgroundServiceVideoEnabled = MainConfig.isBackgroundVideoServiceEnabled();

        PropertyResult propertyResult = homePropertyFilter
                .withClips()
                .withPublicEpisodes()
                .withBackgroundVideo()
                .propertyFilter()
                .getFirstProperty();

        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        clipTitle = propertyResult
                .getClips()
                .getFirstClip()
                .getClipTitle();

        LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
        episodeTitle = episodeResult.getEpisodeTitle();

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

        backgroundServiceVideoDisabledExceptionMessage = "Background Video is not enabled " + TestRun.getAppName() + " " + TestRun.getLocale()
                + " so skipping test";

    }
}
