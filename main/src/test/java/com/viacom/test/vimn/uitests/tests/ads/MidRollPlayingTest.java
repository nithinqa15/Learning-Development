package com.viacom.test.vimn.uitests.tests.ads;

import org.openqa.selenium.Point;
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

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MidRollPlayingTest extends AdsBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MidRollPlayingTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod (alwaysRun = true)
    public void setUpTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    @TestCaseId("36113")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void midRollPlayingAndroidTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        ads.waitForAd(1);

        videoPlayer.playerCtr().waitForPlayerLoad().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible();
        Point middleOfScrubber = videoPlayer.scrubberBar().waitForVisible().getMobileElement().getCenter();
        videoPlayer.scrubberBar().waitForVisible().tap(middleOfScrubber.x, middleOfScrubber.y);
        ads.learnMoreBtn().waitForVisible();
    }
}
