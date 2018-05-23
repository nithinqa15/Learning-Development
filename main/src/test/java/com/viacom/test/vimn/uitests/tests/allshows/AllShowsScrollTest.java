package com.viacom.test.vimn.uitests.tests.allshows;

import java.util.Collections;
import java.util.List;

import com.viacom.test.vimn.common.filters.AllShowsPropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResults;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class AllShowsScrollTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    ChromecastChain chromecastchain;
    AllShows allShows;
    Series series;
    AlertsChain alertsChain;
    AllShowsPropertyFilter allShowsPropertyFilter;

    // Declare Data
    String firstSeriesTitle;
    String lastSeriesTitle;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AllShowsScrollTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        chromecastchain = new ChromecastChain();
        allShows = new AllShows();
        series = new Series();
        alertsChain = new AlertsChain();
        allShowsPropertyFilter = new AllShowsPropertyFilter(NON_PAGINATED);

        PropertyResults propertyResults = allShowsPropertyFilter.propertyFilter();
        List<String> seriesTitles = propertyResults.getPropertyTitles();
        Collections.sort(seriesTitles);
        firstSeriesTitle = seriesTitles.get(0);
        lastSeriesTitle = seriesTitles.get(seriesTitles.size()-1);

    }

    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = {GroupProps.FULL, GroupProps.ALL_SHOWS})
    @Parameters({ParamProps.ANDROID, ParamProps.ALL_APPS})
    public void allShowsScrollAndroidTest() {

        splashChain.splashAtRest();
        chromecastchain.dismissChromecast();

        allShows.enterAllShows();

        allShows.scrollDownToSeriesTile(lastSeriesTitle);
        allShows.scrollUpToSeriesTile(firstSeriesTitle);
    }
    
    @TestCaseId("")
    @Features(GroupProps.ALL_SHOWS)
    @Test(groups = {GroupProps.FULL, GroupProps.ALL_SHOWS })
    @Parameters({ParamProps.IOS, ParamProps.ALL_APPS})
    public void allShowsScrolliOSTest() {
    	
        splashChain.splashAtRest();
        alertsChain.dismissAlerts();

        allShows.enterAllShows();

        allShows.scrollDownToSeriesTile(lastSeriesTitle);
        allShows.scrollUpToSeriesTile(firstSeriesTitle);
    }
}
