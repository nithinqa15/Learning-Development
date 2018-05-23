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
import com.viacom.test.vimn.uitests.actionchains.CellularToggleChain;
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

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_OFF;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_ON;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON;

public class OmnitureCellularVideoPlaybackToggleCallTest extends BaseTest {

	//Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	SettingsChain settingsChain;
	CellularToggleChain cellularToggleChain;
	AutoPlayChain autoPlayToggleChain;
	Settings settings;
	SettingsMenu settingsMenu;

	//Declare data
	Map<String, String> expectedMap;
	Map<String, String> actualMap ;
	String cellVidPlayback, autoplay, version;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmnitureCellularVideoPlaybackToggleCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertschain = new AlertsChain();
		cellularToggleChain = new CellularToggleChain();
		autoPlayToggleChain = new AutoPlayChain();
		settingsChain = new SettingsChain();
		settings = new Settings();
		settingsMenu = new SettingsMenu();
		expectedMap = new HashMap<>();
	}

	@TestCaseId("35900-35899") // Also covers d6209121-15e5-4748-b6a0-a6ff005574d0
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE })
	@Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
	public void omnitureCellularVideoPlabackToggleAndroidTest() {
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		cellularToggleChain.enableCellular();
		ProxyUtils.clearNetworkLogs();
		version = settingsChain.getVersion();
		
		/*upon fresh launch, app defaults with cellular video playback and autoplay enabled. disable cellular to update reporting call*/
		cellularToggleChain.disableCellular();
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.cellularTgl().goBack();

		expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(version, "playbacksettingsOff");
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		actualMap.clear();
		
		/*enable cellular video playback to update reporting call */
		cellularToggleChain.enableCellular();
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.cellularTgl().goBack();

		expectedMap.put("actName", "playbacksettingsOn");
		
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
		
	}
	
	@TestCaseId("35900-35899") // Also covers d6209121-15e5-4748-b6a0-a6ff005574d0
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.SETTINGS, GroupProps.OMNITURE  })
	@Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
	public void omnitureCellularVideoPlabackToggleiOSTest() {
		
		splashChain.splashAtRest();
		alertschain.dismissAlerts();
		cellularToggleChain.enableCellular();
		
		//Disable cellular to update reporting call
		cellularToggleChain.disableCellular();
		settings.settingsBtn().waitForVisible().tap();

		expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		expectedMap.clear();
		actualMap.clear();
		
		//Enable cellular video playback to update reporting call 
		settingsMenu.backBtn().waitForVisible().tap();
		cellularToggleChain.enableCellular();
		settings.settingsBtn().waitForVisible().tap();
		
		expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
	}
}
