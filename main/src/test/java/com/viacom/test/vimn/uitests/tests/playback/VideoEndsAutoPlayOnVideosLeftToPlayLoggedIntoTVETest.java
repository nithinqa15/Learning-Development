package com.viacom.test.vimn.uitests.tests.playback;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;

import com.viacom.test.vimn.common.filters.Order;
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
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class VideoEndsAutoPlayOnVideosLeftToPlayLoggedIntoTVETest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	VideoPlayer videoPlayer;

	// Declare data
	String seriesTitle;
	Integer numOfSwipes;
	String mostRecentEpisodeTitle;
	String episodeThatIsPlayedFirstEpisodeTitle;
	
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public VideoEndsAutoPlayOnVideosLeftToPlayLoggedIntoTVETest(String runParams) {
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
		videoPlayer = new VideoPlayer();

		HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        PropertyResults allSeriesWithEpisodes = propertyFilter.withEpisodes().withMinEpisodesCount(2).propertyFilter();
		PropertyResult seriesWithEpisodes = allSeriesWithEpisodes.get(1);
        LongformResults episodes = seriesWithEpisodes.getEpisodes();
		LongformResult episodeThatIsPlayedFirst = seriesWithEpisodes.getEpisodes().get(0);
		episodes.orderBy(Order.DESC, episodes, Order.episodeOriginalAirDateTimestamp);

		seriesTitle = seriesWithEpisodes.getPropertyTitle();
		numOfSwipes = seriesWithEpisodes.getNumOfSwipes();
		episodeThatIsPlayedFirstEpisodeTitle = episodeThatIsPlayedFirst.getEpisodeTitle();
		mostRecentEpisodeTitle = episodes.get(1).getEpisodeTitle();
	}

	@TestCaseId("34882")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void videoEndsAutoPlayOnVideosLeftToPlayLoggedIntoTVEAndroidTest() {

		ProxyUtils.disableAds();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();

		home.enterSeriesView(seriesTitle, numOfSwipes);
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.getToEndOfVideo();
		if(!episodeThatIsPlayedFirstEpisodeTitle.equals(mostRecentEpisodeTitle)){
			videoPlayer.verifyEpisodeIsPlaying(mostRecentEpisodeTitle);
		}
	}

	@TestCaseId("34882")
	@Features( GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.MTV_DOMESTIC_APP,
				ParamProps.TVLAND_APP, ParamProps.BET_INTL_APP, ParamProps.BET_DOMESTIC_APP,
				ParamProps.CC_INTL_APP, ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void videoEndsAutoPlayOnVideosLeftToPlayLoggedIntoTVEiOSTest() {

		ProxyUtils.disableAds();

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();

		// Enters series detail screen for a series with episodes
		home.enterSeriesView(seriesTitle, numOfSwipes);

		// Verifies video begins playback
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrubs to the end of the video
		videoPlayer.getToEndOfVideo();

		// Verifies the next episode in the list begins playback
        videoPlayer.verifyEpisodeIsPlaying(mostRecentEpisodeTitle);
	}
}
