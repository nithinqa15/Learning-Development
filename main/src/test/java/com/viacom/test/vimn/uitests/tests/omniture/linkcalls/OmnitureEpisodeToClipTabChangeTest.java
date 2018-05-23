package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
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
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureEpisodeToClipTabChangeTest extends BaseTest {

    Home home;
    Clips clips;
    Series series;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    SettingsChain settingsChain;
    HomePropertyFilter propertyFilter;
    AlertsChain alertsChain;

    String seriesTtl;
    Integer numberOfSwipes;
    String clipTtl;
    String version;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureEpisodeToClipTabChangeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        home = new Home();
        clips = new Clips();
        series = new Series();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        expectedMap = new HashMap<>();
        settingsChain = new SettingsChain();
        alertsChain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();

        seriesTtl = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        clipTtl = propertyResult
                .getClips()
                .getFirstClip()
                .getClipTitle();
    }

    @TestCaseId("35910")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureEpisodeToClipTabChangeAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        version = settingsChain.getVersion();

        home.enterSeriesView(seriesTtl, numberOfSwipes);

        series.scrollUpToSeriesTitle(seriesTtl);
        series.switchToClips();

        clips.clipTitle(clipTtl).waitForVisible();

        expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(seriesTtl, version, clipTtl);
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }
    
    @TestCaseId("35910")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureEpisodeToClipTabChangeiOSTest() {

        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        home.enterSeriesView(seriesTtl, numberOfSwipes);

        series.switchToFullEpisodes();
        series.switchToClips();
        clips.clipTitle(clipTtl).waitForVisible();

        expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(seriesTtl, null, clipTtl);
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
