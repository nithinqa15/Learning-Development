package com.viacom.test.vimn.uitests.tests.shortformonly;

import java.util.List;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsOnlyException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

@Deprecated 
public class SwipeOnSeriesDetailsSkipsSeriesWithClipsOnlyTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain	chromecastChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;

	// Declare data
	String mainFeedURL;
	String clipsOnlySeriesTitle;
	String previousSeries;
	String nextSeries;
	Integer numberOfSwipes;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SwipeOnSeriesDetailsSkipsSeriesWithClipsOnlyTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();

		mainFeedURL = FeedFactory.getAppMainFeedURL();
		if (appDataFeed.isShortFormEnabled(mainFeedURL)) {
			String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
			Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
			String promoListFeedURL = FeedFactory.getPromoListFeedURL();
			clipsOnlySeriesTitle = appDataFeed.getClipsOnlySeriesTitle(promoListFeedURL);

			if (clipsOnlySeriesTitle != null) {
				List<String> seriesTitles = appDataFeed.getPropertyTitles(promoListFeedURL);
				int indexOfClipsOnlySeriesTitle = seriesTitles.indexOf(clipsOnlySeriesTitle);

				numberOfSwipes = indexOfClipsOnlySeriesTitle + numberOfSeriesInFeaturedFeed;
				previousSeries = seriesTitles.get(indexOfClipsOnlySeriesTitle - 1);
				nextSeries = seriesTitles.get(indexOfClipsOnlySeriesTitle + 1);
				List<String> seriesWithClipsAndEpisodes = appDataFeed.getClipsAndEpisodesSeriesTitles(promoListFeedURL);
				if (!seriesWithClipsAndEpisodes.contains(previousSeries) || !seriesWithClipsAndEpisodes.contains(nextSeries)) {
					// Test can't continue because the tests before and after the clips
					// only series title should have xtras and full episodes
					previousSeries = null;
					nextSeries = null;
					clipsOnlySeriesTitle = null;
				}

			}
		}

	}

	// VB - 9/22/17 - Swipe between series/clips functionality was removed
				// with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
	@TestCaseId("")
	@Features(Config.GroupProps.SHORTFORM_ONLY)
	@Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.SHORTFORM_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void swipeOnSeriesDetailsSkipsSeriesWithClipsOnlyAndroidTest() {

		if (appDataFeed.isShortFormEnabled(mainFeedURL) && clipsOnlySeriesTitle != null && previousSeries != null
				&& nextSeries != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.enableAutoPlayClip();

			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForNotVisible();

			home.flickLeftToSeriesThumb(previousSeries, 1);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickRightToSeriesThumb(nextSeries, 2);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(previousSeries, 2);
			home.fullEpisodesBtn().waitForVisible().tap();
			series.scrollUpToSeriesTitle(previousSeries, 30);

			series.seriesTtl(previousSeries).flickToNextSeriesFromSeriesView();
			series.fullEpisodesBtn().waitForVisible();
			series.watchXtrasBtn().waitForVisible();
			series.scrollUpToSeriesTitle(nextSeries, 30);

		} else if (!appDataFeed.isShortFormEnabled(mainFeedURL)) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			String message = "There are no clips with only series or the series before and after the series with clips only do not have xtras and full episodes on: "
					+ TestRun.getAppName() + " " + TestRun.getLocale() + " in promolistfeed url : "
					+ FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithClipsOnlyException(message);
		}

	}

	@TestCaseId("")
	@Features(Config.GroupProps.SHORTFORM_ONLY)
	@Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.SHORTFORM_ONLY })
	@Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
	public void swipeOnSeriesDetailsSkipsSeriesWithClipsOnlyiOSTest() {

		if (appDataFeed.isShortFormEnabled(mainFeedURL) && clipsOnlySeriesTitle != null && previousSeries != null
				&& nextSeries != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			autoPlayChain.enableAutoPlayClip();

			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForNotVisible();

			home.flickLeftToSeriesThumb(previousSeries, 1);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickRightToSeriesThumb(nextSeries, 2);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(previousSeries, 2);
			home.fullEpisodesBtn().waitForVisible().tap();
			series.scrollUpToSeriesTitle(previousSeries, 30);

			series.seriesTtl(previousSeries).flickToNextSeriesFromSeriesView();
			home.fullEpisodesBtn().waitForVisible();
			home.watchExtrasBtn().waitForVisible();
			series.scrollUpToSeriesTitle(nextSeries, 30);

		} else if (!appDataFeed.isShortFormEnabled(mainFeedURL)) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			String message = "There are no clips with only series or the series before and after the series with clips only do not have xtras and full episodes on: "
					+ TestRun.getAppName() + " " + TestRun.getLocale() + " in promolistfeed url : "
					+ FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithClipsOnlyException(message);
		}
	}
}
