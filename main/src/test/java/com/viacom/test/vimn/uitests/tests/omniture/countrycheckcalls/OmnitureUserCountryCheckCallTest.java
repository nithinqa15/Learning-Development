package com.viacom.test.vimn.uitests.tests.omniture.countrycheckcalls;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_COUNTRY_CHECK;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.omniture.OmnitureCountryCheckData;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureUserCountryCheckCallTest extends BaseTest {

    SplashChain splashChain;
    AppDataFeed appDataFeed;
    Home home;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    String seriesTtl;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureUserCountryCheckCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        appDataFeed = new AppDataFeed();
        home = new Home();
        expectedMap = new HashMap<>();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String featuredFeed = FeedFactory.getFeaturedPropertyFeedURL();
        seriesTtl = appDataFeed.getEpisodeTitles(featuredFeed).get(0);

    }

    @TestCaseId("35914")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureUserCountryCheckCallAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        home.seriesTtl(seriesTtl).waitForVisible();

        expectedMap = OmnitureCountryCheckData.buildUserCountryCheckExpectedMap();
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_COUNTRY_CHECK);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }
    
    @TestCaseId("35914")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureUserCountryCheckCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        home.seriesTtl(seriesTtl).waitForVisible();

        expectedMap = OmnitureCountryCheckData.buildUserCountryCheckExpectedMap();
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_COUNTRY_CHECK);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }


}
