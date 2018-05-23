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
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class BackToHomeScreenFromFullEpisodesButtonTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;
	HomePropertyFilter propertyFilter;

	// Declare data
	String seriesTitle;
	Integer numberOfSwipes;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public BackToHomeScreenFromFullEpisodesButtonTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		series = new Series();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = propertyFilter.withEpisodes().withEpisodesOnly().propertyFilter().getFirstProperty();
	    seriesTitle = propertyResult.getPropertyTitle();
	    numberOfSwipes = propertyResult.getNumOfSwipes();
	}

	@TestCaseId("34874")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.EPISODES_ONLY })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void backToHomeScreenFromFullEpisodesButtonAndroidTest() {

		if (seriesTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible().tap();
			series.scrollUpToSeriesTitle(seriesTitle);
			series.backBtn().waitForVisible().tap();
			home.seriesTtl(seriesTitle).waitForVisible();

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}

	@TestCaseId("34874")
	@Features(Config.GroupProps.EPISODES_ONLY)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.EPISODES_ONLY  })
	@Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
	public void backToHomeScreenFromFullEpisodesButtoniOSTest() {

		if (seriesTitle != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			autoPlayChain.enableAutoPlay();

			home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForNotPresent();
			home.fullEpisodesBtn().waitForVisible().tap();
			series.scrollUpToSeriesTitle(seriesTitle);
			series.backBtn().waitForVisible().tap();
			home.seriesTtl(seriesTitle).waitForVisible();

		} else {
			String message = "There are no series with episodes only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithEpisodesOnlyException(message);
		}

	}
}