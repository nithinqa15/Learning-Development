package com.viacom.test.vimn.uitests.tests.heartbeat;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.AdsBaseTest;
import com.viacom.test.vimn.common.heartbeat.HeartbeatLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AD_START;

public class HeartbeatAdStartTest extends AdsBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public HeartbeatAdStartTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
       adsSetupTest();
    }

    //@TestCaseId("")
    @Features(Config.GroupProps.VIDEO_ADS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
    public void heartbeatAdStartAndroidTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.scrubberBar().waitForNotPresent();
        ads.fwControlsBar().waitForVisible();

        //verify adStart call is present
        HeartbeatLogUtils.waitForCall("s:meta:v.activity", EXPECTED_PARAM_AD_START, 15);
    }
    
    //@TestCaseId("")
    @Features(Config.GroupProps.VIDEO_ADS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
    public void heartbeatAdStartiOSTest() {
    	if (seriesTitle != null) {
        	splashChain.splashAtRest();
        	chromecastChain.dismissChromecast();
        	autoPlayChain.enableAutoPlay();
        	
            //Ensure Ads play
            ProxyUtils.rewriteTSLA(0);
            ProxyUtils.disableTve();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            if (series.fullEpisodesBtn().isVisible()) { //Btn not available for Episode only series
            	series.fullEpisodesBtn().waitForVisible().tap();
            }
            videoPlayer.playFromBeginningBtn().waitForNotPresent();
            videoPlayer.scrubberBar().waitForNotPresent().pause(StaticProps.LARGE_PAUSE);

            //verify adStart call is present
            HeartbeatLogUtils.waitForCall("s:meta:v.activity", EXPECTED_PARAM_AD_START, 15);
    	} else {
    		Logger.logConsoleMessage("<-------- Meta data collection Error for Ads Test ------>");
    	}
    }
}
