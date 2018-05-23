package com.viacom.test.vimn.uitests.tests.allshows;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.ShortformResult;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsOnlyException;
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

public class AllShowsClipsOnlyTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    AllShows    allShows;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    AllShowsPropertyFilter allShowsPropertyFilter;
    Clips clips;

    // Declare data
    String clipsOnlySeriesTitle = null;
    String clipTitle;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsClipsOnlyTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        allShows = new AllShows();
        series  = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);
        clips = new Clips();

        PropertyResult propertyResult = allShowsPropertyFilter.withClips().withClipsOnly().propertyFilter().getFirstProperty();
        clipsOnlySeriesTitle = propertyResult.getPropertyTitle();

        ShortformResult clipResult = propertyResult.getClips().getFirstClip();
        clipTitle = clipResult.getClipTitle();

    }

    @TestCaseId("10880")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS })
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void allShowsClipsOnlyAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        if (clipsOnlySeriesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(clipsOnlySeriesTitle);

            series.watchXtrasBtn().waitForNotPresentOrVisible();
            series.fullEpisodesBtn().waitForNotPresentOrVisible();

            clips.clipTitle(clipTitle).waitForVisible();
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that only have clips so skipping execution";
            Logger.logMessage(message);
            throw new NoSeriesWithClipsOnlyException(message);
        }
    }

    @TestCaseId("10880")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS  })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS})
    public void allShowsClipsOnlyiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        
        if (clipsOnlySeriesTitle != null) {
            allShows.enterAllShows();
            allShows.enterSeriesView(clipsOnlySeriesTitle);

            series.watchXtrasBtn().waitForNotPresentOrVisible();
            series.fullEpisodesBtn().waitForNotPresentOrVisible();

            clips.clipTitle(clipTitle).waitForVisible();
        } else {
            String message = "There are no series in " + TestRun.getAppName() + " in region " + TestRun.getLocale() + " that only have clips so skipping execution";
            Logger.logMessage(message);
            throw new NoSeriesWithClipsOnlyException(message);
        }
    }
}
