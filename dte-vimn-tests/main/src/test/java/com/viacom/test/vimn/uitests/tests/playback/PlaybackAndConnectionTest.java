package com.viacom.test.vimn.uitests.tests.playback;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.DevSettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.InternetConnectivity;
import com.viacom.test.vimn.common.filters.LongformResult;
import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import io.appium.java_client.android.Connection;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PlaybackAndConnectionTest extends BaseTest {

	// Declare page objects and actions
	SplashChain splashChain;
	AlertsChain alertsChain;
	ChromecastChain chromecastChain;
	AutoPlayChain autoPlayChain;
	Home home;
	Series series;
	VideoPlayer videoPlayer;
	DevSettingsMenu devSettingsMenu;
	HomePropertyFilter propertyFilter;

	// Declare data
	String seriesTitle;
	Integer numOfSwipes;
	String episodeTitle;
	LongformResult firstEpisode;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public PlaybackAndConnectionTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		home = new Home();
		series = new Series();
		videoPlayer = new VideoPlayer();
		propertyFilter = new HomePropertyFilter(NON_PAGINATED);

		PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
		seriesTitle = propertyResult.getPropertyTitle();
		numOfSwipes = propertyResult.getNumOfSwipes();
		LongformResult episodeResult = propertyResult.getLongformFilter().withPublicEpisodesOnly().episodesFilter().getFirstEpisode();
		episodeTitle = episodeResult.getEpisodeTitle();

		firstEpisode = propertyResult.getLongformFilter().episodesFilter().getFirstEpisode();
	}

	@TestCaseId("34895")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.FULL, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackAndConnectionAndroidTest() {

		ProxyUtils.disableAds();
		ProxyUtils.disableTve();

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		home.enterSeriesView(seriesTitle, numOfSwipes);
		series.seriesTtl(seriesTitle).waitForViewLoad();
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		videoPlayer.playerCtr().exposeControlRack();
		Integer prevProgressTxt = videoPlayer.getProgressTxtInSeconds();
		DriverManager.getAndroidDriver().setConnection(Connection.AIRPLANE);
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
		videoPlayer.retryBtn().waitForVisible();
		DriverManager.getAndroidDriver().setConnection(Connection.ALL);
		InternetConnectivity.waitForInternetAvailable(30);
		videoPlayer.retryBtn().waitForVisible().tap();
		videoPlayer.playerCtr().exposeControlRack();
		Integer currentProgressTxt = videoPlayer.getProgressTxtInSeconds();
		Assert.assertTrue(prevProgressTxt <= currentProgressTxt && currentProgressTxt <= prevProgressTxt + 10);
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
	}

	// Kevin Dannenberg: Broken due to NullPointerException error on Airplane Mode locator
	@TestCaseId("34895")
	@Features(GroupProps.PLAYBACK)
	@Test(groups = { GroupProps.BROKEN, GroupProps.PLAYBACK })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void playbackAndConnectioniOSTest() {

		ProxyUtils.disableAds();

		splashChain.splashAtRest();
        alertsChain.dismissAlerts();
		chromecastChain.dismissChromecast();
		
		home.enterSeriesView(seriesTitle, numOfSwipes);
		videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        // Open device settings, turn OFF WiFi & turn ON airplane mode
		videoPlayer.playerCtr().waitForVisible().openDeviceSettingWindow(Config.StaticProps.NORMAL_SCROLL);
        devSettingsMenu.enableAirplaneModeiOS();
		devSettingsMenu.disableWifiModeiOS();
        videoPlayer.playerCtr().waitForVisible().closeDeviceSettingWindow(Config.StaticProps.NORMAL_SCROLL);

        // Verify video is not playing and that an error message appears
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
		videoPlayer.retryBtn().waitForVisible();

        // Open device settings, turn ON WiFi & turn OFF airplane mode
        videoPlayer.playerCtr().waitForVisible().openDeviceSettingWindow(Config.StaticProps.NORMAL_SCROLL);
        devSettingsMenu.disableAirplaneModeiOS();
        devSettingsMenu.enableWifiModeiOS();
        videoPlayer.playerCtr().waitForVisible().closeDeviceSettingWindow(Config.StaticProps.NORMAL_SCROLL);

        // Verify video begins playback again after reconnecting to internet
		InternetConnectivity.waitForInternetAvailable(30);
		videoPlayer.retryBtn().waitForVisible().tap();
		videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
	}

	@AfterMethod
    public void enableInternet(){
	    if(TestRun.isAndroid()){
	        if(!DriverManager.getAndroidDriver().getConnection().equals(Connection.ALL)){
  				DriverManager.getAndroidDriver().setConnection(Connection.ALL);
  			}
  		}
    }
}