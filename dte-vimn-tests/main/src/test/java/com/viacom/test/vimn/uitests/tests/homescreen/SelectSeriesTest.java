package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ru.yandex.qatools.allure.model.SeverityLevel;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class SelectSeriesTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String episodeTitle;
    String firstSeriesTitle;
    String lastSeriesTitle;
    Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SelectSeriesTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        
        PropertyResults propertyResults = propertyFilter.propertyFilter();

        PropertyResult firstSeries = propertyResults.getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();

        PropertyResult lastSeries = propertyResults.getLastProperty();
        lastSeriesTitle = lastSeries.getPropertyTitle();
        numberOfSwipes = lastSeries.getNumOfSwipes();
        episodeTitle = firstSeries.getEpisodes().getFirstEpisode().getEpisodeTitle();
    }

    @TestCaseId("")
    @Features(GroupProps.HOME_SCREEN)
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void selectSeriesAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.enterSeriesView(firstSeriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(firstSeriesTitle);
        series.seriesTtl(firstSeriesTitle).goBack();
        home.seriesThumbBtn(firstSeriesTitle).waitForVisible();

    }

    @TestCaseId("")
    @Features(GroupProps.HOME_SCREEN)
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void selectSeriesiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(firstSeriesTitle, numberOfSwipes);
        series.seriesTtl(firstSeriesTitle).waitForViewLoad().waitForPresent();
    }
}