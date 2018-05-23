package com.viacom.test.vimn.uitests.tests.playback;

import com.viacom.test.vimn.uitests.pageobjects.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

/**
 * Created by chengh on 1/24/18.
 */
public class FightsContentResumePlaybackTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    LoginChain loginChain;
    AutoPlayChain autoPlayChain;
    HomePropertyFilter fightsFilter;
    Home home;
    Series series;
    VideoPlayer videoPlayer;

    //Declare data
    String firstFightTitle;
    Integer numberOfSwipes;
    Boolean hasExtras;
    Boolean hasExtrasOnly;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightsContentResumePlaybackTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        loginChain = new LoginChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        fightsFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults fightsResults = fightsFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightsResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on HomeScreen : " + firstFightTitle);
        hasExtras = firstFight.hasClips();
        hasExtrasOnly = firstFight.hasClipsOnly();
        Logger.logConsoleMessage("First Fight title has extras : " + hasExtras);
        numberOfSwipes = firstFight.getNumOfSwipes();

    }

    @TestCaseId("67029")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK, GroupProps.FIGHTS_CONTENT })
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void fightsContentPlaybackAndroidTest() {

        if(firstFightTitle != null && !hasExtrasOnly) {

            ProxyUtils.disableAds();
            ProxyUtils.disableTve();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlay();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);
            home.specialBtn().waitForVisible().tap();

            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            videoPlayer.playerCtr().waitForPresent().tapCenter();
            series.backBtn().waitForVisible().tap();

            home.progressBar(firstFightTitle).waitForVisible();

            home.specialBtn().waitForVisible().tap();
            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.playFromBeginningBtn().waitForNotPresentOrVisible();

        } else {
            String message = "There is no fight content or has fight content with extras only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }
    }

    @TestCaseId("67029")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK, GroupProps.FIGHTS_CONTENT })
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void fightsContentPlaybackiOSTest() {

        if(firstFightTitle != null && !hasExtrasOnly) {

            ProxyUtils.disableAds();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            alertsChain.dismissAlerts();
            autoPlayChain.enableAutoPlay();
            loginChain.loginIfNot();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);
            home.specialBtn().waitForVisible().tap();

            if(series.specialBtn().isVisible()) {
                series.specialBtn().waitForVisible().tap();
            }

            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            videoPlayer.playerCtr().waitForPresent().tapCenter();
            series.backBtn().waitForVisible().tap();

            home.progressBar(firstFightTitle).waitForVisible();

            home.specialBtn().waitForVisible().tap();
            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.playFromBeginningBtn().waitForNotPresentOrVisible();

        } else {
            String message = "There is no fight content or has fight content with extras only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }
    }

}
