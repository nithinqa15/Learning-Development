package com.viacom.test.vimn.uitests.tests.videoplayer;

import com.viacom.test.vimn.common.VideoPlayerBaseTest;
import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.TestCaseId;

import com.viacom.test.vimn.common.proxy.ProxyUtils;

public class DefaultTimeTest extends VideoPlayerBaseTest {

    static int STARTTIME_BUFFER = 20; // A buffer of 20 seconds is given to account for network issues
    static int STARTTIME = 0;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public DefaultTimeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34844")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER})
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void defaultTimeAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();

        videoPlayer.progressSpinner().pause(StaticProps.XLARGE_PAUSE).waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        int progressTimeInSeconds = videoPlayer.getProgressTxtInSeconds();
        int durationTimeInSeconds = videoPlayer.getDurationTxtInSeconds();

        if (progressTimeInSeconds > STARTTIME_BUFFER) { // If progression time is less than 20 seconds (the buffer to account for network issues), it passes
            Assert.fail("Progress Time, " + progressTimeInSeconds + ", is not starting at 00:00");
        }

        if (durationTimeInSeconds == STARTTIME) { // If duration time equals 00:00 test fails
            Assert.fail("durationTimeInSeconds, " + durationTimeInSeconds + ", equals start time, 00:00 and not displaying video length");
        }
    }
    //add iOS TEst
}
