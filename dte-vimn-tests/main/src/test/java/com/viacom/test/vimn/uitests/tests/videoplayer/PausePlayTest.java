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

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PausePlayTest extends VideoPlayerBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PausePlayTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34833") // Video Player. Pause Test and
    // Video Player. Play Test - 83165bf8-dbcc-4170-bbed-a5bf0056e6fd
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER })
    @Parameters({ ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void pausePlayAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotPresent();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap().waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("34833") // Video Player. Pause Test and
    // Video Player. Play Test - 83165bf8-dbcc-4170-bbed-a5bf0056e6fd
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void pausePlayiOSTest() {

        ProxyUtils.disableAds();
        
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        // Play
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.progressSpinner().waitForPlayerLoad();
    	videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
    	// Pause
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        
        // Play
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
    	// Pause
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }
}
