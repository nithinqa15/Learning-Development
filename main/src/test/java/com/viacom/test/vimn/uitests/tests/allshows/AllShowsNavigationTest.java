package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsNavigationTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    AllShows    allShows;
    Series series;
    Clips clips;
    Home home;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    AllShowsPropertyFilter allShowsPropertyFilter;

    // Declare Data
    String clipsAndEpisodesTitle = null;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsNavigationTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        allShows = new AllShows();
        series = new Series();
        clips = new Clips();
        home = new Home();
        chromecastChain = new ChromecastChain();
        alertsChain = new AlertsChain();
        allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = allShowsPropertyFilter
                .withEpisodes()
                .withClips()
                .propertyFilter()
                .getFirstProperty();

        clipsAndEpisodesTitle = propertyResult.getPropertyTitle();
    }

    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void allShowsNavigationAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        if (clipsAndEpisodesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(clipsAndEpisodesTitle);

            series.scrollUpToSeriesTitle(clipsAndEpisodesTitle);
            series.watchXtrasBtn().waitForVisible().tap();

            clips.backBtn().waitForVisible().tap();

            allShows.enterSeriesView(clipsAndEpisodesTitle);

            series.fullEpisodesBtn().waitForVisible().tap();
            series.scrollUpToSeriesTitle(clipsAndEpisodesTitle);

            clips.backBtn().goBack();

            allShows.allShowsBackBtn().waitForVisible();
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that have both clips and episodes so skipping execution";
            Logger.logMessage(message);
            throw new SkipException(message);
        }
    }
    
    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS  })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS})
    public void allShowsNavigationiOSTest() {
    	
        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        if (clipsAndEpisodesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(clipsAndEpisodesTitle);

            series.scrollUpToSeriesTitle(clipsAndEpisodesTitle);
            home.watchExtrasBtn().waitForViewLoad().waitForVisible().tap();
            clips.clipCloseBtn().waitForVisible().tap();
            allShows.allShowsSeriesTitleText(clipsAndEpisodesTitle).waitForViewLoad().waitForVisible().tap();
            series.fullEpisodesBtn().waitForVisible().tap();
            series.scrollUpToSeriesTitle(clipsAndEpisodesTitle);
            series.backBtn().waitForVisible().tap();
            allShows.allShowsBackBtn().waitForVisible();
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that have both clips and episodes so skipping execution";
            Logger.logMessage(message);
            throw new SkipException(message);
        }
    }
}