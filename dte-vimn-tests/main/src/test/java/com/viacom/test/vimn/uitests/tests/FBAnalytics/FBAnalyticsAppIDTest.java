package com.viacom.test.vimn.uitests.tests.FBAnalytics;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.FBAnalytics;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Bala;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class FBAnalyticsAppIDTest extends BaseTest {
	//Declare page objects and actions
	Home home;
	Bala bala;
	FBAnalytics fbAnalytics;
	SplashChain splashChain;
	AlertsChain alertsChain;
	
	//Declare data
	String AppIDKey;
	String FBAnalyticsReportingURL;
	Boolean marketingEnabled = false;
	
	 
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FBAnalyticsAppIDTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	home = new Home();
    	bala = new Bala();
    	fbAnalytics = new FBAnalytics();
        splashChain = new SplashChain();
        alertsChain = new AlertsChain();
        
        marketingEnabled = MainConfig.isMarketingEnabled();
    }

    @TestCaseId("35857")
    @Features(GroupProps.FB_ANALYTICS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.FB_ANALYTICS }) // Broken until key miss match issue resolved
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void fbAnalyticsAppIDAndroidTest() {
    	
		splashChain.splashAtRest();
        alertsChain.dismissAlerts();
		
		AppIDKey = fbAnalytics.getFBAppIdKey(TestRun.getAppName());
		Logger.logMessage("Test App - " + TestRun.getAppName() +" : " + "FB AppID - " + AppIDKey);
		
    	if (!marketingEnabled) { //Use rewrite for International brands  
    		
    		ProxyRESTManager.clearLog();
        	ProxyRESTManager.applyResponseFilters(ProxyUtils.enableMarketing());
        	
        	//app main config refreshtime 1 min
        	bala.waitUntilMainConfigRefreshTime(20);
        	DriverManager.getAndroidDriver().resetApp(); // App Reset to reflect new filter
        	
        	home.posterImage().pause(StaticProps.XLARGE_PAUSE);
    	}
    	// Marketing only enabled for US brands
    	Assert.assertTrue(ProxyLogUtils.isRequestUrlFired(StaticProps.FB_ANALYTICS_HOST +
    				".*" + AppIDKey + ".*"), "FB App key not found for : " +  TestRun.getAppName());
    	FBAnalyticsReportingURL = ProxyLogUtils.getRequestURL(StaticProps.FB_ANALYTICS_HOST +".*" + AppIDKey + ".*");
    	Logger.logMessage("FB AppID verified in : " + FBAnalyticsReportingURL);
	}

	@TestCaseId("35857")
    @Features(GroupProps.FB_ANALYTICS)
    @Test(groups = { GroupProps.FULL, GroupProps.FB_ANALYTICS })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void fbAnalyticsAppIDiOSTest() {
		
		splashChain.splashAtRest();
        alertsChain.dismissAlerts();
		
		AppIDKey = fbAnalytics.getFBAppIdKey(TestRun.getAppName());
		Logger.logMessage("Test App - " + TestRun.getAppName() +" : " + "FB AppID - " + AppIDKey);
		
    	if (!marketingEnabled) { //Use rewrite for International brands  
    		
    		ProxyRESTManager.clearLog();
        	ProxyRESTManager.applyResponseFilters(ProxyUtils.enableMarketing());
        	
        	//app main config refreshtime 1 min
        	bala.waitUntilMainConfigRefreshTime(20);
        	DriverManager.getIOSDriver().resetApp(); // App Reset to reflect new filter
        	
        	home.posterImage().pause(StaticProps.XLARGE_PAUSE);
    	}
    	// Marketing only enabled for US brands
    	Assert.assertTrue(ProxyLogUtils.isRequestUrlFired(StaticProps.FB_ANALYTICS_HOST +
    				".*" + AppIDKey + ".*"), "FB App key not found for : " +  TestRun.getAppName());
    	FBAnalyticsReportingURL = ProxyLogUtils.getRequestURL(StaticProps.FB_ANALYTICS_HOST +".*" + AppIDKey + ".*");
    	Logger.logMessage("FB AppID verified in : " + FBAnalyticsReportingURL);
    }
}
