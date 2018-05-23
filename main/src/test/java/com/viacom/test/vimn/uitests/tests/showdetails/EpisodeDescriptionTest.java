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
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
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

public class EpisodeDescriptionTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
    AutoPlayChain autoPlayChain;
    SelectSeasonChain selectSeasonChain;
	Home home;
	Series series;
	AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String seriesTitle;
    String episodeTitle;
    String episodeDescription;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public EpisodeDescriptionTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
        autoPlayChain = new AutoPlayChain();
        selectSeasonChain = new SelectSeasonChain();
    	home = new Home();
    	series = new Series();
    	appDataFeed = new AppDataFeed();
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

        episodeTitle = episodeResult.getEpisodeTitle();
        episodeDescription = episodeResult.getEpisodeDescription();
        episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();
    }

    @TestCaseId("10911")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void episodeDescriptionAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.tapEpisodeTitle(episodeTitle);
        series.verifyEpisodeDescriptionIsVisible(episodeTitle, episodeDescription);
    }

    @TestCaseId("10911")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void episodeDescriptioniOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        if (!series.episodeTtl(episodeTitle).isVisible()) {
        	series.scrollDownToEpisodeTtl(episodeTitle);
        }
        series.episodeTtl(episodeTitle).waitForVisible().tap();
        series.verifyEpisodeDescriptionIsVisible(episodeTitle, episodeDescription);
    }
    
}
