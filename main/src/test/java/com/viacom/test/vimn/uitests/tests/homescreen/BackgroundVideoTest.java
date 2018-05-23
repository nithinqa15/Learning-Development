package com.viacom.test.vimn.uitests.tests.homescreen;

import com.viacom.test.vimn.common.exceptions.NoSeriesWithBackgroundVideoException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class BackgroundVideoTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Series series;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;

    //Declare data
    String seriesTitle;
    Boolean backgroundServiceVideoEnabled = false;
    Boolean hasSeriesWithBackgroundVideo = false;
    Integer numberOfSwipes;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public BackgroundVideoTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        alertschain = new AlertsChain();
        chromecastChain = new ChromecastChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        backgroundServiceVideoEnabled = MainConfig.isBackgroundVideoServiceEnabled();
        if (backgroundServiceVideoEnabled) {
            PropertyResult propertyResult = homePropertyFilter.withBackgroundVideo().propertyFilter().getFirstProperty();
            hasSeriesWithBackgroundVideo = propertyResult != null;
            if (hasSeriesWithBackgroundVideo) {
                seriesTitle = propertyResult.getPropertyTitle();
                numberOfSwipes = propertyResult.getNumOfSwipes();
            }
        }
    }

    @TestCaseId("")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void backgroundVideoAndroidTest() {

        if (backgroundServiceVideoEnabled) {
            if (hasSeriesWithBackgroundVideo) {

                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
                autoPlayChain.enableAutoPlayClip();
                videoPlayer.verifyVideoIsPlayingByScreenshots(5);
                autoPlayChain.disableAutoPlayClip();
                videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
            } else {
                String message = "No series with background video found on feed.";
                Logger.logMessage(message);
                throw new NoSeriesWithBackgroundVideoException(message);
            }
        } else {
            String message = "Background Video is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new BackgroundVideoNotEnabledException(message);
        }
    }

    @TestCaseId("")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN  })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void backgroundVideoiOSTest() {

        if (!backgroundServiceVideoEnabled) {
            String message = "Background Video is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new BackgroundVideoNotEnabledException(message);
        } else {
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            
            home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
            autoPlayChain.enableAutoPlayClip();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            autoPlayChain.disableAutoPlayClip();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        }

    }
}
