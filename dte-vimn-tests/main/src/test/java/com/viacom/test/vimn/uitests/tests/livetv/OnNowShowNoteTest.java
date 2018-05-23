package com.viacom.test.vimn.uitests.tests.livetv;

import java.util.HashMap;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OnNowShowNoteTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Home home;
	LiveTV liveTV;
	AppDataFeed appDataFeed;
    AlertsChain alertschain;
    
    //Declare data
    String liveSeriesTitle;
    String liveEpisodeTitle;
    String liveEpisodeDescription;
    String liveEpisodeAirTime;
    Integer flickCount;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OnNowShowNoteTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	home = new Home();
    	liveTV = new LiveTV();
    	appDataFeed = new AppDataFeed();
        alertschain = new AlertsChain();

        String liveTVFeedURL = FeedFactory.getLiveTvScheduleFeedUrl();
        HashMap<String, String> liveTVDetails = appDataFeed.getCurrentLiveTVDetails(liveTVFeedURL);
        liveSeriesTitle = liveTVDetails.get("SeriesTitle");
        liveEpisodeTitle = liveTVDetails.get("EpisodeTitle");
        liveEpisodeDescription = liveTVDetails.get("Description");
        liveEpisodeAirTime = liveTVDetails.get("BroadcastTime");
        
        flickCount = appDataFeed.getPropertyTitles(FeedFactory.getPromoListFeedURL()).size() + 1;
        
    }

    @TestCaseId("34698")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void onNowShowNoteAndroidTest() {
        
    	// 12-7-15 BROKEN until dev resolves android login timeout issues with Android UiAutomator
        
    }

    @TestCaseId("34698")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV }) //Live TV feed parsing issue
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void onNowShowNoteiOSTest() {
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.loginIfNot();
        
        //verify live TV series detail on live screen 
        home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        home.onNowBtn().waitForPresent();
        liveTV.onNowEpisodeTtl(liveEpisodeTitle).waitForPresent();
        liveTV.onNowAirTimeTxt(liveEpisodeAirTime).waitForPresent();
        liveTV.onNowEpisodeDescriptionTxt(liveEpisodeDescription).waitForPresent();
        liveTV.liveTVCloseBtn().waitForVisible().tap();
    }
}
