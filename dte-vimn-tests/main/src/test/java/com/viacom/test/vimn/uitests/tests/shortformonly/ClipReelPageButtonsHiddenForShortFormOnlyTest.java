package com.viacom.test.vimn.uitests.tests.shortformonly;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsOnlyException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
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

public class ClipReelPageButtonsHiddenForShortFormOnlyTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AutoPlayChain autoPlayChain;
	ChromecastChain chromecastchain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;
	HomePropertyFilter propertyFilter;

	// Declare data
	String firstSeriesTitle;
	String clipsAndEpisodesSeriesTitle;
	String clipsOnlySeriesTitle;
	Integer numberOfSwipesForClipsOnly;
	Integer numberOfSwipesForClipsAndEpisodes;
	Boolean shortFormEnabled = false;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ClipReelPageButtonsHiddenForShortFormOnlyTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		autoPlayChain = new AutoPlayChain();
		chromecastchain = new ChromecastChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		
		shortFormEnabled = MainConfig.isShortFormEnabled();
		if (shortFormEnabled) {
				//Clips only series result
	            PropertyResult clipOnlyPropertyResult = propertyFilter.withClips().withClipsOnly().propertyFilter().getFirstProperty();
	            clipsOnlySeriesTitle = clipOnlyPropertyResult.getPropertyTitle();
	            numberOfSwipesForClipsOnly = clipOnlyPropertyResult.getNumOfSwipes();
	            
	            propertyFilter = new HomePropertyFilter(NON_PAGINATED); //Re-initialization to use filter second time
	            //Clips and episodes series result
	            PropertyResult clipsAndEpisodesPropertyResult = propertyFilter.withEpisodes().withClips().propertyFilter().getFirstProperty();
	            clipsAndEpisodesSeriesTitle = clipsAndEpisodesPropertyResult.getPropertyTitle();
	            numberOfSwipesForClipsAndEpisodes = clipsAndEpisodesPropertyResult.getNumOfSwipes();
	    }

	}

	@TestCaseId("")
	@Features(Config.GroupProps.SHORTFORM_ONLY)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SHORTFORM_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void clipReelPageButtonsHiddenForShortFormOnlyAndroidTest() {

		if (shortFormEnabled && clipsOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			autoPlayChain.enableAutoPlayClip();

			// Open clip reel for series w/ episodes and short forms
			home.flickRightToSeriesThumb(clipsAndEpisodesSeriesTitle, numberOfSwipesForClipsAndEpisodes);
			home.watchExtrasBtn().waitForVisible().tap();
			series.watchXtrasBtn().waitForVisible();
			series.fullEpisodesBtn().waitForVisible();

			// Go back to the home screen and to the first series
			series.backBtn().waitForVisible().tap();
			// Have to do this since
			// home.fullEpisodesBtn().waitForCarouselFlickedLeftTo(numberOfSwipesForClipsAndEpisodes);
			// doesnt work
			for (int i = 0; i < numberOfSwipesForClipsAndEpisodes; i++) {
				home.fullEpisodesBtn().flickCarouselLeft();
			}

			// Go to clips only series
			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipesForClipsOnly);
			home.watchExtrasBtn().waitForVisible().tap();
			// Make sure that watch extras and full episodes button are not visible
			series.watchXtrasBtn().waitForNotVisible();
			series.fullEpisodesBtn().waitForNotVisible();

		} else if (!shortFormEnabled) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			String message = "There are no clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithClipsOnlyException(message);
		}

	}
	
	@TestCaseId("")
	@Features(Config.GroupProps.SHORTFORM_ONLY)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SHORTFORM_ONLY  })
	@Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
	public void clipReelPageButtonsHiddenForShortFormOnlyiOSTest() {

		if (shortFormEnabled && clipsOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			autoPlayChain.enableAutoPlayClip();

			// Open clip reel for series w/ episodes and short forms
			home.flickRightToSeriesThumb(clipsAndEpisodesSeriesTitle, numberOfSwipesForClipsAndEpisodes);
			home.watchExtrasBtn().waitForVisible().tap();
			series.watchXtrasBtn().waitForVisible();
			series.fullEpisodesBtn().waitForVisible().tap();

			// Go back to the home screen and to the first series
			series.backBtn().waitForVisible().tap();
			home.flickBackToFirstSeriesThumb(clipsOnlySeriesTitle, numberOfSwipesForClipsAndEpisodes);

			// Go to clips only series
			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipesForClipsOnly);
			home.watchExtrasBtn().waitForVisible().tap();
			// Make sure that watch extras and full episodes button are not visible
			series.watchXtrasBtn().waitForNotPresentOrVisible();
			series.fullEpisodesBtn().waitForNotPresentOrVisible();
		} else if (!shortFormEnabled) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			String message = "There are no clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithClipsOnlyException(message);
		}
	}
}
