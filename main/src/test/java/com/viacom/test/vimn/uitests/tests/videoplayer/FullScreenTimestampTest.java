package com.viacom.test.vimn.uitests.tests.videoplayer;

import com.viacom.test.vimn.common.VideoPlayerBaseTest;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class FullScreenTimestampTest extends VideoPlayerBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FullScreenTimestampTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34836")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER})
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.TVLAND_APP,
            Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP, Config.ParamProps.CMT_APP, Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void timestampAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad();

        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.maximizeBtn().waitForVisible().tap();
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
    @TestCaseId("34836")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_PLAYER  })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.TVLAND_APP,
            Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP, Config.ParamProps.CMT_APP, Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void fullscreenTimestampiOSTest() {

        ProxyUtils.disableAds();
        
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();
        
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        series.seriesTtl(seriesTitle).waitForViewLoad().rotateScreen(ScreenOrientation.LANDSCAPE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        String startTxt = videoPlayer.progressTxt().waitForVisible().getMobileElement().getAttribute(VALUE);
        String endTxt = videoPlayer.durationTxt().waitForPresent().getMobileElement().getAttribute(VALUE);
        videoPlayer.progressTxt().waitForNotAttribute(VALUE, startTxt);
        videoPlayer.durationTxt().waitForAttribute(VALUE, endTxt).waitForNotVisible();
        
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        startTxt = videoPlayer.progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.fullscreenPlayerCtr().waitForPresent().tapCenter();
        videoPlayer.progressTxt().waitForViewLoad().waitForPresent().waitForNotAttribute(VALUE, startTxt);
        videoPlayer.durationTxt().waitForPresent().waitForNotVisible().waitForAttribute(VALUE, endTxt);
        videoPlayer.verifyVideoIsPlaying(1);
    }
}
