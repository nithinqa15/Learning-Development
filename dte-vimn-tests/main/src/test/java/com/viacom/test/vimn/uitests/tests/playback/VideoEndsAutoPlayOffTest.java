package com.viacom.test.vimn.uitests.tests.playback;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.common.filters.LongformResult;
import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class VideoEndsAutoPlayOffTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	HomePropertyFilter propertyFilter;

	// Declare data
	PropertyResult seriesWithPublicEpisodes;
	String seriesTitle;
	Integer numberOfSwipes;
	String episodeTitle;
	LongformResult firstEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public VideoEndsAutoPlayOffTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		alertsChain = new AlertsChain();
		chromecastChain = new ChromecastChain();
		loginChain = new LoginChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numberOfSwipes = propertyResult.getNumOfSwipes();

		LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
		episodeTitle = episodeResult.getEpisodeTitle();

		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

	}

	@TestCaseId("34883")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = {GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void videoEndsAutoPlayOffAndroidTest() {

		ProxyUtils.disableAds();
		ProxyUtils.disableTve();

		// Chain actions
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.disableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesTitle,numberOfSwipes);


		// Verify series details page opens for the selected series
		series.scrollUpToSeriesTitle(seriesTitle);


		// Verify episode starts playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrub to the end of the episode
		videoPlayer.getToEndOfVideo();

		// Video should end and verify that the series detail page is shown
		series.scrollUpToSeriesTitle(seriesWithPublicEpisodes.getPropertyTitle());
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

	}

	@TestCaseId("34883")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void videoEndsAutoPlayOffiOSTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.disableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesWithPublicEpisodes.getPropertyTitle(), seriesWithPublicEpisodes.getNumOfSwipes());

		// Verify series details page opens for the selected series
		series.scrollUpToSeriesTitle(seriesWithPublicEpisodes.getPropertyTitle());

		/// Verify episode starts playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrub to the end of the episode
		videoPlayer.getToEndOfVideo();

		// Verify video does not begin playback again, and verify that the series detail page is still shown
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

	}
}
