package com.viacom.test.vimn.uitests.tests.omniture.tvecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmnitureTVEData;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureTVEUserAuthenticationStatusCallTest extends BaseTest {

    SplashChain splashChain;
    HomePropertyFilter propertyFilter;
    LoginChain loginChain;
	AlertsChain alertschain;

    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureTVEUserAuthenticationStatusCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        loginChain = new LoginChain();
        expectedMap = new HashMap<>();
		alertschain = new AlertsChain();

    }

    @TestCaseId("35916")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE, GroupProps.OMNITURE }, dependsOnMethods = { "omnitureTVEUserAuthenticationNotLoggedInCallAndroidTest" })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEUserAuthenticationLoggedInCallAndroidTest() {

        splashChain.splashAtRest();

        expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("TVE authenticated user", LocaleDataFactory.getDefaultProviderData().get("name"));
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }

    @TestCaseId("35916")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEUserAuthenticationNotLoggedInCallAndroidTest() {

        splashChain.splashAtRest();

        expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("guest user", "no provider");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        expectedMap.clear();

        loginChain.loginIfNot();
        ProxyUtils.clearNetworkLogs();

    }
    
    @TestCaseId("35916")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEUserAuthenticationNotLoggedInCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        DriverManager.getIOSDriver().resetApp();

        expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("guest user", "no provider");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        expectedMap.clear();

        loginChain.loginIfNot();
        ProxyUtils.clearNetworkLogs();
    }

    @TestCaseId("35916")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE, GroupProps.OMNITURE  }, dependsOnMethods = { "omnitureTVEUserAuthenticationNotLoggedInCalliOSTest" })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureTVEUserAuthenticationLoggedInCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();

        expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("TVE authenticated user", LocaleDataFactory.getDefaultProviderData().get("name"));
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
