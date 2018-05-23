package com.viacom.test.vimn.uitests.tests.ads;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.AdsBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PausePlayAdsTest extends AdsBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PausePlayAdsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    @TestCaseId("") // VideoAds. Pause Ad 0e371d2d-a606-4bee-8d30-a5bf0056e6ef
    // VideoAds. Resume ad - http://enttester-app-1.mtvn.ad.viacom.com/et5/#/script/edit/91071172-91e8-4fe0-998a-a5bf0056e6ef
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CC_INTL_APP, 
		    	  ParamProps.CC_DOMESTIC_APP, ParamProps.MTV_INTL_APP, 
		    	  ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
		    	  ParamProps.BET_DOMESTIC_APP, ParamProps.VH1_APP,
		    	  ParamProps.PARAMOUNT_APP })
    public void pausePlayAdsAndroidTest() {

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
        videoPlayer.scrubberBar().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        ads.pauseAdsBtn().waitForVisible().tap();
        videoPlayer.verifyVideoNotPlaying(5);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        ads.playAdsBtn().waitForVisible().tap();
        videoPlayer.verifyVideoPlaying(5);
    }

    @TestCaseId("") // VideoAds. Pause Ad
    // VideoAds. Resume ad - http://enttester-app-1.mtvn.ad.viacom.com/et5/#/script/edit/91071172-91e8-4fe0-998a-a5bf0056e6ef
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_ADS }) //revisit. Player control & TSLA rewrite issue
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void PlayPauseAdsiOSTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        autoPlayChain.enableAutoPlay();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.fullEpisodesBtn().waitForVisible().tap();
        home.fullEpisodesBtn().waitForPlayerLoad();
        series.scrollUpToSeriesTitle(seriesTitle);

        //verify ad is playing by checking for pause button and verify that the pause is successful
        ads.pauseAdsBtn().waitForVisible().tap();
        videoPlayer.verifyVideoNotPlaying(5);
        //unpause ad and verify ad is playing
        ads.playAdsBtn().waitForVisible().tap();
        videoPlayer.verifyVideoPlaying(5);
    }
}

