package com.viacom.test.vimn.uitests.tests.playback;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.uitests.actionchains.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.common.filters.LongformResults;
import com.viacom.test.vimn.common.BaseTest;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;

import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PlaybackIsActiveLoadingNewVideoTest extends BaseTest {

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
	String episodeTitle;
	String secondEpisodeTitle;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public PlaybackIsActiveLoadingNewVideoTest(String runParams) {
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

        // Get a series with episodes and at least 2 public episodes
        HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numberOfSwipes = propertyResult.getNumOfSwipes();

		LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
		episodeTitle = episodeResult.getEpisodeTitle();

		LongformResult secondEpisodeResult = propertyResult.getLongformFilter().episodesFilter().getSecondEpisode();
		secondEpisodeTitle = secondEpisodeResult.getEpisodeTitle();
	}

	@TestCaseId("34890")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackIsActiveLoadingNewVideoAndroidTest() {

		ProxyUtils.disableAds();

		// Chain actions
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		autoPlayChain.enableAutoPlay();

		// Open a series
		home.enterSeriesView(seriesTitle, numberOfSwipes);
		series.scrollUpToSeriesTitle(seriesTitle);
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Play the second episode
		series.scrollDownToEpisodeTtl(secondEpisodeTitle);
		series.tapEpisodePlayBtn(secondEpisodeTitle);;
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);


	}

    @TestCaseId("34890")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
		    	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
		    	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
		    	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
		    	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void playbackIsActiveLoadingNewVideoiOSTest() {

		ProxyUtils.disableAds();

        // Chain actions
        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();
        autoPlayChain.enableAutoPlay();
        alertsChain.dismissAlerts();

        // Open a series with at least two public episodes
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);

        // Verify episode beings playback
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        // Play the second episode and verify it beings playing
        series.scrollDownToEpisodeTtl(secondEpisodeTitle);
        series.tapEpisodePlayBtn(secondEpisodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }
}
