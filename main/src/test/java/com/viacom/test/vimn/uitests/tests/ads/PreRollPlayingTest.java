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

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PreRollPlayingTest extends AdsBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PreRollPlayingTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    @TestCaseId("36114")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.MTV_INTL_APP,
	              ParamProps.MTV_DOMESTIC_APP, ParamProps.CC_INTL_APP,
	              ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
	              ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP, 
	              ParamProps.BET_DOMESTIC_APP })
    public void preRollPlayingAndroidTest() {

        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();
        ads.waitForAd(1);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible();
        series.episodePlayBtn(nextPrivateEpisode).waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        ads.learnMoreBtn().waitForVisible();
    }
}
