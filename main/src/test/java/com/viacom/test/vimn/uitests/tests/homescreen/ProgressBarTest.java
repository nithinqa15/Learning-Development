package com.viacom.test.vimn.uitests.tests.homescreen;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ProgressBarTest extends BaseTest {

    //Declare page objects/actions
	AutoPlayChain autoPlayChain;
    SplashChain splashChain;
    SelectSeasonChain selectSeasonChain;
	Home home;
	Series series;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String episodeTitle;
    Integer episodeSeason;
    String firstSeriesTitle;
    String lastSeriesTitle;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ProgressBarTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        autoPlayChain = new AutoPlayChain();
    	splashChain = new SplashChain();
        selectSeasonChain = new SelectSeasonChain();
    	home = new Home();
    	series = new Series();
        videoPlayer = new VideoPlayer();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        
        PropertyResult firstSeries = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();

        PropertyResult lastSeries = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getLastProperty();
        lastSeriesTitle = lastSeries.getPropertyTitle();
        numberOfSwipes = lastSeries.getNumOfSwipes();
        episodeTitle = firstSeries.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode().getEpisodeTitle();
        episodeSeason = firstSeries.getEpisodes().getFirstEpisode().getEpisodeSeasonNumber();
    }

    @TestCaseId("51716")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void progressBarAndroidTest() {

        ProxyUtils.disableAds();

    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(firstSeriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeason);
        series.tapEpisodePlayBtn(episodeTitle);
        series.episodeTtl(episodeTitle).waitForScrolledTo().pause(50000);
        series.backBtn().waitForPresent().tap();
        home.seriesThumbBtn(firstSeriesTitle).waitForVisible();
        home.flickRightToSeriesThumb(firstSeriesTitle, numberOfSwipes);
        home.resumeWatchingLbl().waitForVisible().isPresent();
        home.progressBar(firstSeriesTitle).waitForVisible();
  
    }

    @TestCaseId("51716")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void progressBariOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(firstSeriesTitle, numberOfSwipes);
        series.switchToFullEpisodes();
        series.scrollUpToSeriesTitle(firstSeriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeason);
        series.scrollDownToEpisodeTtl(episodeTitle);
		series.episodeTtl(episodeTitle).waitForScrolledTo().pause(45000);
		series.backBtn().waitForVisible().tap();
        home.flickRightToSeriesThumb(firstSeriesTitle, numberOfSwipes);
        home.progressBar(firstSeriesTitle).waitForVisible();
    }
}