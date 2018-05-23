package com.viacom.test.vimn.uitests.tests.shortformonly;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsOnlyException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class WatchExtrasOnHomeTest extends BaseTest{

    //Declare page objects and actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Series series;
    VideoPlayer videoPlayer;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String clipsOnlySeriesTitle;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public WatchExtrasOnHomeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

    	shortFormEnabled = MainConfig.isShortFormEnabled();
		if (shortFormEnabled) {
	            PropertyResult propertyResult = propertyFilter.withClips().withClipsOnly().propertyFilter().getFirstProperty();

	            clipsOnlySeriesTitle = propertyResult.getPropertyTitle();
	            numberOfSwipes = propertyResult.getNumOfSwipes();
	    }

    }

    @TestCaseId("34877")
    @Features(Config.GroupProps.SHORTFORM_ONLY)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.SHORTFORM_ONLY })
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void watchExtrasOnHomeAndroidTest() {

        if (shortFormEnabled && clipsOnlySeriesTitle != null) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();

            home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible();
            home.fullEpisodesBtn().waitForNotVisible();

        } else if (!shortFormEnabled) {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        } else {
            String message = "There are no clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale() +
                " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoSeriesWithClipsOnlyException(message);
        }
    }

    @TestCaseId("34877")
    @Features(Config.GroupProps.SHORTFORM_ONLY)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.SHORTFORM_ONLY  })
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void WatchExtrasOnHomeiOSTest () {

        if (shortFormEnabled && clipsOnlySeriesTitle != null) {

            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            autoPlayChain.enableAutoPlayClip();

            home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible();
            home.fullEpisodesBtn().waitForNotPresentOrVisible();

        } else if (!shortFormEnabled) {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        } else {
            String message = "There are no clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale() +
                " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoSeriesWithClipsOnlyException(message);
        }
    }
}
