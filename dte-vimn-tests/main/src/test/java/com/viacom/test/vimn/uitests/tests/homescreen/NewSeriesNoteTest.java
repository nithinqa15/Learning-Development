package com.viacom.test.vimn.uitests.tests.homescreen;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoNewSeriesException;
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

public class NewSeriesNoteTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Home home;
    Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;

    //Declare data
    String seriesTitleWNote = null;
    String episodeTitle;
    Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NewSeriesNoteTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = homePropertyFilter.withNewSeries().propertyFilter().getFirstProperty();
        seriesTitleWNote = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

    }

    @TestCaseId("11934")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void newSeriesNoteAndroidTest() {

        if (seriesTitleWNote != null) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();

            home.flickRightToSeriesThumb(seriesTitleWNote, numberOfSwipes);
            home.newSeriesBtn().waitForVisible().tap();
            series.scrollUpToSeriesTitle(seriesTitleWNote);
        } else {
            String message = "A series with a New Series Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoNewSeriesException(message);
        }
    }

    @TestCaseId("11934")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void newSeriesNoteiOSTest() {

        if (seriesTitleWNote == null) {
            String message = "A series with a New Series Note was not returned from the data feed.";
            Logger.logMessage(message);
            throw new NoNewSeriesException(message);
        } else {
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            home.enterNewSeries(seriesTitleWNote, numberOfSwipes);

            if (series.fullEpisodesBtn().waitForViewLoad().isVisible()) {
                series.fullEpisodesBtn().waitForVisible().tap();
            }
            series.seriesTtl(seriesTitleWNote).waitForViewLoad().waitForPresent();
        }
    }
}
