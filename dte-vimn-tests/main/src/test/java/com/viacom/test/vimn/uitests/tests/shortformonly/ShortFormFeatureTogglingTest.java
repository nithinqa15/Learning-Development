package com.viacom.test.vimn.uitests.tests.shortformonly;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ShortFormFeatureTogglingTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Settings settings;
	SettingsMenu settingsMenu;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ShortFormFeatureTogglingTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		home = new Home();
		settings = new Settings();
		settingsMenu = new SettingsMenu();
	}

	@TestCaseId("35988")
	@Features(GroupProps.SHORTFORM_ONLY)
	@Test(groups = { GroupProps.FULL, GroupProps.SHORTFORM_ONLY })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void shortFormFeatureTogglingAndroidTest() {	
    	
    	ProxyUtils.rewriteShortform("true", "false");
		splashChain.splashAtRest();
		home.muteBtn().waitForNotPresentOrVisible();
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.autoPlayClipsTgl().waitForNotPresentOrVisible();
	}

}
