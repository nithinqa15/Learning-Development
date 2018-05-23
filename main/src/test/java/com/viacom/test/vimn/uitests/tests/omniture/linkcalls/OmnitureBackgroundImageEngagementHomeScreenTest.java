package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_BACKGROUND_IMAGE_ENGAGEMENT_ON_HOME_SCREEN;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureBackgroundImageEngagementHomeScreenTest extends BaseTest {

    AppDataFeed appDataFeed;
    Home home;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    SettingsChain settingsChain;
    HomePropertyFilter propertyFilter;
    AlertsChain alertschain;

    String seriesTitle;
    Integer numberOfSwipes;
    String seriesPosition;
    String ClipTitle;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureBackgroundImageEngagementHomeScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        expectedMap = new HashMap<>();
        home = new Home();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        settingsChain = new SettingsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertschain = new AlertsChain();

        PropertyResults propertyResults = propertyFilter.withBackgroundImage().propertyFilter();
        PropertyResult propertyResult = propertyResults.getFirstProperty();

        seriesTitle = propertyResult.getPropertyTitle();
        ClipTitle = propertyResults.getFirstProperty().getClips().getFirstClip().getClipTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        seriesPosition = String.valueOf(propertyResult.getPositionOnCarousel());

    }

    @TestCaseId("35905")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureBackgroundImageEngagementHomeScreenAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        String version = settingsChain.getVersion();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.enterClipsByTappingOnBackground();

        expectedMap = OmnitureLinkData.buildBackgroundImageEngagementHomeScreenExpectedMap(seriesTitle, seriesPosition, version, ClipTitle);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_BACKGROUND_IMAGE_ENGAGEMENT_ON_HOME_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }
    
    @TestCaseId("35905")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureBackgroundImageEngagementHomeScreeniOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        
        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.enterClipsByTappingOnBackground();

        expectedMap = OmnitureLinkData.buildBackgroundImageEngagementHomeScreenExpectedMap(seriesTitle, seriesPosition, null, ClipTitle);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_BACKGROUND_IMAGE_ENGAGEMENT_ON_HOME_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }
}
