package com.viacom.test.vimn.uitests.tests.videoplayer;

import com.viacom.test.vimn.common.VideoPlayerBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TimestampTest extends VideoPlayerBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TimestampTest(String runParams) {
        super.setRunParams(runParams);
    }


    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34828")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER})
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void timestampAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad();

        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        progressBar.loadingSpinnerIcn().pause(StaticProps.XLARGE_PAUSE).waitForNotPresent(); // pause added to delay until loading spinner displays
        videoPlayer.playerCtr().waitForPresent().tapCenter() ;

        String startTxt = videoPlayer.progressTxt().waitForVisible().getMobileElement().getAttribute(TEXT);
        String endTxt = videoPlayer.durationTxt().waitForVisible().getMobileElement().getAttribute(TEXT);
        videoPlayer.progressTxt().waitForNotAttribute(TEXT, startTxt);
        videoPlayer.durationTxt().waitForAttribute(TEXT, endTxt).waitForNotPresent();

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        startTxt = videoPlayer.progressTxt().waitForPresent().getMobileElement().getAttribute(TEXT);
        MobileElement scrubberElement = videoPlayer.scrubberBar().waitForPresent().getMobileElement();
        Integer scrubberX = scrubberElement.getCenter().x;
        Integer scrubberY = scrubberElement.getCenter().y;
        videoPlayer.scrubberBar().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.scrubberBar().waitForVisible().tap(scrubberX, scrubberY);
        videoPlayer.progressTxt().waitForPresent().waitForNotAttribute(TEXT, startTxt);
        videoPlayer.playerCtr().exposeControlRack();
        videoPlayer.durationTxt().waitForPresent().waitForAttribute(TEXT, endTxt).waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
    
    //@PV - Test case broken because progress and duration text doesn't contain time value
    @TestCaseId("34828")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_PLAYER  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void timestampiOSTest() {

        splashChain.splashAtRest();
        alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        String startTxt = videoPlayer.progressTxt().waitForVisible().getMobileElement().getAttribute(VALUE);
        String endTxt = videoPlayer.durationTxt().waitForPresent().getMobileElement().getAttribute(VALUE);
        videoPlayer.progressTxt().waitForNotAttribute(VALUE, startTxt);
        videoPlayer.durationTxt().waitForAttribute(VALUE, endTxt).waitForNotVisible();

        startTxt = videoPlayer.progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.progressTxt().waitForViewLoad().waitForPresent().waitForNotAttribute(VALUE, startTxt);
        videoPlayer.durationTxt().waitForPresent().waitForNotVisible().waitForAttribute(VALUE, endTxt);
        videoPlayer.verifyVideoIsPlaying(1);

    }
}
