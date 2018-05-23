package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.*;

import java.util.HashMap;
import java.util.Map;

import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.CellularToggleChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.ProgressBar;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.Share;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureSharingCallTest extends BaseTest{

	//Declare page objects/actions
	SplashChain splashChain;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	LoginChain loginChain;
	SettingsChain settingsChain;
	Home home;
	CellularToggleChain cellularToggleChain;
	AutoPlayChain autoPlayToggleChain;
	AppDataFeed appDataFeed;
	Settings settings;
	ProgressBar progressBar;
	VideoPlayer videoPlayer;
	SettingsMenu settingsMenu;
	Navigate navigate;
	Share share;

	//Declare data
	String seriesTitle;
	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	String cellVidPlayback, version;

	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public OmnitureSharingCallTest(String runParams) {
		super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		splashChain = new SplashChain();
		chromecastChain = new ChromecastChain();
		alertschain = new AlertsChain();
		loginChain = new LoginChain();
		cellularToggleChain = new CellularToggleChain();
		autoPlayToggleChain = new AutoPlayChain();
		settingsChain = new SettingsChain();
	    videoPlayer = new VideoPlayer();
        progressBar = new ProgressBar();
		appDataFeed = new AppDataFeed();
		settings = new Settings();
		home = new Home();
		share = new Share();
		settingsMenu = new SettingsMenu();
		navigate = new Navigate();
		expectedMap = new HashMap<>();

		String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
        seriesTitle = appDataFeed.getPropertyTitles(featuredPropertyFeedURL).get(0);
	}

	@TestCaseId("35903")
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.BROKEN, GroupProps.VIDEO_PLAYER, GroupProps.OMNITURE })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omnitureSharingCallAndroidTest() {
		//TO-DO
	}

	@TestCaseId("35903")
	@Features(GroupProps.OMNITURE)
	@Test(groups = { GroupProps.FULL, GroupProps.VIDEO_PLAYER, GroupProps.OMNITURE  })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
	public void omnitureSharingCalliOSTest() {

		ProxyUtils.disableAds();
        
		splashChain.splashAtRest();
		alertschain.dismissAlerts();
		chromecastChain.dismissChromecast();
		loginChain.logoutIfLoggedIn();
		
		settings.settingsBtn().waitForVisible().tap();
	    //PV - If Phone screen small then we need to support this method with scroll (iPhone 5S in lab)
	    if (!settingsMenu.buildVersion().isVisible()) {
	        	settingsMenu.buildVersion().waitForScrolledDownTo(5, 300);
	    } 
	    version = settingsMenu.buildVersion().waitForVisible().getIOSElement().getText();
	    cellVidPlayback = settingsMenu.getCellVidPlaybackStatus();
	    settingsMenu.backBtn().waitForVisible().tap();
	    
		home.seriesThumbBtn(seriesTitle).waitForVisible();
		home.fullEpisodesBtn().waitForVisible().tap();
		progressBar.loadingSpinnerIcn().pause(StaticProps.LARGE_PAUSE).waitForNotPresent();
		share.shareBtn().waitForVisible().tap();
		share.AddtoNotesBtn().waitForVisible().tap();
        
		expectedMap = OmnitureLinkData.buildShareExpectedMap(seriesTitle, version);
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHARE);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
	}
}