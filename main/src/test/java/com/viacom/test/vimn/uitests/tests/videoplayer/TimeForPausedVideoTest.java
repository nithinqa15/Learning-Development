package com.viacom.test.vimn.uitests.tests.videoplayer;

import com.viacom.test.vimn.common.VideoPlayerBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TimeForPausedVideoTest extends VideoPlayerBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TimeForPausedVideoTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34843")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = {GroupProps.FULL, GroupProps.VIDEO_PLAYER})
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void timeForPausedVideoAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad();

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(1);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotPresent();
        String timeAtPause = videoPlayer.progressTxt().waitForVisible().getMobileElement().getText();
        videoPlayer.progressTxt().waitForAttribute(TEXT, timeAtPause);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        String compareTimeAtPause = videoPlayer.progressTxt().waitForVisible().getMobileElement().getText();
        videoPlayer.progressTxt().waitForAttribute(TEXT, compareTimeAtPause);

        if (!timeAtPause.equals(compareTimeAtPause)) {
            Assert.fail("The time as the pause button was tapped, " + timeAtPause +
                    ", does not match the the time after verfiying video has stopped, " + compareTimeAtPause +
                    ", meaning the progress text continued during video pause.");
        }

    }
    //add timeForPausedVideoiOStest
}