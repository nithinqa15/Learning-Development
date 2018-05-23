package com.viacom.test.vimn.uitests.tests.general;

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
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ErrorInPromotionFeedsTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	Home home;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ErrorInPromotionFeedsTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		home = new Home();
	}

	// VB - 11.7.2017 - Test is iOS Only
    @Deprecated
	@TestCaseId("")
	@Features(GroupProps.GENERAL)
	@Test(groups = { GroupProps.BROKEN, GroupProps.GENERAL })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void errorInPromotionFeedsAndroidTest() {
		
		ProxyUtils.rewritePromoFeedWithAnInternalErrorResponse();
		splashChain.splashAtRest();
		String responseBody = ProxyLogUtils.getResponse(Config.StaticProps.REGEX_MAINFEED_URL);
		Logger.logMessage("PromotionFeedResponse : "+responseBody);
		home.ohNoTxt().waitForVisible();
		home.retryBtn().waitForVisible();
	}
}
