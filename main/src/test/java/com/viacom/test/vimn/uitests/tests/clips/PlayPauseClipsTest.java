package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.*;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class PlayPauseClipsTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Clips clips;
    Series series;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    HomePropertyFilter homePropertyFilter;
    AlertsChain alertsChain;

    //Declare data
    String clipTitle;
    String seriesTitle;
    Integer numberOfSwipes;
    Boolean isShortFormEnabled = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PlayPauseClipsTest(String runParams){
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        videoPlayer = new VideoPlayer();
        chromecastChain = new ChromecastChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertsChain = new AlertsChain();

        isShortFormEnabled = MainConfig.isShortFormEnabled();
        if (isShortFormEnabled) {
            PropertyResult propertyResult = homePropertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();

            clipTitle = propertyResult.getClips().getFirstClip().getClipTitle();
        }
    }

    // test also includes 4ce4f6ca-8d32-4194-90ef-a6cd0154ce63
    @TestCaseId("10919")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void playPauseClipsAndroidTest() {

        if (isShortFormEnabled) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(clipTitle).waitForClipsPlaylistLoad().waitForPosterSpinnerNotVisible().waitForVisible();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

            videoPlayer.largePlayBtn().waitForVisible().tap();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

            videoPlayer.playerCtr().waitForPresent().tapCenter();
            videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotPresent();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }

    @TestCaseId("10919")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void playPauseClipsiOSTest() {

        if (isShortFormEnabled) {
            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(clipTitle).waitForPlayerLoad().waitForVisible();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

            videoPlayer.largePlayBtn().waitForVisible().tap();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }

}
