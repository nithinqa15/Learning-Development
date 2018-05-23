package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.CommandExecutorUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON;

public class OmnitureAutoplayClipsToggleCallTest extends BaseTest {

	//Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	SettingsChain settingsChain;
	AutoPlayChain autoPlayToggleChain;
	Settings settings;
	SettingsMenu settingsMenu;
	AlertsChain alertschain;


	//Declare data
	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	String cellVidPlayback, autoplay, version;


	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmnitureAutoplayClipsToggleCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayToggleChain = new AutoPlayChain();
		alertschain = new AlertsChain();
		settingsChain = new SettingsChain();
		settings = new Settings();
		settingsMenu = new SettingsMenu();
		expectedMap = new HashMap<>();
	}

	@TestCaseId("35893-35902") // Also covers d1d1bf0a-73ee-4ba8-a880-a7ae006aaca5
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void omnitureAutoplayClipsToggleAndroidTest() {
		
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		autoPlayToggleChain.enableAutoPlayClip();
		ProxyUtils.clearNetworkLogs();
		version = settingsChain.getVersion();
		
		/*disable autoplay clips to update reporting call*/
		autoPlayToggleChain.disableAutoPlayClip();
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.cellularTgl().goBack();

		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(version, "clipautoplayOff");
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		actualMap.clear();
		
		/*enable autoplay to update reporting call */
		autoPlayToggleChain.enableAutoPlayClip();
		settings.settingsBtn().waitForVisible().tap();
		expectedMap.replace("actName", "clipautoplayOn");
		
        settingsMenu.cellularTgl().goBack();
		
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
	}
	
	@TestCaseId("35893-35092") // Also covers d1d1bf0a-73ee-4ba8-a880-a7ae006aaca5
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE  })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void omnitureAutoplayClipsToggleiOSTest() {

		splashChain.splashAtRest();
		alertschain.dismissAlerts();
		autoPlayToggleChain.enableAutoPlayClip();

		//disable autoplay clips to update reporting call
		autoPlayToggleChain.disableAutoPlayClip();
		settings.settingsBtn().waitForVisible().tap();

		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);

		expectedMap.clear();
		actualMap.clear();

		//enable autoplay clips to update reporting call
		settingsMenu.backBtn().waitForVisible().tap();
		autoPlayToggleChain.enableAutoPlayClip();
		settings.settingsBtn().waitForVisible().tap();
		
		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
	}
}
