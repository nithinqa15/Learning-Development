package com.viacom.test.vimn.uitests.tests.playback;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.uitests.actionchains.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PlaybackIsOnPauseLoadNewVideoTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	LoginChain loginChain;
    AlertsChain alertsChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;

	// Declare data
	String seriesTitle;
	Integer numberOfSwipes;
	String firstEpisodeTitle;
	String secondEpisodeTitle;
	LongformResult firstEpisode;
	LongformResult secondEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public PlaybackIsOnPauseLoadNewVideoTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        loginChain = new LoginChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = propertyFilter.withEpisodes().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numberOfSwipes = propertyResult.getNumOfSwipes();

		firstEpisode = propertyResult.getLongformFilter().withPrivateEpisodes().episodesFilter().getFirstEpisode();
		secondEpisode = propertyResult.getLongformFilter().withPrivateEpisodes().episodesFilter().getSecondEpisode();
		firstEpisodeTitle = firstEpisode.getEpisodeTitle();
		secondEpisodeTitle = secondEpisode.getEpisodeTitle();

	}

	@TestCaseId("34891")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackIsOnPauseLoadNewVideoAndroidTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest(); // Waits for the splash/launch screen to be over
		chromecastChain.dismissChromecast(); // Dismisses chromecast alert
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesTitle, numberOfSwipes);
		
		// Verify the first episode to play is playing 
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(firstEpisodeTitle);
		
		// Pause the episode
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.smallPauseBtn().waitForVisible().tap();
		
		// Verify the video is paused
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
		
		// Play the second episode 
		series.scrollDownToEpisodeTtl(secondEpisodeTitle);
		series.tapEpisodePlayBtn(secondEpisodeTitle);
		
		// Verify the second episode to play is playing 
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(secondEpisodeTitle);

	}

	@TestCaseId("34891")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackIsOnPauseLoadNewVideoiOSTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();
        alertsChain.dismissAlerts();

		// Open a series with episodes
        home.enterSeriesView(seriesTitle, numberOfSwipes);

		// Verify episode is playing
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(firstEpisodeTitle);

        // Pause the episode and verify it paused
        videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.smallPauseBtn().waitForVisible().tap();
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

		// Play the second episode and verify it beings playing
		series.scrollDownToEpisodeTtl(secondEpisodeTitle);
		series.tapEpisodePlayBtn(secondEpisodeTitle);
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().exposeControlRack();
		videoPlayer.videoTitle().waitForVisible().getMobileElement().getText().contains(secondEpisodeTitle);

	}
}