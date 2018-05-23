package com.viacom.test.vimn.uitests.tests.livetv;

import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.props.Orientation;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class MaximizeMinimizeTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	ChromecastChain chromecastChain;
	Home home;
	LiveTV liveTV;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
    AlertsChain alertschain;
    
    //Declare data
    String liveSeriesTitle;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MaximizeMinimizeTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	chromecastChain = new ChromecastChain();
    	home = new Home();
    	liveTV = new LiveTV();
    	videoPlayer = new VideoPlayer();
    	appDataFeed = new AppDataFeed();
        alertschain = new AlertsChain();

    }

    @TestCaseId("34704")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.LIVE_TV, GroupProps.BROKEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void maximizeMinimizeAndroidTest() {
    	
    	splashChain.splashAtRest();
    	chromecastChain.dismissChromecast();
        loginChain.loginIfNot();

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowSeriesTtl(liveSeriesTitle).waitForPlayerLoad().waitForPresent();
  
        videoPlayer.maximizeBtn().exposeControlRack().waitForVisible().tap().waitForScreenOrientation(Orientation.LANDSCAPE);
        videoPlayer.minimizeBtn().waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        videoPlayer.liveTVPlayer().waitForVisible().tap();
        videoPlayer.minimizeBtn().waitForVisible().tap().waitForScreenOrientation(Orientation.PORTRAIT);
        videoPlayer.maximizeBtn().waitForVisible();
        liveTV.onNowSeriesTtl(liveSeriesTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        
    	// 12-7-15 BROKEN until dev resolves android login timeout issues with Android UiAutomator
       
    }

    @TestCaseId("34704")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV }) //Revisit, Spend enough time to work with player control
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void maximizeMinimizeiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.loginIfNot();
        
        //verify video play on full screen
        home.liveTVBtn().waitForVisible().tap();
        videoPlayer.progressSpinner().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //verify video play on small screen
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.minimizeBtn().waitForNotVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //verify video play on full screen
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //verify video play on small screen
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.minimizeBtn().waitForNotVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.progressSpinner().waitForPlayerLoad();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
}
