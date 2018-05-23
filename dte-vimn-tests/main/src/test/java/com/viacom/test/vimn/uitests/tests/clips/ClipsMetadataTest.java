package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.ShortformResult;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ClipsMetadataTest extends BaseTest {

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
    String clipDuration;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ClipsMetadataTest(String runParams){
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

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResult propertyResult = homePropertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();

            ShortformResult clipResult = propertyResult.getShortformFilter().clipsFilter().getFirstClip();
            clipTitle = clipResult.getClipTitle();
            clipDuration = clipResult.getClipDurationInTimeCode();
        }
    }

    // TODO - ADD add gif, meme checks to this test - http://enttester-app-1.mtvn.ad.viacom.com/et5/#/script/edit/8f3eb1bf-70c9-4462-94de-a5ec00933808
    @TestCaseId("10922")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void clipsMetadataAndroidTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.verifyClipTitleIsVisible(clipTitle);
            clips.clipDuration(clipTitle).isVisible();
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

    @TestCaseId("10922")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void clipsMetadataiOSTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            autoPlayChain.enableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.verifyClipTitleIsVisible(clipTitle);
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }
}
