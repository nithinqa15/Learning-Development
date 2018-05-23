package com.viacom.test.vimn.uitests.tests.episodesonly;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithEpisodesOnlyException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TappingOnBackgroundImageTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Clips clips;
	Series series;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;
	HomePropertyFilter propertyFilter;

	// Declare data
	String seriesTitle;
	String firstSeriesTitle;
	String firstClipTitle;
	String episodesOnlySeriesTitle;
	Integer numberOfSwipes;
	Integer numberOfSwipesToSeriesWithEpisodesOnly;
	Integer numberOfClips;
	Integer numberOfSwipesToSeriesWithEpisodesAndClips;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public TappingOnBackgroundImageTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		clips = new Clips();
		series = new Series();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		//Get a First series on Home Screen
		PropertyResult firstPropertyResult = propertyFilter.propertyFilter().getFirstProperty();
	    firstSeriesTitle = firstPropertyResult.getPropertyTitle();
	    numberOfSwipes = firstPropertyResult.getNumOfSwipes();
	    
		//Get a series which have clips and Episodes
		PropertyResult propertyResult = propertyFilter.withEpisodes().withClips().propertyFilter().getFirstProperty();
	    seriesTitle = propertyResult.getPropertyTitle();
	    numberOfSwipesToSeriesWithEpisodesAndClips = propertyResult.getNumOfSwipes();
	    numberOfClips = propertyResult.getClips().size();
	    firstClipTitle = propertyResult.getClips().getFirstClip().getClipTitle();
	    
	    //Get a series which have only Episodes
	    PropertyResult episodesOnlypropertyResult = propertyFilter.withEpisodes().withEpisodesOnly().propertyFilter().getFirstProperty();
	    episodesOnlySeriesTitle = episodesOnlypropertyResult.getPropertyTitle();
		numberOfSwipesToSeriesWithEpisodesOnly = episodesOnlypropertyResult.getNumOfSwipes();
	   
	}

	@TestCaseId("34876")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.EPISODES_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void tappingOnBackgroundImageAndroidTest() {

		if (seriesTitle != null && episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(seriesTitle,  numberOfSwipesToSeriesWithEpisodesAndClips);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();
			// Used to tap on the background image
			home.enterClipsByTappingOnBackground();
			clips.clipTitle(firstClipTitle).waitForScrollDownOnClipsView(numberOfClips);

			clips.backBtn().waitForVisible().tap();
			// Verify we are on the home screen
			home.seriesTtl(seriesTitle).waitForVisible();

			// Go back to the first series
			home.flickBackToFirstSeriesThumb(firstSeriesTitle, numberOfSwipes);
			
			// Navigate to series which have only Episodes
			home.flickRightToSeriesThumb(episodesOnlySeriesTitle, numberOfSwipesToSeriesWithEpisodesOnly);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible();
			home.enterClipsByTappingOnBackground();
			series.scrollUpToSeriesTitle(episodesOnlySeriesTitle);

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}

	@TestCaseId("34876")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = {Config.GroupProps.FULL, Config.GroupProps.EPISODES_ONLY })
	@Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
	public void tappingOnBackgroundImageIosTest() {

		if (seriesTitle != null && episodesOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(seriesTitle,  numberOfSwipesToSeriesWithEpisodesAndClips);
			home.watchExtrasBtn().waitForVisible();
			home.fullEpisodesBtn().waitForVisible();
			// Used to tap on the background image
			home.enterClipsByTappingOnBackground();
			clips.clipTitle(firstClipTitle).waitForScrollDownOnClipsView(numberOfClips);

			clips.backBtn().waitForVisible().tap();
			// Verify we are on the home screen
			home.seriesTtl(seriesTitle).waitForVisible();

			// Go back to the first series
			home.flickBackToFirstSeriesThumb(firstSeriesTitle, numberOfSwipes);
			
			// Navigate to series which have only Episodes
			home.flickRightToSeriesThumb(episodesOnlySeriesTitle, numberOfSwipesToSeriesWithEpisodesOnly);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible();
			home.enterClipsByTappingOnBackground();
			series.scrollUpToSeriesTitle(episodesOnlySeriesTitle);

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}
}