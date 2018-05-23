package com.viacom.test.vimn.uitests.tests.playback;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class NotLoggedInWithTVETest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	VideoPlayer videoPlayer;
	Series series;
	HomePropertyFilter propertyFilter;


	// Declare data
	String seriesWithPublicEpisode;
	Integer numberOFSwipes;
	String episodeTitle;
	LongformResult firstEpisode;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public NotLoggedInWithTVETest(String runParams) {
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
		seriesWithPublicEpisode = propertyResult.getPropertyTitle();
		numberOFSwipes = propertyResult.getNumOfSwipes();

		LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
		episodeTitle = episodeResult.getEpisodeTitle();
		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
	}

	@TestCaseId("34886")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void notLoggedInWithTVEAndroidTest() {

		ProxyUtils.disableAds();
		ProxyUtils.disableTve();

		// Action Chains
		splashChain.splashAtRest(); // Wait for splash/launch screen to end
		chromecastChain.dismissChromecast(); // Dismiss the chromecast alert
		loginChain.logoutIfLoggedIn();
		autoPlayChain.enableAutoPlay();


		home.enterSeriesView(seriesWithPublicEpisode, numberOFSwipes);
		series.scrollUpToSeriesTitle(seriesWithPublicEpisode);
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		videoPlayer.getToEndOfVideo();

		videoPlayer.contentNotAvailableIcn().waitForVisible();

	}

	@TestCaseId("34886")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //getToEndOfVideo() not implement for iOS
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void notLoggedInWithTVEiOSTest() {

		ProxyUtils.disableAds();

		// Action Chains
		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.logoutIfLoggedIn();
		autoPlayChain.enableAutoPlay();

		// Open a series with unlocked episodes
		home.enterSeriesView(seriesWithPublicEpisode, numberOFSwipes);
		home.fullEpisodesBtn().waitForVisible().tap();

		// Verifies video begins playback
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Scrubs to the end of videos until a locked one loads in the player
		videoPlayer.getToEndOfVideoUntilLocked();

        // Verify video is not playing
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

	}
}