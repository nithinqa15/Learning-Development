package com.viacom.test.vimn.uitests.tests.truebackgroundvideoservices;

import com.viacom.test.vimn.common.TrueBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapFullEpisodeButtonAndBackClipAutoPlayOffTest extends TrueBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapFullEpisodeButtonAndBackClipAutoPlayOffTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void SetUpTest() {
        if (seriesTitle == null) {
            trueBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32027")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void tapFullEpisodeButtonAndBackClipAutoPlayOffAndroidTest() {

        if (backgroundServiceVideoEnabled) {
            ProxyUtils.disableAds();
            ProxyUtils.disableTve();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlayClip();
            
            home.enterSeriesViewByTappingEpisodesButton(seriesTitle, numberOfSwipes);
            series.seriesTtl(seriesTitle).waitForViewLoad();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.backBtn().waitForPresent().tap();
            home.verifyBackgroundVideoNotPlaying();

        } else {
            throw new BackgroundVideoNotEnabledException(backgroundServiceVideoDisabledExceptionMessage);
        }
    }
}
