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

public class FullScreenPausePlayTest extends VideoPlayerBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FullScreenPausePlayTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34833-34834")
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER})
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void fullScreenPausePlayAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();

        series.tapEpisodePlayBtn(episodeTitle);
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent(); // pause added to delay until loading spinner displays

        videoPlayer.playerCtr().waitForPresent().tapCenter() ;
        videoPlayer.smallPauseBtn().waitForVisible().waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotPresent();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap().waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
    
    @TestCaseId("34833-34834") // Video Player. Full Screen Pause
    // Video Player. Full Screen Play - a532e8ab-ffcb-4fde-a40e-a5bf0056e6f9
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = {GroupProps.FULL, GroupProps.VIDEO_PLAYER  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void fullscreenPausePlayiOSTest() {

        ProxyUtils.disableAds();
        
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();
        
        //Full-Screen Play
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
    	videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    	
    	//Full-Screen Pause
    	videoPlayer.playerCtr().waitForPresent().tapCenter();
    	videoPlayer.smallPauseBtn().waitForVisible().tap();
    	videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
    	videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
    	videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    	
    	//Full-Screen Play
    	videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //Full-Screen Pause
    	videoPlayer.playerCtr().waitForPresent().tapCenter();
    	videoPlayer.smallPauseBtn().waitForVisible().tap();
    	videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.MEDIUM_PAUSE); //Allow time to player icon disappear
    	videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
    	videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }
}
