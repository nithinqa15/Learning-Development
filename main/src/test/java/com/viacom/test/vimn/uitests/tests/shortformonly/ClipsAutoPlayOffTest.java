package com.viacom.test.vimn.uitests.tests.shortformonly;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsOnlyException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;

import com.viacom.test.vimn.common.util.Config.StaticProps;
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
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ClipsAutoPlayOffTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	Clips clips;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
	AlertsChain alertschain;
	HomePropertyFilter propertyFilter;

	// Declare data
	String clipsOnlySeriesTitle;
	Integer numberOfSwipes;
	Integer numOfClips;
	Integer numOfClipsToLookAt;
	Boolean shortFormEnabled = false;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ClipsAutoPlayOffTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		autoPlayChain = new AutoPlayChain();
		clips = new Clips();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();
		alertschain = new AlertsChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		shortFormEnabled = MainConfig.isShortFormEnabled();
		if (shortFormEnabled) {
	            PropertyResult propertyResult = propertyFilter.withClips().withClipsOnly().propertyFilter().getFirstProperty();

	            clipsOnlySeriesTitle = propertyResult.getPropertyTitle();
	            numberOfSwipes = propertyResult.getNumOfSwipes();
	            numOfClips = propertyResult.getClips().size();
	    }

	}

	@TestCaseId("34880")
	@Features(GroupProps.SHORTFORM_ONLY)
	@Test(groups = { GroupProps.FULL, GroupProps.SHORTFORM_ONLY })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void clipsAutoPlayOffAndroidTest() {

		if (shortFormEnabled && clipsOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.disableAutoPlayClip();

			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForVisible().tap();
			
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			numOfClipsToLookAt = Math.min(3, numOfClips);
			for (int i = 0; i < numOfClipsToLookAt; i++) {
				clips.backBtn().scrollDownOnSeriesView(StaticProps.NORMAL_SCROLL);
				clips.backBtn().scrollDownOnSeriesView(StaticProps.NORMAL_SCROLL);
				videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			}

		} else if (!shortFormEnabled) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new SkipException(message);
		} else {
			String message = "There are no series with clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new SkipException(message);
		}

	}
	
	@TestCaseId("34880")
	@Features(GroupProps.SHORTFORM_ONLY)
	@Test(groups = { GroupProps.FULL, GroupProps.SHORTFORM_ONLY  })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
				  ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
				  ParamProps.BET_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP,
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void clipsAutoPlayOffiOSTest() {

		if (shortFormEnabled && clipsOnlySeriesTitle != null) {

			splashChain.splashAtRest();
			alertschain.dismissAlerts();
			autoPlayChain.disableAutoPlayClip();

			home.flickRightToSeriesThumb(clipsOnlySeriesTitle, numberOfSwipes);
			home.watchExtrasBtn().waitForVisible().tap();
			home.watchExtrasBtn().waitForPlayerLoad();
			
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			numOfClipsToLookAt = Math.min(3, numOfClips);
			for (int i = 0; i < numOfClipsToLookAt; i++) {
				clips.clipCloseBtn().scrollDownOnSeriesView(StaticProps.NORMAL_SCROLL);
				clips.clipCloseBtn().scrollDownOnSeriesView(StaticProps.NORMAL_SCROLL);
				videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			}

		} else if (!shortFormEnabled) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			String message = "There are no series with clips only on: " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so skipping test";
			Logger.logMessage(message);
			throw new NoSeriesWithClipsOnlyException(message);
		}
	}
}
