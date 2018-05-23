package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HOME_MAIN_SCREEN;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.pageobjects.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureMainScreenCarouselPagesTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    Home home;
    AlertsChain alertsChain;
    PropertyFilter propertyFilter;

    //Declare data
    Map<String, String> expectedMap;
    Map<String, String> actualMap;
    PropertyResults propertyResults;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureMainScreenCarouselPagesTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        splashChain = new SplashChain();
        home = new Home();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        expectedMap = new HashMap<>();

        propertyResults = propertyFilter.propertyFilter();

    }

    @TestCaseId("35876")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureMainScreenCarouselPagesAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        propertyResults.forEach((propertyResult -> {
            String seriesTitle = propertyResult.getPropertyTitle();
            Integer seriesPosition = propertyResult.getPositionOnCarousel();
            Integer previousSeriesPosition = seriesPosition - 1;
            expectedMap = OmniturePageData.buildMainScreenCarouselExpectedMap(seriesTitle, String.valueOf(previousSeriesPosition), String.valueOf(seriesPosition));
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
            actualMap.clear();
            ProxyUtils.clearNetworkLogs();
            home.seriesThumbBtn(seriesTitle).waitForVisible().flickCarouselRight();
        }));

    }

    @TestCaseId("35876")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureMainScreenCarouselPagesiOSTest() {

        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        propertyResults.forEach((propertyResult -> {
            String seriesTitle = propertyResult.getPropertyTitle();
            Integer seriesPosition = propertyResult.getPositionOnCarousel();
            Integer previousSeriesPosition = seriesPosition - 1;
            expectedMap = OmniturePageData.buildMainScreenCarouselExpectedMap("no-franchise", String.valueOf(previousSeriesPosition), String.valueOf(seriesPosition));
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
            actualMap.clear();
            ProxyUtils.clearNetworkLogs();
            home.seriesThumbBtn(seriesTitle).waitForVisible().flickCarouselRight();
        }));
    }
}
