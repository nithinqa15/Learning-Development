package com.viacom.test.vimn.uitests.tests.falsebackgroundvideoservices;


import com.viacom.test.vimn.common.FalseBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoEnabledException;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MultipleActionsAndBackHomeWithClipsOnTest extends FalseBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MultipleActionsAndBackHomeWithClipsOnTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            falseBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void multipleActionsAndBackHomeWithClipsOnAndroidTest() {

        if (backgroundServiceVideoNotEnabled) {
            ProxyUtils.disableAds();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.enableAutoPlayClip();

            home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
            home.fullEpisodesBtn().waitForVisible().tap();
            series.watchXtrasBtn().waitForVisible().tap();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.backBtn().waitForPresent().tap();
            home.verifyBackgroundVideoNotPlaying();

            home.enterClipsByTappingOnBackground();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.backBtn().waitForPresent().tap();
            home.verifyBackgroundVideoNotPlaying();

            home.watchExtrasBtn().waitForVisible().tap();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.backBtn().waitForPresent().tap();
            home.verifyBackgroundVideoNotPlaying();
        } else {
            throw new BackgroundVideoEnabledException(backgroundEnabledExceptionMessage);
        }
    }
}