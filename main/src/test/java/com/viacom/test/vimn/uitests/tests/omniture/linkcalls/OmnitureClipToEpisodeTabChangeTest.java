package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CLIP_TO_EPISODE_TAB_CHANGE;

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

public class OmnitureClipToEpisodeTabChangeTest extends BaseTest {

    Home home;
    Clips clips;
    Series series;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    SettingsChain settingsChain;
    HomePropertyFilter propertyFilter;

    String seriesTtl;
    Integer numberOfSwipes;
    String clipTtl;
    String version;
    String episodeTtl;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureClipToEpisodeTabChangeTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        home = new Home();
        clips = new Clips();
        series = new Series();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        expectedMap = new HashMap<>();
        settingsChain = new SettingsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();

        seriesTtl = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        clipTtl = propertyResult
                .getClips()
                .getFirstClip()
                .getClipTitle();
        episodeTtl = propertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode()
                .getEpisodeTitle();

    }

    @TestCaseId("35909")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureClipToEpisodeTabChangeAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        version = settingsChain.getVersion();

        home.flickRightToSeriesThumb(seriesTtl, numberOfSwipes);
        home.watchExtrasBtn().waitForVisible().tap();

        clips.clipTitle(clipTtl).waitForVisible();
        series.switchToFullEpisodes();

        series.scrollUpToSeriesTitle(seriesTtl);

        expectedMap = OmnitureLinkData.buildClipToEpisodeTabChangeExpectedMap(seriesTtl, version, episodeTtl);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_TO_EPISODE_TAB_CHANGE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
    
    @TestCaseId("35909")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureClipToEpisodeTabChangeiOSTest() {

        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        home.flickRightToSeriesThumb(seriesTtl, numberOfSwipes);
        home.watchExtrasBtn().waitForVisible().tap();

        clips.clipTitle(clipTtl).waitForVisible();
        series.switchToFullEpisodes();

        series.scrollUpToSeriesTitle(seriesTtl);

        expectedMap = OmnitureLinkData.buildClipToEpisodeTabChangeExpectedMap(seriesTtl, null, episodeTtl);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_TO_EPISODE_TAB_CHANGE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
