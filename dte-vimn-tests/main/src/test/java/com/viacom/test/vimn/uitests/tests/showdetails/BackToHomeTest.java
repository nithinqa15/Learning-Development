package com.viacom.test.vimn.uitests.tests.showdetails;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class BackToHomeTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
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
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public BackToHomeTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
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
        episodeSeasonNumber = episodeResult.getEpisodeSeasonNumber();
        numberOfSwipes = propertyResult.getEpisodes().size();

    }

    @TestCaseId("")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void backToHomeAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.episodeTtl(episodeTitle).waitForScrolledTo();
        
        series.episodeTtl(episodeTitle).waitForScrolledTo().goBack();
        home.seriesThumbBtn(seriesTitle).waitForVisible();
        
    }

    @TestCaseId("")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS})
    public void backToHomeiOSTest() {

    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForScrolledTo();
        //LANDSCAPE_LEFT & PORTRAIT_UPRIGHT method not implemented yet
        series.backBtn().waitForVisible().rotateScreen(ScreenOrientation.LANDSCAPE).pause(StaticProps.XLARGE_PAUSE)
            .rotateScreen(ScreenOrientation.PORTRAIT).waitForVisible().tap();
        home.seriesThumbBtn(seriesTitle).waitForVisible();
        
    }
    
}
