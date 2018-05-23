package com.viacom.test.vimn.uitests.tests.truebackgroundvideoservices;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TrueBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapFullEpisodeButtonAndBackAutoplayOffTest extends TrueBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapFullEpisodeButtonAndBackAutoplayOffTest(String runParams) { 
		super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
        	trueBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32021")
    @Features(Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void tapFullEpisodeButtonAndBackAutoplayOffAndroidTest() {

        if (backgroundServiceVideoEnabled) {
            ProxyUtils.disableAds();
            ProxyUtils.disableTve();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlay();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesViewByTappingEpisodesButton(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);
            videoPlayer.playerLoad(Config.StaticProps.LARGE_PAUSE);
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
            series.goBack();
            home.verifyBackgroundVideoNotPlaying();
        } else {
            throw new BackgroundVideoNotEnabledException(backgroundServiceVideoDisabledExceptionMessage);
        }
    }
}