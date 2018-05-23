package com.viacom.test.vimn.uitests.tests.episodesonly;

import java.util.List;

import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
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
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class SwipeOnClipReelSkipsOverSeriesWithEpisodesOnlyTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertsChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Clips clips;
	AppDataFeed appDataFeed;

	// Declare data
	String episodesOnlySeriesTitle;
	String prevSeriesTitle;
	String nextSeriesTitle;
	String prevSeriesFirstClipTitle;
	String nextSeriesFirstClipTitle;
	Integer numberOfSwipes;
	Integer numOfClipsForPrevSeries;
	Integer numOfClipsForNextSeries;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SwipeOnClipReelSkipsOverSeriesWithEpisodesOnlyTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		clips = new Clips();
		appDataFeed = new AppDataFeed();

		String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
		Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
		String promoListFeedURL = FeedFactory.getPromoListFeedURL();
		episodesOnlySeriesTitle = appDataFeed.getEpisodesOnlySeriesTitle(promoListFeedURL);

		if (episodesOnlySeriesTitle != null) {
			List<String> seriesTitles = appDataFeed.getPropertyTitles(promoListFeedURL);
			numberOfSwipes = seriesTitles.indexOf(episodesOnlySeriesTitle) + numberOfSeriesInFeaturedFeed;

			int indexOfEpisodesOnlySeries = seriesTitles.indexOf(episodesOnlySeriesTitle);

			if (indexOfEpisodesOnlySeries > 0) {
				Integer indexOfPrevSeries = indexOfEpisodesOnlySeries - 1;
				Integer indexOfNextSeries = indexOfEpisodesOnlySeries + 1;

				// Gets us the previous series title and next series title relative to
				// the episodes only series
				prevSeriesTitle = seriesTitles.get(indexOfPrevSeries);
				nextSeriesTitle = seriesTitles.get(indexOfNextSeries);

				// Gets the clip title for the previous and next series relative to the
				// episodes only series
				List<String> seriesIDs = appDataFeed.getSeriesIds(promoListFeedURL);
				String seriesIdOfPrevSeries = seriesIDs.get(indexOfPrevSeries);
				String seriesIdOfNextSeries = seriesIDs.get(indexOfNextSeries);
				String prevSeriesClipsFeedURL = FeedFactory.getClipsFeedURL(seriesIdOfPrevSeries);
				String nextSeriesClipsFeedURL = FeedFactory.getClipsFeedURL(seriesIdOfNextSeries);
				prevSeriesFirstClipTitle = appDataFeed.getFirstPublicClipTitle(prevSeriesClipsFeedURL);
				nextSeriesFirstClipTitle = appDataFeed.getFirstPublicClipTitle(nextSeriesClipsFeedURL);
				numOfClipsForPrevSeries = appDataFeed.getClipTitles(prevSeriesClipsFeedURL).size();
				numOfClipsForNextSeries = appDataFeed.getClipTitles(nextSeriesClipsFeedURL).size();
			}
		}

	}

	// VB - 9/22/17 - Swipe between series/clips functionality was removed
	// with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
	@TestCaseId("")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.EPISODES_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void swipeOnClipReelSkipsOverSeriesWithEpisodesOnlyAndroidTest() {

		if (episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(episodesOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(prevSeriesTitle, 1);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickRightToSeriesThumb(nextSeriesTitle, 2);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(prevSeriesTitle, 2);
			home.watchExtrasBtn().waitForVisible().tap();

			clips.clipTitle(prevSeriesFirstClipTitle).waitForScrollDownOnClipsView(numOfClipsForPrevSeries);
			clips.backBtn().flickToNextSeriesFromSeriesView();
			clips.clipTitle(nextSeriesFirstClipTitle).waitForScrollDownOnClipsView(numOfClipsForNextSeries);

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}

	@TestCaseId("")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = {Config.GroupProps.DEPRECATED, Config.GroupProps.EPISODES_ONLY })
	@Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
	public void swipeOnClipReelSkipsOverSeriesWithEpisodesOnlyIosTest() {

		if (episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			alertsChain.dismissAlerts();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(episodesOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(prevSeriesTitle, 1);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickRightToSeriesThumb(nextSeriesTitle, 2);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();

			home.flickLeftToSeriesThumb(prevSeriesTitle, 2);
			home.watchExtrasBtn().waitForVisible().tap();

			clips.clipTitle(prevSeriesFirstClipTitle).waitForScrollDownOnClipsView(numOfClipsForPrevSeries);
			clips.backBtn().flickToNextSeriesFromSeriesView();
			clips.clipTitle(nextSeriesFirstClipTitle).waitForScrollDownOnClipsView(numOfClipsForNextSeries);

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}
}