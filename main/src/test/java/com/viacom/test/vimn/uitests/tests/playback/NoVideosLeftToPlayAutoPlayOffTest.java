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
import com.viacom.test.vimn.common.filters.LongformResults;
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

public class NoVideosLeftToPlayAutoPlayOffTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;

	// Declare data
	PropertyResult seriesWithEpisodes;
	LongformResult lastEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public NoVideosLeftToPlayAutoPlayOffTest(String runParams) {
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

		HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		PropertyResults allSeriesWithEpisodes = propertyFilter.withEpisodes().propertyFilter();

		seriesWithEpisodes = allSeriesWithEpisodes.get(0);
		String seriesOrder = seriesWithEpisodes.getOrder();

		// Gets the last episode for that series
		if (seriesOrder.toLowerCase().contains("asc")) {
			LongformResults allEpisodes = seriesWithEpisodes.getEpisodes();
			lastEpisode = allEpisodes.get(0);
		} else if (seriesOrder.toLowerCase().contains("desc")) {
			LongformResults allEpisodes = seriesWithEpisodes.getEpisodes();
			lastEpisode = allEpisodes.get(allEpisodes.size() - 1);
		}
	}

	@TestCaseId("34884")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void noVideosLeftToPlayAutoPlayOffAndroidTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.disableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesWithEpisodes.getPropertyTitle(), seriesWithEpisodes.getNumOfSwipes());

		// Verify series details page opens for the selected series
		series.scrollUpToSeriesTitle(seriesWithEpisodes.getPropertyTitle());

		// Play the last episode
		series.episodePlayBtn(lastEpisode.getEpisodeTitle()).waitForVisible().tap();

		// Ensure that the video playing is the last episode
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(lastEpisode.getEpisodeTitle());

		// Verify episode starts playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrub to the end of the episode
		videoPlayer.getToEndOfVideo();

		// Video should end and verify that the series detail page is shown
		series.scrollUpToSeriesTitle(seriesWithEpisodes.getPropertyTitle());
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

		// Tap the video player
		videoPlayer.playerCtr().waitForVisible().tap();

		// Verify that the video playing is still the last episode
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(lastEpisode.getEpisodeTitle());

		// Verify that the same episode is playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

	}

	// Kevin Dannenberg: Won't run successfully until the getToEndOfVideo method is added for iOS
	@TestCaseId("34884")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void noVideosLeftToPlayAutoPlayOffiOSTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.disableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesWithEpisodes.getPropertyTitle(), seriesWithEpisodes.getNumOfSwipes());

		// Verify series details page opens for the selected series
		series.scrollUpToSeriesTitle(seriesWithEpisodes.getPropertyTitle());

		// Scroll to the last episode in the list and play it
		series.scrollDownToEpisodeTtl(lastEpisode.getEpisodeTitle());
        series.episodePlayBtn(lastEpisode.getEpisodeTitle()).waitForVisible().tap();

		// Verify episode starts playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrub to the end of the episode
		videoPlayer.getToEndOfVideo();

		// Video should end
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

		// Tap the video player to begin playback of video
		videoPlayer.playerCtr().waitForVisible().tapCenter();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Verify that the video playing is still the last episode
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(lastEpisode.getEpisodeTitle());

	}
}
