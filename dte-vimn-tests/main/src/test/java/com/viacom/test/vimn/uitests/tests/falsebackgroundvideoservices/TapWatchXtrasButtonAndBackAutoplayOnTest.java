package com.viacom.test.vimn.uitests.tests.falsebackgroundvideoservices;

import com.viacom.test.vimn.common.FalseBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapWatchXtrasButtonAndBackAutoplayOnTest extends FalseBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapWatchXtrasButtonAndBackAutoplayOnTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            falseBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32011")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN, Config.GroupProps.CLIPS })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void tapWatchXtrasButtonAndBackAutoplayOnAndroidTest() {

        if (backgroundServiceVideoNotEnabled) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();

            home.enterClipsView(seriesTitle, numberOfSwipes);

            clips.clipTitle(clipTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

            clips.goBack();

            home.seriesThumbBtn(seriesTitle).waitForVisible();

            home.verifyBackgroundVideoNotPlaying();

        } else {
            throw new BackgroundVideoEnabledException(backgroundEnabledExceptionMessage);
        }
        
    }

    @TestCaseId("32011")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.HOME_SCREEN, Config.GroupProps.CLIPS })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void tapWatchXtrasButtonAndBackAutoplayOniOSTest() {
        // TO DO
    }
}
