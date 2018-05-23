package com.viacom.test.vimn.uitests.tests.omniture.tvecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_MVPD_SELECTED;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmnitureTVEData;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureSettingsPageMVPDSelectedCallTest extends BaseTest {

	SplashChain splashChain;
	HomePropertyFilter propertyFilter;
	LoginChain loginChain;
	ChromecastChain chromecastChain;
	SettingsChain settingsChain;
	AlertsChain alertschain;
	private Settings settings;
	private SettingsMenu settingsMenu;
	private SelectProvider selectProvider;

	Map<String, String> expectedMap;
	String version;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmnitureSettingsPageMVPDSelectedCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {

		splashChain = new SplashChain();
		settingsChain = new SettingsChain();
		alertschain = new AlertsChain();
		chromecastChain = new ChromecastChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		settings = new Settings();
		settingsMenu = new SettingsMenu();
		selectProvider = new SelectProvider();
		loginChain = new LoginChain();
		expectedMap = new HashMap<>();

	}

	@TestCaseId("35919")
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.TVE})
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omnitureSettingsPageMVPDSelectedCallAndroidTest() {

		splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
		loginChain.logoutIfLoggedIn();
		ProxyUtils.clearNetworkLogs();
		version = settingsChain.getVersion();

		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.signInBtn().waitForVisible().tap();
		if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
			selectProvider.backBtn().waitForVisible().tap();
		}
		if (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp()) {
			selectProvider.dishProviderBtn().waitForVisible().tap();
		} else {
			selectProvider.optimumProviderBtn().waitForVisible().tap();
		}

		expectedMap = OmnitureTVEData.buildOmnitureSettingsPageMVPDSelectedExpectedMap(version, LocaleDataFactory.getDefaultProviderData().get("name"));
		Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_MVPD_SELECTED);
		Omniture.assertOmnitureValues(expectedMap, actualMap);	
	}

	@TestCaseId("35919")
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.TVE  })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omnitureSettingsPageMVPDSelectedCalliOSTest() {

		splashChain.splashAtRest();
		alertschain.dismissAlerts();
		loginChain.logoutIfLoggedIn();
		ProxyUtils.clearNetworkLogs();

		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.signInBtn().waitForVisible().tap();
		alertschain.dismissAlerts();
		if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
			selectProvider.backBtn().waitForVisible().tap();
		}
		if (TestRun.isCCINTLApp() || TestRun.isMTVINTLApp()) {
			selectProvider.dishProviderBtn().waitForVisible().tap();
		} else {
			selectProvider.optimumProviderBtn().waitForVisible().tap();
		}

		expectedMap = OmnitureTVEData.buildOmnitureSettingsPageMVPDSelectedExpectedMap(null,LocaleDataFactory.getDefaultProviderData().get("name"));
		Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_MVPD_SELECTED);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
	}
}
