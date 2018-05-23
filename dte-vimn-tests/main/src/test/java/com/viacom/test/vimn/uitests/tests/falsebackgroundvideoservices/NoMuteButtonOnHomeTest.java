package com.viacom.test.vimn.uitests.tests.falsebackgroundvideoservices;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.FalseBackgroundVideoServicesBaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class NoMuteButtonOnHomeTest extends FalseBackgroundVideoServicesBaseTest {


    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NoMuteButtonOnHomeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        if(seriesTitle == null) {
            falseBackgroundVideoServicesSetupTest();
        }
    }

    @TestCaseId("")
    @Features(Config.GroupProps.HOME_SCREEN)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.HOME_SCREEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void noMuteButtonOnHomeAndroidTest() {
        if(backgroundServiceVideoNotEnabled) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();

            home.muteBtn().waitForNotPresent();
            home.flickRightToSeriesThumb(seriesTitle,numberOfSwipes);
            home.verifyBackgroundVideoNotPlaying();

        } else {
            String message = "Background Video is enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new BackgroundVideoNotEnabledException(message);
        }
    }
}
