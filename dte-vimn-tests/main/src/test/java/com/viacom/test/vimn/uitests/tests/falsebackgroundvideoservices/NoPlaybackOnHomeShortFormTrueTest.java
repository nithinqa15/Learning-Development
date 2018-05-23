package com.viacom.test.vimn.uitests.tests.falsebackgroundvideoservices;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.FalseBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoEnabledException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class NoPlaybackOnHomeShortFormTrueTest extends FalseBackgroundVideoServicesBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NoPlaybackOnHomeShortFormTrueTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            falseBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("32007")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void noPlaybackOnHomeShortFormTrueAndroidTest() {

        if (backgroundServiceVideoNotEnabled) {

            if (shortFormEnabled) {

                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                autoPlayChain.enableAutoPlay();

                home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
                home.verifyBackgroundVideoNotPlaying();

            } else {
                String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                        " so skipping test";
                Logger.logMessage(message);
                throw new ShortFormNotEnabledException(message);
            }

        } else {
            throw new BackgroundVideoEnabledException(backgroundEnabledExceptionMessage);
        }
    }
}
