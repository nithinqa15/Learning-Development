package com.viacom.test.vimn.uitests.tests.general;

import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class DefaultHomeTemplateTest extends BaseTest {

	// Declare page objects/actions
	private SplashChain splashChain;
    private ChromecastChain chromecastChain;
    private AlertsChain alertsChain;
	private AppDataFeed appDataFeed;
	
	//Declare data
	private String homeScreenTemplate;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public DefaultHomeTemplateTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        
        homeScreenTemplate = appDataFeed.getmainFeedAppConfigurationValue(FeedFactory.getAppMainFeedURL(),"homeScreenTemplate");
	}

	@TestCaseId("35976")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.DEPRECATED, GroupProps.GENERAL })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void defaultHomeTemplateAndroidTest() {

		splashChain.splashAtRest();
        chromecastChain.dismissChromecast();   
        Logger.logConsoleMessage("homeScreenTemplate : " + homeScreenTemplate);
		Assert.assertEquals(homeScreenTemplate, "PanoramicCards", "homeScreenTemplate is not equals to PanoramicCards");
	
	}

	@TestCaseId("35976")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.DEPRECATED, GroupProps.GENERAL })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void defaultHomeTemplateIosTest() {

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		Logger.logConsoleMessage("homeScreenTemplate : " + homeScreenTemplate);
		Assert.assertEquals(homeScreenTemplate, "PanoramicCards", "homeScreenTemplate is not equals to PanoramicCards");

	}
}
