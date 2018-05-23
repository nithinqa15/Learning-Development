package com.viacom.test.vimn.uitests.tests.playback;


import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ScrubberTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	HomePropertyFilter propertyFilter;

	// Declare data
	PropertyResult featuredProperty;
	String seriesTitle;
	String episodeTitle;
	Integer numberOfSwipes;
	LongformResult firstEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public ScrubberTest(String runParams) {
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
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numberOfSwipes = propertyResult.getNumOfSwipes();

		LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
		episodeTitle = episodeResult.getEpisodeTitle();

		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();

	}

	@TestCaseId("34887")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void scrubberAndroidTest() {

		ProxyUtils.disableAds();
		ProxyUtils.disableTve();

		// Chain actions
		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		autoPlayChain.enableAutoPlay();

		// Open a featured series
		home.enterSeriesView(seriesTitle,numberOfSwipes);

		series.seriesTtl(seriesTitle).waitForViewLoad();

		// Verify episode starts playing right away
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

		// Verifies that the scrubber is moving by checking the progress txt
		int checkScrubberNumTimes = 5;
		Integer prevProgressTxt = videoPlayer.getProgressTxtInSeconds();
		while (checkScrubberNumTimes > 0) {
			videoPlayer.playerCtr().pause(1000);
			Integer currentProgressTxt = videoPlayer.getProgressTxtInSeconds();
			Logger.logMessage(
					"Is prevProgressTxt: " + prevProgressTxt + " is less than the currentProgressTxt: " + currentProgressTxt);
			Assert.assertTrue(prevProgressTxt < currentProgressTxt);
			prevProgressTxt = currentProgressTxt;
			checkScrubberNumTimes--;
		}
	}
}
