package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_VIDEO_PLAY_BEGIN;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.CellularToggleChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.ProgressBar;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmniturePlayFromBeginningCallTest extends BaseTest{

	//Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	LoginChain loginChain;
	SettingsChain settingsChain;
	Home home;
	CellularToggleChain cellularToggleChain;
	AutoPlayChain autoPlayToggleChain;
	SelectSeasonChain selectSeasonChain;
	AppDataFeed appDataFeed;
	Settings settings;
	ProgressBar progressBar;
	VideoPlayer videoPlayer;
	SettingsMenu settingsMenu;
	Series series;
	HomePropertyFilter propertyFilter;

	//Declare data
	Map<String, String> expectedMap;
	Map<String, String> actualMap;
    String episodeTitle;
    Integer episodeSeason;
    String firstSeriesTitle;
    String lastSeriesTitle;
    Integer numberOfSwipes;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmniturePlayFromBeginningCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertschain = new AlertsChain();
		selectSeasonChain = new SelectSeasonChain();
		loginChain = new LoginChain();
		cellularToggleChain = new CellularToggleChain();
		autoPlayToggleChain = new AutoPlayChain();
		settingsChain = new SettingsChain();
	    videoPlayer = new VideoPlayer();
        progressBar = new ProgressBar();
		appDataFeed = new AppDataFeed();
		settings = new Settings();
		home = new Home();
		series = new Series();
		settingsMenu = new SettingsMenu();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		expectedMap = new HashMap<>();
		
        PropertyResult firstSeries = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        firstSeriesTitle = firstSeries.getPropertyTitle();

        PropertyResult lastSeries = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getLastProperty();
        lastSeriesTitle = lastSeries.getPropertyTitle();
        numberOfSwipes = lastSeries.getNumOfSwipes();
        episodeTitle = firstSeries.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode().getEpisodeTitle();
        episodeSeason = firstSeries.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode().getEpisodeSeasonNumber();
	}

	@TestCaseId("35901-35895") // Also covers 60340b31-734f-4646-a711-a6ea009a7c41
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_PLAYER, GroupProps.OMNITURE })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omniturePlayFromBeginningCallAndroidTest() {

		//TO-DO
		
	}
	
	@TestCaseId("35901-35895") // Also covers 60340b31-734f-4646-a711-a6ea009a7c41
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER, GroupProps.OMNITURE  })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omniturePlayFromBeginningCalliOSTest() {

		ProxyUtils.disableAds();
		
        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoPlayToggleChain.enableAutoPlay();

        home.enterSeriesView(firstSeriesTitle, numberOfSwipes);
        series.switchToFullEpisodes();
        series.scrollUpToSeriesTitle(firstSeriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeason);
        series.scrollDownToEpisodeTtl(episodeTitle);
		series.episodeTtl(episodeTitle).waitForScrolledTo().pause(15000);
		series.backBtn().waitForVisible().tap();
        home.flickRightToSeriesThumb(firstSeriesTitle, numberOfSwipes);
        home.progressBar(firstSeriesTitle).waitForVisible();
	    
	    //Video play from beginning on portrait mode
		home.seriesThumbBtn(firstSeriesTitle).waitForVisible().tap();
		series.switchToFullEpisodes();
		progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForVisible().tap();
        progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent(); // pause added to delay until loading spinner displays
        
		expectedMap = OmnitureLinkData.buildVideoPlayFromBeginExpectedMap(firstSeriesTitle, episodeTitle);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_VIDEO_PLAY_BEGIN);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		
		actualMap.clear();
		
		//Video play from beginning on landscape mode - Broken
		/*ProxyUtils.clearNetworkLogs();
		settingsMenu.backBtn().waitForVisible().tap();
		home.seriesThumbBtn(firstSeriesTitle).waitForVisible().tap().rotateScreen(ScreenOrientation.LANDSCAPE);
		progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent();
        videoPlayer.playFromBeginningBtn().waitForVisible().tap();
        progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent(); // pause added to delay until loading spinner displays

		expectedMap = OmnitureLinkData.buildVideoPlayFromBeginExpectedMap(firstSeriesTitle, episodeTitle);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_VIDEO_PLAY_BEGIN);
		Omniture.assertOmnitureValues(expectedMap, actualMap); */
	}
}