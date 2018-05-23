package com.viacom.test.vimn.uitests.tests.omniture.playercalls;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.AdsBaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmniturePlayerData;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AD_START;

import java.util.Map;

public class OmnitureAdStartCallTest extends AdsBaseTest {
	
    //Declare data
    Map<String, String> expectedMap;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureAdStartCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    //@TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = {GroupProps.FULL, GroupProps.OMNITURE, GroupProps.BENTO_SMOKE})
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CC_INTL_APP, ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
            ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP})
    public void omnitureAdStartAndroidTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.scrubberBar().waitForNotPresent();
        ads.fwControlsBar().waitForVisible();

        //verify adStart call is present
        OmnitureLogUtils.getActualMap("activity", EXPECTED_PARAM_AD_START);
    }
    
    //@TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.BENTO_SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, ParamProps.CC_INTL_APP, ParamProps.CC_DOMESTIC_APP,
             ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
             ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureAdStartiOSTest() {
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
	        expectedMap = OmniturePlayerData.buildAdsStartExpectedMap();
	        Map<String, String> actualMap = OmnitureLogUtils.getActualMap("activity", EXPECTED_PARAM_AD_START);
	        Omniture.assertOmnitureValues(expectedMap, actualMap);
    	} else {
    		Logger.logConsoleMessage("<-------- Meta data collection Error for Ads Test ------>");
    	}
    }
}
