package com.viacom.test.vimn.uitests.tests.playback;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PlaybackVideoIsInBackgroundTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	LoginChain loginChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Ads ads;
	VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;

	// Declare data
	String seriesTitle;
	Integer numberOfSwipes;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public PlaybackVideoIsInBackgroundTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		loginChain = new LoginChain();
		autoPlayChain = new AutoPlayChain();
		home = new Home();
		ads = new Ads();
		videoPlayer = new VideoPlayer();
		appDataFeed = new AppDataFeed();

		String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
		Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
		String promoListFeedURL = FeedFactory.getPromoListFeedURL();
		seriesTitle = appDataFeed.getPropertyTitles(promoListFeedURL).get(0);
		List<String> seriesTitles = appDataFeed.getPropertyTitles(promoListFeedURL);
		numberOfSwipes = seriesTitles.indexOf(seriesTitle) + numberOfSeriesInFeaturedFeed;
	}

	@TestCaseId("34893")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackVideoIsInBackgroundAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();
		home.enterSeriesView(seriesTitle, numberOfSwipes);
		ads.durationTimer().waitForVisible();
		ads.waitForAd(1);
		while (!videoPlayer.scrubberBar().isVisible()) {
			videoPlayer.playerCtr().waitForVisible().tap();
		}
		videoPlayer.smallPauseBtn().waitForVisible();
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		DriverManager.getAndroidDriver().runAppInBackground(5);
		while (!videoPlayer.scrubberBar().isVisible()) {
			videoPlayer.playerCtr().waitForVisible().tap();
		}
		videoPlayer.smallPlayBtn().waitForVisible();
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

	}

    @TestCaseId("34893")
    @Features(GroupProps.PLAYBACK)
    @Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK }) //Feed parsing error
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void playbackVideoIsInBackgroundiOSest() {

        ProxyUtils.disableAds();

		splashChain.splashAtRest();
		alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.loginIfNot();
		autoPlayChain.enableAutoPlay();

		// Enters entity detail screen and verifies video begins playback
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        home.fullEpisodesBtn().waitForVisible().tap();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        
        // Backgrounds application for 5 seconds and then foregrounds it
		DriverManager.getIOSDriver().runAppInBackground(5);

		// Verifies entity detail screen loads and video is paused after foregrounding application
        videoPlayer.smallPlayBtn().waitForVisible();
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);

    }
}