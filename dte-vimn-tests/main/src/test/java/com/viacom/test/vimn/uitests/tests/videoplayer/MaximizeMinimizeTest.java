package com.viacom.test.vimn.uitests.tests.videoplayer;

import com.viacom.test.vimn.common.VideoPlayerBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.props.Orientation;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MaximizeMinimizeTest extends VideoPlayerBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MaximizeMinimizeTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            videoPlayerSetupTest();
        }
    }

    @TestCaseId("34841") // Video Player. Full Screen Minimize
    // Video Player. Full Screen Maximize - 1dc9c628-3a01-4dc6-848e-a5bf0056e6fd
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER })
    @Parameters({ ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void maximizeMinimizeAndroidTest() {

        ProxyUtils.disableAds();
        ProxyUtils.disableTve();

    	splashChain.splashAtRest();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();

        videoPlayer.playerCtr().waitForVisible().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        videoPlayer.minimizeBtn().waitForNotPresent();
        videoPlayer.fullscreenPlayerCtr().waitForVisible().tapCenter();
        videoPlayer.minimizeBtn().waitForVisible().tap().waitForScreenOrientation(Orientation.PORTRAIT);
        videoPlayer.maximizeBtn().waitForNotPresent();
        series.scrollUpToSeriesTitle(seriesTitle);
        
    }

    @TestCaseId("34841") // Video Player. Full Screen Minimize
    // Video Player. Full Screen Maximize - 1dc9c628-3a01-4dc6-848e-a5bf0056e6fd
    @Features(GroupProps.VIDEO_PLAYER)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER  })
    @Parameters({ ParamProps.IOS, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP, ParamProps.VH1_APP,
            ParamProps.PARAMOUNT_APP })
    public void maximizeMinimizeiOSTest() {

        ProxyUtils.disableAds();
        
    	splashChain.splashAtRest();
    	alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();
        
        //Screen Maximize
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.LARGE_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //Screen Minimize
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.minimizeBtn().waitForNotVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.LARGE_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //Screen Maximize
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.LARGE_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //Screen Minimize
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.minimizeBtn().waitForNotVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter().pause(StaticProps.LARGE_PAUSE); //Allow time to player icon disappear
        videoPlayer.playFromBeginningBtn().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
}
