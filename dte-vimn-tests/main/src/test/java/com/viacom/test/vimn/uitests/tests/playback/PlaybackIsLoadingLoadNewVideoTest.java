package com.viacom.test.vimn.uitests.tests.playback;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PlaybackIsLoadingLoadNewVideoTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertsChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;

	// Declare data
	PropertyResult seriesWithEpisodes;
	LongformResult secondEpisodeToPlay;
	String seriesTitle;
	Integer numOfSwipes;
	String secondEpisodeToPlayTitle;
	
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public PlaybackIsLoadingLoadNewVideoTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertsChain = new AlertsChain();
		loginChain = new LoginChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();

		HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		PropertyResults allSeriesWithEpisodes = propertyFilter.withEpisodes().withMinEpisodesCount(2).propertyFilter();
		seriesWithEpisodes = allSeriesWithEpisodes.get(0);
		
		seriesTitle = seriesWithEpisodes.getPropertyTitle();
		numOfSwipes = seriesWithEpisodes.getNumOfSwipes();
		
		secondEpisodeToPlay = seriesWithEpisodes.getEpisodes().get(1);
		secondEpisodeToPlayTitle = secondEpisodeToPlay.getEpisodeTitle();
	}

	@TestCaseId("34892")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackIsLoadingLoadNewVideoAndroidTest() {

		ProxyUtils.disableAds();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();
		home.enterSeriesView(seriesTitle, numOfSwipes);
		videoPlayer.progressSpinner().waitForVisible();
		series.scrollDownToEpisodeTtl(secondEpisodeToPlayTitle);
		series.episodePlayBtn(secondEpisodeToPlayTitle).waitForVisible().tap();
		videoPlayer.verifyEpisodeIsPlaying(secondEpisodeToPlayTitle);
	}

	@TestCaseId("34892")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackIsLoadingLoadNewVideoiOSTest() {

		ProxyUtils.disableAds();

		// Action chains
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
        alertsChain.dismissAlerts();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numOfSwipes);

        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.scrollDownToEpisodeTtl(secondEpisodeToPlayTitle);
		series.episodePlayBtn(secondEpisodeToPlayTitle).waitForVisible().tap();
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
	}
}