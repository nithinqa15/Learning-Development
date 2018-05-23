package com.viacom.test.vimn.uitests.tests.omniture.countrycheckcalls;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.OmnitureCountryCheckData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.uitests.pageobjects.UnsupportedCountry;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_UNSUPPORTED_COUNTRY;

public class OmnitureUnsupportedCountryScreenTest extends BaseTest {

    UnsupportedCountry unsupportedCountry;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureUnsupportedCountryScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        unsupportedCountry = new UnsupportedCountry();
        expectedMap = new HashMap<>();

    }

    @TestCaseId("35888")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureUnsupportedCountryScreenAndroidTest() {

        unsupportedCountry.waitForNotAvailableInThisCountryTxtVisible();

        expectedMap = OmnitureCountryCheckData.buildUnsupportedCountryScreenExpectedMap();
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_UNSUPPORTED_COUNTRY);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }

    @TestCaseId("35888")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureUnsupportedCountryScreeniOSTest() {
    	
        unsupportedCountry.waitForNotAvailableInThisCountryTxtVisible();

        expectedMap = OmnitureCountryCheckData.buildUnsupportedCountryScreenExpectedMap();
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_UNSUPPORTED_COUNTRY);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
