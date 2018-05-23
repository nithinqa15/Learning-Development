package com.viacom.test.vimn.uitests.tests.ads;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MOATEnabledPausePlayTest extends BaseTest {
    //Declare page objects and actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    Home home;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;
    Ads ads;
    Series series;

    //Declare data
    String seriesTitle;
    Integer numberOfSwipes;
    Boolean moatEnabled = false;
    String episodeTitle;
    LongformResult firstEpisode;


    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MOATEnabledPausePlayTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        videoPlayer = new VideoPlayer();
        ads = new Ads();
        series = new Series();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        moatEnabled = MainConfig.isMoatEnabled();
        if (moatEnabled) {
            PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();

            LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
            episodeTitle = episodeResult.getEpisodeTitle();

            firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

        }
    }

    @TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, 
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.BET_DOMESTIC_APP,ParamProps.PARAMOUNT_APP})
    public void moatEnabledPausePlayAndroidTest() {
        if (moatEnabled && seriesTitle != null) {

            //Ensure Ads Play
            ProxyUtils.rewriteTSLA(1);
            ProxyUtils.disableTve();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);

            videoPlayer.playFromBeginningBtn().waitForNotPresent();
            videoPlayer.playerCtr().waitForPresent().tapCenter();
            videoPlayer.scrubberBar().waitForNotPresent();
            videoPlayer.playerCtr().waitForPresent().tapCenter();

            ads.durationTimer().waitForNotPresent();
            ads.blackAdOverlay().waitForNotPresent();
            ads.pauseAdsBtn().waitForVisible().tap();

            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
            ads.playAdsBtn().waitForVisible().tap();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "MOAT is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() + "in Main Config so skipping test";
            Logger.logMessage(message);
            throw new SkipException(message);
        }
    }
}
