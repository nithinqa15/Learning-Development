package com.viacom.test.vimn.uitests.tests.homescreen;

import com.viacom.test.vimn.common.filters.*;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ToClipsAndBackTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    Home home;
    Clips clips;
    Series series;
    SelectSeasonChain selectSeasonChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;

    //Declare data
    String clipTitle;
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ToClipsAndBackTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        selectSeasonChain = new SelectSeasonChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResult propertyResult = homePropertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();

            LongformResult episodeResult = propertyResult
                    .getLongformFilter()
                    .withPublicEpisodesOnly()
                    .episodesFilter()
                    .getFirstEpisode();
            episodeTitle = episodeResult.getEpisodeTitle();
            episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();

            ShortformResult clipResult = propertyResult.getShortformFilter().clipsFilter().getFirstClip();
            clipTitle = clipResult.getClipTitle();
        }

    }

    @TestCaseId("51713")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void ToClipsAndBackAndroidTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.backBtn().waitForVisible().tap();

            home.seriesThumbBtn(seriesTitle).waitForVisible();
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.backBtn().waitForVisible().tap();

            home.seriesThumbBtn(seriesTitle).waitForVisible();
            home.enterClipsByTappingOnBackground();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.backBtn().waitForVisible().tap();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.switchToFullEpisodes();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.switchToClips();
            clips.clipTitle(clipTitle).waitForVisible();
            series.switchToFullEpisodes();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.seriesTtl(seriesTitle).goBack();

            home.seriesThumbBtn(seriesTitle).waitForVisible();
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

    @TestCaseId("51713")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.BROKEN, Config.GroupProps.HOME_SCREEN})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void ToClipsAndBackiOSTest() {

        if (shortFormEnabled) {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        } else {
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForViewLoad().waitForVisible();

            clips.clipCloseBtn().waitForVisible().tap();
            home.seriesThumbBtn(seriesTitle).waitForVisible();
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForViewLoad().waitForVisible();

            clips.clipCloseBtn().waitForVisible().tap();
            home.enterClipsByTap(seriesTitle);
            clips.clipTitle(clipTitle).waitForViewLoad().waitForVisible();

            clips.clipCloseBtn().waitForVisible().tap();
            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForVisible();
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForVisible();
            home.fullEpisodesBtn().waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForVisible();
            Logger.logMessage(DriverManager.getAppiumDriver().getPageSource());

            series.seriesCloseBtn().waitForVisible().tap();
            home.seriesThumbBtn(seriesTitle).waitForVisible();
        }
    }
}