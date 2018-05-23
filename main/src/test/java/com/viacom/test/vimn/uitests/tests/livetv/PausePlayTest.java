package com.viacom.test.vimn.uitests.tests.livetv;

import java.util.HashMap;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

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

public class PausePlayTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Home home;
	LiveTV liveTV;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
    AlertsChain alertschain;
    
    //Declare data
    String liveSeriesTitle;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PausePlayTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	home = new Home();
    	liveTV = new LiveTV();
    	videoPlayer = new VideoPlayer();
    	appDataFeed = new AppDataFeed();
        alertschain = new AlertsChain();

        String liveTVFeedURL = FeedFactory.getLiveTvScheduleFeedUrl();
        HashMap<String, String> liveTVDetails = appDataFeed.getCurrentLiveTVDetails(liveTVFeedURL);
        liveSeriesTitle = liveTVDetails.get("SeriesTitle");
        	
    }

    @TestCaseId("34703-34706")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV, GroupProps.BROKEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void pausePlayAndroidTest() {
        
    	// 12-7-15 BROKEN until dev resolves android login timeout issues with Android UiAutomator
        
    }

    @TestCaseId("34703-34706")
    @Features(GroupProps.LIVE_TV)

    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV }) //Revisit, Spend enough time to work with player control
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void pausePlayiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.loginIfNot();
        
        home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        
        //verify video pause
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        
        //verify video play
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        //verify video pause
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPauseBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        
        //verify video play
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.smallPlayBtn().waitForVisible().tap().waitForNotVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
}
