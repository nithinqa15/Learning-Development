package com.viacom.test.vimn.uitests.tests.ads;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.AdsBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class FullscreenPausePlayAdsTest extends AdsBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FullscreenPausePlayAdsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    @TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void fullscreenPlayPauseAdsAndroidTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();

        MobileElement scrubberElement = videoPlayer.scrubberBar().waitForPresent().getMobileElement();
        Integer scrubberX = scrubberElement.getCenter().x;
        Integer scrubberY = scrubberElement.getCenter().y;

        videoPlayer.scrubberBar().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.scrubberBar().waitForVisible().tap(scrubberX, scrubberY);
        videoPlayer.playerCtr().waitForViewLoad().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap().waitForVisible();

        ads.pauseAdsBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoNotPlaying(10);
        ads.playAdsBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoPlaying(10);
    }

    @TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_ADS  }) //revisit. Player control & TSLA rewrite issue
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void fullscreenPlayPauseAdsiOSTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        autoPlayChain.enableAutoPlay();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.fullEpisodesBtn().waitForVisible().tap();
        home.fullEpisodesBtn().waitForPlayerLoad();
        series.scrollUpToSeriesTitle(seriesTitle);

        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.smallPauseBtn().waitForNotVisible();
        videoPlayer.verifyVideoPlaying(10);

        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        ads.pauseAdsBtn().tap().waitForNotVisible();
        videoPlayer.verifyVideoNotPlaying(5);
        ads.playAdsBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoPlaying(5);
    }
}