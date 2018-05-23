package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithNewEpisodeException;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SHOW_DETAILS_VIEW;

public class OmnitureNewEpisodeSelectionCallTest extends BaseTest {

    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    Home home;
    Series series;
    PropertyFilter propertyFilter;

    String seriesTitle;
    Integer numberOfSwipes;
    Boolean seriesHasNewEpisodeRibbon = false;
    String positionOnCarousel;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureNewEpisodeSelectionCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        series = new Series();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertsChain = new AlertsChain();
        expectedMap = new HashMap<>();

        PropertyResult propertyResult = propertyFilter.withNewEpisodes().propertyFilter().getFirstProperty();
        seriesHasNewEpisodeRibbon = propertyResult != null;
        if (seriesHasNewEpisodeRibbon) {
            seriesTitle = propertyResult.getPropertyTitle();
            positionOnCarousel = String.valueOf(propertyResult.getPositionOnCarousel());
            numberOfSwipes = propertyResult.getNumOfSwipes();
        }

    }

    @TestCaseId("35885")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureNewEpisodeSelectionCallAndroidTest() {

        //Test is broken due to https://jira.mtvi.com/browse/VAA-5420
        // TODO - GF - 27/09/2017 validate test and change group to full once https://jira.mtvi.com/browse/VAA-5420 is fixed
        if (seriesHasNewEpisodeRibbon) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();

            home.enterSeriesWithNewEpisode(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);

            expectedMap = OmniturePageData.buildNewEpisodeSelectionCallExpectedMap(seriesTitle, positionOnCarousel);
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
        } else {
            String message = "A Series with a New Episode Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoSeriesWithNewEpisodeException(message);
        }
    }

    @TestCaseId("35885")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureNewEpisodeSelectionCalliOSTest() {

        if (seriesHasNewEpisodeRibbon) {

            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            home.enterSeriesWithNewEpisode(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);

            expectedMap = OmniturePageData.buildNewEpisodeSelectionCallExpectedMap(seriesTitle, positionOnCarousel);
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
        } else {
            String message = "A Series with a New Episode Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoSeriesWithNewEpisodeException(message);
        }
    }
}
