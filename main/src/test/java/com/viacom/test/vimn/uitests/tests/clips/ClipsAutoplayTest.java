package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import com.viacom.test.vimn.common.proxy.ProxyUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ClipsAutoplayTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Clips clips;
    Series series;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String mainFeedURL;
    String clipTitle;
    String seriesTitle;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = false;
    Boolean clipPrerolledEnabled = false;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ClipsAutoplayTest(String runParams) {
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
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        clipPrerolledEnabled = MainConfig.isPrerollsForClipsEnabled();
        
        if (shortFormEnabled) {
            PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();
            clipTitle = propertyResult.getClips().getFirstClip().getClipTitle();
        }
    }

    @TestCaseId("10923")
    @Features(GroupProps.CLIPS)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.SMOKE })
    @Parameters({ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
            ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
            ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
            ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
            ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void ClipsAutoplayAndroidTest () {

        if (shortFormEnabled) {

            ProxyUtils.disableTve();
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();
            
            if (clipPrerolledEnabled) {
            	ProxyUtils.disableAds();
            }

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.scrollDownToClipTtl(clipTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            DriverManager.getAndroidDriver().navigate().back();

            autoPlayChain.disableAutoPlayClip();
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.scrollDownToClipTtl(clipTitle);
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

    @TestCaseId("10923")
    @Features(GroupProps.CLIPS)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void ClipsAutoplayiOSTest () {
    	
        if (!shortFormEnabled) {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        } else {
            ProxyUtils.disableTve();
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            autoPlayChain.disableAutoPlayClip();
            
            if (clipPrerolledEnabled) {
            	ProxyUtils.disableAds();
            }

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.fullEpisodesBtn().waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForPlayerLoad();
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipPlayBtn(clipTitle).waitForPlayerLoad().waitForVisible();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
            clips.clipCloseBtn().waitForVisible().tap();
            autoPlayChain.enableAutoPlayClip();
            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForPlayerLoad();
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipPlayBtn(clipTitle).waitForPlayerLoad().waitForNotVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        }
    }
}