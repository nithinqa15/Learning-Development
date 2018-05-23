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
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.List;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

/**
 * Created by fioreg on 28/11/16.
 *
 * The goal of this test is to validate if a user can scroll all the way down until
 * the last short clip of a series and then scroll up back to the first video.
 *
 * Requirements:
 * - Name of first series from PromoFeed
 * - SeriesID of first series from PromoFeed
 * - A list of short form titles generated directly from the respective series feed
 * - The first short video title in the feed
 *
 * User Story:
 * I launch the app
 * And the splash screen is presented for 5 seconds
 * When splash screen is gone
 * I'm presented with home view
 * I flick the series carousel until I find the desired series
 * And I Tap it
 * And I'm directed to Full Episodes tab in respective Series View
 * I tap on the Extras tab
 * And I'm presented with respective series short form videos
 * I verify if the first video title matches with the first title from my list
 * While I still have titles in my list
 * I scroll down the clips reel until I find the next video title in the list
 * Once I finish the clips titles list
 * I scroll up the clips reel until I find the first title from my list
 *
 */

public class ScrollUpAndDownClipsTest extends BaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ScrollUpAndDownClipsTest(String runParams) {super.setRunParams(runParams);}

    //Declare page objects and actions
    SplashChain splashChain;
    Home home;
    Series series;
    Clips clips;
    AutoPlayChain autoPlayChain;
    ChromecastChain chromecastChain;
    HomePropertyFilter homePropertyFilter;
    AlertsChain alertsChain;

    //Declare data
    String seriesTitle;
    List<String> clipsTitles;
    String firstClipTitle;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        clips = new Clips();
        autoPlayChain = new AutoPlayChain();
        chromecastChain = new ChromecastChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertsChain = new AlertsChain();

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResult propertyResult = homePropertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();

            ShortformResults clipResults = propertyResult.getClips();
            clipsTitles = clipResults.getClipTitles();
            clipsTitles.subList(3, clipsTitles.size()).clear();
            firstClipTitle = clipsTitles.get(0);
        }

    }

    // Test case also covers Clips Reel Ordering 9deae143-9e11-44b2-9523-a6c500fd2175
    @TestCaseId("10921")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void scrollUpAndDownClipsAndroidTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(firstClipTitle).waitForClipsPlaylistLoad().isVisible();
            clipsTitles.forEach(clipTitle -> clips.verifyClipTitleIsVisible(clipTitle));
            clips.clipTitle(firstClipTitle).waitForScrolledUpToOnClipsView(clipsTitles.size());
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }
    
    // Test case also covers Clips Reel Ordering 9deae143-9e11-44b2-9523-a6c500fd2175
    @TestCaseId("10921")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void scrollUpAndDownClipsiOSest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(firstClipTitle).isVisible();
            clipsTitles.forEach(clipTitle -> clips.verifyClipTitleIsVisible(clipTitle));
            clips.clipTitle(firstClipTitle).waitForScrolledUpToOnClipsView(clipsTitles.size());
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

}
