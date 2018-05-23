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

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON;

public class OmnitureAutoplayToggleCallTest extends BaseTest {

	//Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	SettingsChain settingsChain;
	AutoPlayChain autoPlayToggleChain;
	Settings settings;
	SettingsMenu settingsMenu;

	//Declare data
	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	String cellVidPlayback, autoplay, version;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmnitureAutoplayToggleCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertschain = new AlertsChain();
		autoPlayToggleChain = new AutoPlayChain();
		settingsChain = new SettingsChain();
		settings = new Settings();
		settingsMenu = new SettingsMenu();
		expectedMap = new HashMap<>();
	}

	@TestCaseId("35896-35894") // Also covers 9fc76126-86fa-4353-899c-a7d6010d7430
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void omnitureAutoplayToggleAndroidTest() {
		
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		autoPlayToggleChain.enableAutoPlay();
		ProxyUtils.clearNetworkLogs();
		version = settingsChain.getVersion();
		
		/*disable autoplay to update reporting call*/
		autoPlayToggleChain.disableAutoPlay();
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.cellularTgl().goBack();

		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(version, "vidautoplayOff");
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		actualMap.clear();
		
		/*enable autoplay to update reporting call */
		autoPlayToggleChain.enableAutoPlay();
		settings.settingsBtn().waitForVisible().tap();
        settingsMenu.cellularTgl().goBack();

		expectedMap.put("actName", "vidautoplayOn");
		
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
		
	}
	
	@TestCaseId("35896-35894") // Also covers 9fc76126-86fa-4353-899c-a7d6010d7430
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE  })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void omnitureAutoplayToggleiOSTest() {
		
		splashChain.splashAtRest();
		alertschain.dismissAlerts();
		autoPlayToggleChain.enableAutoPlay();
	    
		//disable autoplay to update reporting call
		autoPlayToggleChain.disableAutoPlay();
		settings.settingsBtn().waitForVisible().tap();

		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		expectedMap.clear();
		actualMap.clear();
		
		//enable autoplay to update reporting call
		settingsMenu.backBtn().waitForVisible().tap();
		autoPlayToggleChain.enableAutoPlay();
		settings.settingsBtn().waitForVisible().tap();
		
		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
	}
}
