package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.*;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Series;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ClipWithoutRelatedEpisodeTest extends BaseTest {

    //Declare Page Objects and Methods
    AutoPlayChain autoPlayChain;
    SplashChain splashChain;
    Home home;
    Series series;
    Clips clips;
    ChromecastChain chromecastChain;
    HomePropertyFilter homePropertyFilter;

    //Declara Data
    String seriesTitle;
    String clipTitle;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ClipWithoutRelatedEpisodeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        autoPlayChain = new AutoPlayChain();
        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        clips = new Clips();
        chromecastChain = new ChromecastChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResults propertyResults = homePropertyFilter.withClips().propertyFilter();
            for (PropertyResult propertyResult : propertyResults) {
                int index = propertyResults.indexOf(propertyResult);
                if (propertyResult.getShortformFilter().clipsFilter().get(index).hasNoRelatedEpisodes()) {
                    seriesTitle = propertyResult.getPropertyTitle();
                    numberOfSwipes = propertyResult.getNumOfSwipes();
                    ShortformResult clipResult = propertyResult
                            .getShortformFilter()
                            .withNoRelatedEpisodes()
                            .clipsFilter()
                            .getFirstClip();
                    clipTitle = clipResult.getClipTitle();
                    if (clipTitle != null && seriesTitle != null) {
                        break;
                    }
                }
            }
        }
    }

    @TestCaseId("")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void clipWithoutRelatedEpisodeAndroidTest() {

        if (shortFormEnabled) {
            if (clipTitle != null) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                autoPlayChain.enableAutoPlay();

                home.enterSeriesView(seriesTitle, numberOfSwipes);
                series.switchToClips();
                clips.clipTitle(clipTitle).waitForClipsPlaylistLoad().waitForScrolledTo().waitForVisible();
                clips.watchEpisodeBtn(clipTitle).waitForNotPresentOrVisible();
            } else {
                throw new SkipException("Skipping Test: There is no clip without related episode on" + TestRun.getAppName());
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }
    
    @TestCaseId("")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.BROKEN, Config.GroupProps.CLIPS}) //Need to check feed
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void clipWithoutRelatedEpisodeiOSTest() {

        if (shortFormEnabled) {
            if (clipTitle != null) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                autoPlayChain.enableAutoPlay();

                home.enterSeriesView(seriesTitle, numberOfSwipes);
                series.switchToClips();
                clips.clipTitle(clipTitle).waitForClipsPlaylistLoad().waitForScrolledTo().waitForVisible();
                clips.watchEpisodeBtn(clipTitle).waitForNotPresentOrVisible();
            } else {
                throw new SkipException("Skipping Test: There is no clip without related episode on" + TestRun.getAppName());
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

}