package com.viacom.test.vimn.uitests.tests.playback;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
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
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;


public class FightsContentPlaybackAutoplayOFFTest extends BaseTest {

    //declare pageobjects and action chains
    Home home;
    Series series;
    AllShows allShows;
    VideoPlayer videoPlayer;
    SplashChain splashChain;
    AlertsChain alertsChain;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    LoginChain loginChain;
    HomePropertyFilter propertyFilter;

    //declare data
    String firstFightTitle;
    Integer numberOfSwipes;
    Boolean hasExtras;
    Boolean hasExtrasOnly;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightsContentPlaybackAutoplayOFFTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        allShows = new AllShows();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        autoPlayChain = new AutoPlayChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        videoPlayer = new VideoPlayer();
        loginChain = new LoginChain();

        PropertyResults fightResults = propertyFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on ShowsScreen : " + firstFightTitle);
        hasExtras = firstFight.hasClips();
        hasExtrasOnly = firstFight.hasClipsOnly();
        Logger.logConsoleMessage("First Fights title has extras : " + hasExtras);
        numberOfSwipes = firstFight.getNumOfSwipes();

    }

    @TestCaseId("48079")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK, GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.ANDROID, ParamProps.PARAMOUNT_APP })
    public void fightsContentPlaybackAutoplayOFFAndroidTest() {

        if (firstFightTitle != null && !hasExtrasOnly) {

            ProxyUtils.disableAds();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlay();
            loginChain.loginIfNot();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);
            home.specialBtn().waitForVisible().tap();

            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        } else {
            String message = "There is no fight content or only fight content with extras only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }

    }

    @TestCaseId("48079")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK, GroupProps.FIGHTS_CONTENT})
    @Parameters({ ParamProps.IOS, ParamProps.PARAMOUNT_APP })
    public void fightsContentPlaybackAutoplayOFFiOSTest() {

        if (firstFightTitle != null && !hasExtrasOnly) {

            ProxyUtils.disableAds();

            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlay();
            loginChain.loginIfNot();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwipes);
            home.specialBtn().waitForVisible().tap();

            if (series.specialBtn().isVisible()) {
                series.specialBtn().waitForVisible().tap();
            }

            videoPlayer.progressSpinner().waitForNotPresent();
            videoPlayer.progressSpinner().waitForPlayerLoad();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        } else {
            String message = "There is no fight content or only fight content with extras only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
            Logger.logMessage(message);
            throw new NoEventContentException(message);
        }
    }
}
