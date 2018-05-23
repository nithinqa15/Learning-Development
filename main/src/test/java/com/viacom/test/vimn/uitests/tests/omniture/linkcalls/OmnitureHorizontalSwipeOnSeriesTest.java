package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HORIZONTAL_SWIPE;

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
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureHorizontalSwipeOnSeriesTest extends BaseTest {

    OmnitureLinkData omnitureLinkData;
    Home home;
    Series series;
    SplashChain splashChain;
    HomePropertyFilter propertyFilter;
    SettingsChain settingsChain;

    String seriesTitle;
    String nextSeriesTitle;
    Integer numberOfSwipes;
    String episodeTitle;
    Map<String, String> expectedMap;
    Map<String, String> dynamicMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureHorizontalSwipeOnSeriesTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        omnitureLinkData = new OmnitureLinkData();
        expectedMap = new HashMap<>();
        home = new Home();
        series = new Series();
        splashChain = new SplashChain();
        dynamicMap = new HashMap<>();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        settingsChain = new SettingsChain();

        PropertyResults propertyResults = propertyFilter.withEpisodes().propertyFilter();

        PropertyResult propertyResult = propertyResults.getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        PropertyResult nextPropertyResult = propertyResults.get(1);
        nextSeriesTitle = nextPropertyResult.getPropertyTitle();
        episodeTitle = nextPropertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode()
                .getEpisodeTitle();

        dynamicMap.put("seriesTitle", seriesTitle);
        dynamicMap.put("nextSeriesTitle", nextSeriesTitle);
        dynamicMap.put("episodeTitle", episodeTitle);

    }

    @Deprecated // VB - 9/22/17 - Swipe between series/clips functionality was removed
                // with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureHorizontalSwipeOnSeriesAndroidTest() {

        splashChain.splashAtRest();

        String version = settingsChain.getVersion();
        dynamicMap.put("version", version);

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.seriesTtl(nextSeriesTitle).flickToNextSeriesFromSeriesView().waitForNotPresentOrVisible();
        series.scrollUpToSeriesTitle(nextSeriesTitle);

        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HORIZONTAL_SWIPE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }

    @Deprecated
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureHorizontalSwipeOnSeriesiOSTest() {

        splashChain.splashAtRest();

        String version = settingsChain.getVersion();
        dynamicMap.put("version", version);

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.seriesTtl(nextSeriesTitle).flickToNextSeriesFromSeriesView();
        series.scrollUpToSeriesTitle(nextSeriesTitle);

        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HORIZONTAL_SWIPE);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
