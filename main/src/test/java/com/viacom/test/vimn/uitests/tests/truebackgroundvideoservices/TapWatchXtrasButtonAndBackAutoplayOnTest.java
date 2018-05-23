package com.viacom.test.vimn.uitests.tests.truebackgroundvideoservices;

import com.viacom.test.vimn.common.TrueBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapWatchXtrasButtonAndBackAutoplayOnTest extends TrueBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapWatchXtrasButtonAndBackAutoplayOnTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            trueBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32026")
    @Features(Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES})
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void tapWatchXtrasButtonAndBackAutoplayOnAndroidTest() {

        if (backgroundServiceVideoEnabled) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();

            home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible().tap();

            clips.clipTitle(clipTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

            clips.goBack();

            home.seriesThumbBtn(seriesTitle).waitForVisible();
            home.verifyBackgroundVideoPlaying();

        } else {
            throw new BackgroundVideoNotEnabledException(backgroundServiceVideoDisabledExceptionMessage);
        }
    }

    @TestCaseId("32026")
    @Features(Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES})
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void tapWatchXtrasButtonAndBackAutoplayOniOSTest() {
        // TODO
    }
}
