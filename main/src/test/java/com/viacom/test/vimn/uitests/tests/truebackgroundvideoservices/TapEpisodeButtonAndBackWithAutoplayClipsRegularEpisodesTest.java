package com.viacom.test.vimn.uitests.tests.truebackgroundvideoservices;

import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class TapEpisodeButtonAndBackWithAutoplayClipsRegularEpisodesTest extends BaseTest {

	// Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
	ChromecastChain chromecastChain;
	HomePropertyFilter propertyFilter;
	AutoPlayChain autoPlayChain;
	VideoPlayer videoPlayer;

	// Declare data
	String seriesWithFullEpisodes;
	String seriesTitle;
	Integer numberOfSwipes;
	Boolean backgroundServiceVideoEnabled;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public TapEpisodeButtonAndBackWithAutoplayClipsRegularEpisodesTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {

		splashChain = new SplashChain();
		home = new Home();
		series = new Series();
		chromecastChain = new ChromecastChain();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);
		autoPlayChain = new AutoPlayChain();
		videoPlayer = new VideoPlayer();

		PropertyResult propertyResult = propertyFilter.withPublicEpisodes().withBackgroundVideo().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numberOfSwipes = propertyResult.getNumOfSwipes();
		backgroundServiceVideoEnabled = MainConfig.isBackgroundVideoServiceEnabled();
	}

	@TestCaseId("32020")
	@Features(Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.TRUE_BACKGROUND_VIDEO_SERVICES})
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void tapEpisodeButtonAndBackWithAutoplayClipsRegularEpisodesAndroidTest() {

		if (backgroundServiceVideoEnabled) {
			ProxyUtils.disableAds();

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoPlayChain.enableAutoPlay();
			autoPlayChain.enableAutoPlayClip();

			home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
			home.fullEpisodesBtn().waitForVisible().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			series.backBtn().waitForPresent().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		} else {
			String message = "Background Video is not enabled " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new BackgroundVideoNotEnabledException(message);
		}
	}
}
