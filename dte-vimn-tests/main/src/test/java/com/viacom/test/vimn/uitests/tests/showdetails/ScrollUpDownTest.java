package com.viacom.test.vimn.uitests.tests.showdetails;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ScrollUpDownTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Home home;
	Series series;
    AppDataFeed appDataFeed;
    SelectSeasonChain selectSeasonChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String seriesTitle;
    String firstEpisode;
    String lastEpisode;
    Integer episodeCount;
    Integer seasonNumber;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ScrollUpDownTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	home = new Home();
    	series = new Series();
        appDataFeed = new AppDataFeed();
        selectSeasonChain = new SelectSeasonChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);


        PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();
        
        LongformResult episodeResult = propertyResult
                .getLongformFilter()
                .withPublicEpisodesOnly()
                .episodesFilter()
                .getFirstEpisode();

        firstEpisode = episodeResult.getEpisodeTitle();
        seasonNumber = episodeResult.getEpisodeSeasonNumber();
        
    }

    @TestCaseId("10906")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void scrollUpDownAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(seasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.episodeTtl(lastEpisode).waitForScrolledTo();
        series.scrollUpToSeriesTitle(seriesTitle);
    }

    @TestCaseId("10906")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void scrollUpDowniOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        selectSeasonChain.navigateToSeason(seasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        if (!series.episodeTtl(firstEpisode).isVisible()) {
        	 series.scrollDownToEpisodeTtl(firstEpisode);
        }
        series.scrollUpToSeriesTitle(seriesTitle);
    }

}
