package com.viacom.test.vimn.uitests.tests.truebackgroundvideoservices;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TrueBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapWatchXtrasButtonAndBackClipAutoplayOffTest extends TrueBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapWatchXtrasButtonAndBackClipAutoplayOffTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            trueBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32025")
    @Features(Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES})
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void tapWatchXtrasButtonAndBackClipAutoplayOffAndroidTest() {

        if (backgroundServiceVideoEnabled) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlayClip();

            home.enterClipsView(seriesTitle, numberOfSwipes);

            clips.clipTitle(clipTitle).waitForVisible();
            videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
            videoPlayer.smallPlayBtn().waitForVisible().tap().waitForNotPresent();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            clips.goBack();
            home.seriesThumbBtn(seriesTitle).waitForVisible();
            home.verifyBackgroundVideoNotPlaying();

        } else {
            throw new BackgroundVideoNotEnabledException(backgroundServiceVideoDisabledExceptionMessage);
        }
    }
}
