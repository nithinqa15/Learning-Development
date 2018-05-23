package com.viacom.test.vimn.uitests.tests.omniture.tvecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_TVE_SIGN_IN_COMPLETED;

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
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureSettingsPageTVELoginSuccessfulCallTest extends BaseTest {

    SplashChain splashChain;
    HomePropertyFilter propertyFilter;
    LoginChain loginChain;
    AlertsChain alertschain;

    Map<String, String> expectedMap;
    Map<String, String> dynamicMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureSettingsPageTVELoginSuccessfulCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        loginChain = new LoginChain();
        dynamicMap = new HashMap<>();
        expectedMap = new HashMap<>();

    }

    @TestCaseId("35930")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.OMNITURE, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureSettingsPageTVELoginSuccessfulCallAndroidTest() {
    	//To-Do
    }

    @TestCaseId("35930")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.OMNITURE, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void omnitureSettingsPageTVELoginSuccessfulCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        ProxyUtils.clearNetworkLogs();
        
        loginChain.loginIfNot();
        
        expectedMap = OmnitureTVEData.buildOmnitureSettingsPageTVELginSuccessfulExpectedMap(LocaleDataFactory.getDefaultProviderData().get("name"), "TVE authenticated user");
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_SIGN_IN_COMPLETED);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}