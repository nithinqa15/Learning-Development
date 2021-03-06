package com.viacom.test.vimn.uitests.tests.homescreen;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
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
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class BackgroundTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter homePropertyFilter;
    
    //Declare data
    String seriesTitle;
    Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public BackgroundTest(String runParams) {
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

        PropertyResult propertyResult = homePropertyFilter.withEpisodes().withEpisodesOnly().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

    }
    
    @TestCaseId("51559")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void backgroundAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.seriesTtl(seriesTitle).background(5);
        home.seriesTtl(seriesTitle).waitForVisible();
        home.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForVisible();

    }

    @TestCaseId("51559")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void backgroundiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        chromecastChain.dismissChromecast();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        DriverManager.getIOSDriver().runAppInBackground(5);
        home.seriesTtl(seriesTitle).waitForVisible();
        home.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForVisible();


    }
    
}
