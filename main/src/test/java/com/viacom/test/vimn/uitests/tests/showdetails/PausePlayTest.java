package com.viacom.test.vimn.uitests.tests.showdetails;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.proxy.ProxyUtils;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
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
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class PausePlayTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
    SelectSeasonChain selectSeasonChain;
	Series series;
	Home home;
	VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    LongformResult firstEpisode;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PausePlayTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	autoPlayChain = new AutoPlayChain();
        selectSeasonChain = new SelectSeasonChain();
    	series = new Series();
    	home = new Home();
    	videoPlayer = new VideoPlayer();
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

        firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

    }

    @TestCaseId("10896")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void pausePlayAndroidTest() {

        ProxyUtils.disableTve();

    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();
        
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.seriesTtl(seriesTitle).waitForViewLoad();
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.tapEpisodePlayBtn(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.tapEpisodePauseBtn(episodeTitle);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }

    @TestCaseId("10896")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void pausePlayiOSTest() {

        ProxyUtils.disableAds();
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
    	selectSeasonChain.navigateToSeason(episodeSeasonNumber);
    	series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        series.tapEpisodePlayBtn(episodeTitle);
    	videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.tapEpisodePauseBtn(episodeTitle);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
    }
}