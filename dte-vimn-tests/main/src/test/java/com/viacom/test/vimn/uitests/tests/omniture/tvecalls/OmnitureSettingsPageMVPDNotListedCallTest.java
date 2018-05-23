package com.viacom.test.vimn.uitests.tests.omniture.tvecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_TVE_MVPD_NOT_LISTED;

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
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureSettingsPageMVPDNotListedCallTest extends BaseTest {

    SplashChain splashChain;
    HomePropertyFilter propertyFilter;
    LoginChain loginChain;
    AlertsChain alertschain;
    SettingsChain settingsChain;
    ChromecastChain chromecastChain;
    private Settings settings;
	private SettingsMenu settingsMenu;
	private SelectProvider selectProvider;
	private String version;

    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureSettingsPageMVPDNotListedCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        settingsChain = new SettingsChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        selectProvider = new SelectProvider();
        loginChain = new LoginChain();
        expectedMap = new HashMap<>();

    }

    @TestCaseId("35931")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.OMNITURE, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureSettingsPageMVPDNotListedCallAndroidTest() {
        	
    		/* TODO: THIS TEST IS BROKEN BECAUSE OF https://jira.mtvi.com/browse/VAA-5418 */
    	
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
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.dontSeeProviderBtn().waitForVisible().tap();
       
        expectedMap = OmnitureTVEData.buildOmnitureSettingsPageMVPDNotListedExpectedMap(version, "TVE/provider not listed");
        
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_MVPD_NOT_LISTED);
        Omniture.assertOmnitureValues(expectedMap, actualMap);		
    }
    
    @TestCaseId("35931")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureSettingsPageMVPDNotListedCalliOSTest() {

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
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.dontSeeProviderBtn().waitForVisible().tap();

        expectedMap = OmnitureTVEData.buildOmnitureSettingsPageMVPDNotListedExpectedMap(null, "TVE/provider not listed");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_MVPD_NOT_LISTED);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}