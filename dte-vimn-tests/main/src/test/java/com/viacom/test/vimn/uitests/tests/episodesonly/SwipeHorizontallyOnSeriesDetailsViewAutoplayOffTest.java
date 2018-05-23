package com.viacom.test.vimn.uitests.tests.episodesonly;

import java.util.List;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithEpisodesOnlyException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class SwipeHorizontallyOnSeriesDetailsViewAutoplayOffTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;

	// Declare data
	String episodesOnlySeriesTitle;
	String episodesAndClipsSeriesTitle;
	Integer numberOfSwipesForEpisodesAndClipsSeriesTitle;
	Integer totalNumOfSwipesToLastSeries;
	List<String> seriesTitles;
	Integer numOfFlicks;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SwipeHorizontallyOnSeriesDetailsViewAutoplayOffTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		loginChain = new LoginChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();

		String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
		Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
		String promoListFeedURL = FeedFactory.getPromoListFeedURL();
		episodesOnlySeriesTitle = appDataFeed.getEpisodesOnlySeriesTitle(promoListFeedURL);
		episodesAndClipsSeriesTitle = appDataFeed.getClipsAndEpisodesSeriesTitle(promoListFeedURL);
		if (episodesOnlySeriesTitle != null) {
			seriesTitles = appDataFeed.getPropertyTitles(promoListFeedURL);
			numberOfSwipesForEpisodesAndClipsSeriesTitle = seriesTitles.indexOf(episodesAndClipsSeriesTitle)
					+ numberOfSeriesInFeaturedFeed;
			totalNumOfSwipesToLastSeries = seriesTitles.size() - (seriesTitles.indexOf(episodesAndClipsSeriesTitle) + 1);
			numOfFlicks = seriesTitles.indexOf(episodesAndClipsSeriesTitle) + seriesTitles.indexOf(episodesOnlySeriesTitle);
		}

	}

	// VB - 9/22/17 - Swipe between series/clips functionality was removed
	// with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
	@TestCaseId("")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.EPISODES_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void swipeHorizontallyOnSeriesDetailsViewAutoplayOffAndroidTest() {

		if (episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			loginChain.loginIfNot();
			autoPlayChain.disableAutoPlay();

			home.flickRightToSeriesThumb(episodesAndClipsSeriesTitle, numberOfSwipesForEpisodesAndClipsSeriesTitle);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible().tap();

			// Verify we are in the series episode screen
			series.watchXtrasBtn().waitForVisible();
			series.fullEpisodesBtn().waitForVisible();
			series.scrollUpToSeriesTitle(episodesAndClipsSeriesTitle);
			series.flickToNextSeriesUntilSeriesTitleIsVisible(episodesOnlySeriesTitle, numOfFlicks);
			series.watchXtrasBtn().waitForNotPresent();
			series.fullEpisodesBtn().waitForNotPresent();

			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			videoPlayer.playerCtr().waitForVisible().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}
	}

	@TestCaseId("")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.EPISODES_ONLY  })
	@Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
	public void swipeHorizontallyOnSeriesDetailsViewAutoplayOffiOSTest() {

		if (episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			loginChain.loginIfNot();
			autoPlayChain.disableAutoPlay();

			home.flickRightToSeriesThumb(episodesAndClipsSeriesTitle, numberOfSwipesForEpisodesAndClipsSeriesTitle);
			home.watchExtrasBtn().waitForVisible();
			series.fullEpisodesBtn().waitForVisible().tap();

			// Verify we are in the series episode screen
			series.fullEpisodesBtn().waitForVisible();
			series.scrollUpToSeriesTitle(episodesAndClipsSeriesTitle);
			series.flickToNextSeriesUntilSeriesTitleIsVisible(episodesOnlySeriesTitle, numOfFlicks);
			series.watchXtrasBtn().waitForNotPresent();
			series.fullEpisodesBtn().waitForNotVisible();

			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			videoPlayer.playerCtr().waitForVisible().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}
	}
}
