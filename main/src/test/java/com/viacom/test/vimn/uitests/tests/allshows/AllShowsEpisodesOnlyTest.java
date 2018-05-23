package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithEpisodesOnlyException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsEpisodesOnlyTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    AllShows    allShows;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    AllShowsPropertyFilter allShowsPropertyFilter;

    // Declare data
    String episodesOnlySeriesTitle = null;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsEpisodesOnlyTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        allShows = new AllShows();
        series  = new Series();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = allShowsPropertyFilter.withEpisodes().withEpisodesOnly().propertyFilter().getFirstProperty();
        episodesOnlySeriesTitle = propertyResult.getPropertyTitle();

    }

    @TestCaseId("10876")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS }) //TODO - https://jira.mtvi.com/browse/VAA-4187
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void allShowsEpisodesOnlyAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        if (episodesOnlySeriesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(episodesOnlySeriesTitle);

            series.watchXtrasBtn().waitForNotPresentOrVisible();
            series.fullEpisodesBtn().waitForNotPresentOrVisible();

            series.scrollUpToSeriesTitle(episodesOnlySeriesTitle);
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that only have episodes so skipping execution";
            Logger.logMessage(message);
            throw new NoSeriesWithEpisodesOnlyException(message);
        }
    }
    
    @TestCaseId("10876")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS  })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS})
    public void AllShowsEpisodesOnlyiOSTest() {
    	
        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        if (episodesOnlySeriesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(episodesOnlySeriesTitle);

            series.watchXtrasBtn().waitForNotPresentOrVisible();
            series.fullEpisodesBtn().waitForNotPresentOrVisible();

            series.scrollUpToSeriesTitle(episodesOnlySeriesTitle);
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that only have episodes so skipping execution";
            Logger.logMessage(message);
            throw new NoSeriesWithEpisodesOnlyException(message);
        }
     }
  }
