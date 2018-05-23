package com.viacom.test.vimn.uitests.tests.clips.continousplayback;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ClipsContinousPlaybackWithAutoplayClipsOnLandscapeMode extends BaseTest {
	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain clipsAutoPlay;
	Home home;
	Clips clips;
	VideoPlayer videoPlayer;
	HomePropertyFilter propertyFilter;

	// Declare data
	String seriesTitle;
	Integer numOfSwipes;
	String firstClipTitle;
	String secondClipTitle;
	Boolean shortFormEnabled = false;

	@Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ClipsContinousPlaybackWithAutoplayClipsOnLandscapeMode(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		clipsAutoPlay = new AutoPlayChain();
		home = new Home();
		clips = new Clips();
		videoPlayer = new VideoPlayer();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		shortFormEnabled = MainConfig.isShortFormEnabled();

		if (shortFormEnabled) {
			PropertyResult seriesResult = propertyFilter.withClips().propertyFilter().getFirstProperty();
			seriesTitle = seriesResult.getPropertyTitle();
			numOfSwipes = seriesResult.getNumOfSwipes();
			firstClipTitle = seriesResult.getClips().getFirstClip().getClipTitle();
			secondClipTitle = seriesResult.getClips().get(1).getClipTitle();
		}
	}

	@TestCaseId("66442")
	@Features(Config.GroupProps.CLIPS_CONTINOUS_PLAYBACK)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.CLIPS_CONTINOUS_PLAYBACK })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
	public void clipsContinuousPlaybackWithAutoplayClipsOnLandscapeModeAndroidTest() {
		if (shortFormEnabled && seriesTitle != null) {

			ProxyUtils.disableAds();

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			clipsAutoPlay.enableAutoPlayClip();

			home.flickRightToSeriesThumb(seriesTitle, numOfSwipes);
			home.enterClipsByTappingOnBackground();

			clips.clipTitle(firstClipTitle).waitForVisible().rotateScreen(ScreenOrientation.LANDSCAPE).pause(1000);			
			videoPlayer.progressSpinner().waitForPlayerLoad();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			videoPlayer.getToEndOfVideo();
			videoPlayer.maximizeBtn().waitForVisible().rotateScreen(ScreenOrientation.PORTRAIT).pause(1000);
			clips.clipTitle(secondClipTitle).waitForVisible();
			videoPlayer.progressSpinner().waitForPlayerLoad();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		} else {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		}
	}

	@TestCaseId("66442")
	@Features(Config.GroupProps.CLIPS_CONTINOUS_PLAYBACK)
	@Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.CLIPS_CONTINOUS_PLAYBACK })
	@Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
	public void clipsContinuousPlaybackWithAutoplayClipsOnLandscapeModeiOSTest() {
		
		// To-Do

	}
	
}
