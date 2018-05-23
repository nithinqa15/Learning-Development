package com.viacom.test.vimn.uitests.tests.general;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class NonexistenceHomeTemplateTest extends BaseTest {
	// Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AppDataFeed appDataFeed;

	// Declare data
	String homeScreenTemplate;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public NonexistenceHomeTemplateTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		appDataFeed = new AppDataFeed();
		chromecastChain = new ChromecastChain();

	}

	@TestCaseId("35984")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.DEPRECATED, GroupProps.GENERAL })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void nonexistenceHomeTemplateAndroidTest() {

		ProxyUtils.rewriteHomeScreenTemplate("\"Abcd\"");
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		String responseBody = ProxyLogUtils.getResponse(Config.StaticProps.REGEX_MAINFEED_URL);
		homeScreenTemplate = appDataFeed.getMainFeedHomeScreenTemplateValue(responseBody);
		Logger.logMessage("homeScreenTemplate : " + homeScreenTemplate);
		Assert.assertEquals(homeScreenTemplate, "Abcd", "homeScreenTemplate doesn't equal Abcd");

	}
}
