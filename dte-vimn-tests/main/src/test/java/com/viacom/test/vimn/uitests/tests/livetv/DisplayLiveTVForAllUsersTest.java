package com.viacom.test.vimn.uitests.tests.livetv;

import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.LiveTVNotEnabledForAllUsersException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

public class DisplayLiveTVForAllUsersTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Home home;
	LiveTV liveTV;
    AlertsChain alertschain;
    Settings settings;
    SettingsMenu settingsMenu;
    
    //Declare data
    Boolean displayLiveTVForAllUsersEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public DisplayLiveTVForAllUsersTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	home = new Home();
    	liveTV = new LiveTV();
        alertschain = new AlertsChain();

        displayLiveTVForAllUsersEnabled = MainConfig.isDisplayLiveTVForAllUsersEnabled();
    }

    @TestCaseId("52800")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV }) //https://jira.mtvi.com/browse/VAA-6509
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void displayLiveTVForAllUsersAndroidTest() {
    	
     	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        if (displayLiveTVForAllUsersEnabled) {
        	home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        } else {
    		String message = "LiveTV not available for all users so skipping test";
    		Logger.logMessage(message);
    		throw new LiveTVNotEnabledForAllUsersException(message);
        }
    }

    @TestCaseId("52800")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.FULL, GroupProps.LIVE_TV })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void displayLiveTVForAllUsersiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        if (displayLiveTVForAllUsersEnabled) {
        	home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        } else {
    		String message = "LiveTV not available for all users so skipping test";
    		Logger.logMessage(message);
    		throw new LiveTVNotEnabledForAllUsersException(message);
        }
    }
}
