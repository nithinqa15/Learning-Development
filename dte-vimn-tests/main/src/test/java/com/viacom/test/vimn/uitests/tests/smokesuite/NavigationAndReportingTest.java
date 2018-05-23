package com.viacom.test.vimn.uitests.tests.smokesuite;

import com.viacom.test.vimn.common.megabeacon.MegabeaconLogUtils;
import com.viacom.test.vimn.common.omniture.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_ALL_SHOW_SCREEN;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_COUNTRY_CHECK;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HOME_MAIN_SCREEN;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_INSTALL_EVENT;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SETTINGS_SCREEN;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SETTINGS_PAGENAME;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.SmokeBaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import java.util.Map;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class NavigationAndReportingTest extends SmokeBaseTest {

	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NavigationAndReportingTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	smokeSetupTest();
    }

    // VB - 2.5.17 - Broken until omniture discrepencies are resovled
    @TestCaseId("55659")
    @Features(GroupProps.NAVIGATION_AND_REPORTING)
    @Test(groups = { GroupProps.FULL, GroupProps.NAVIGATION_AND_REPORTING, GroupProps.SMOKE, GroupProps.BENTO_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void navigationAndReportingAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		//Validate Home screen locator.(Series title, Series description, All Shows, Settings, Brand logo)
		home.seriesTtl(firstSeriesTitle).waitForPresent();
		home.seriesDescriptionTxt(firstSeriesDescription).waitForVisible();
		settings.settingsBtn().waitForVisible();
		if (!searchServiceEnabled && AllShowsEnabled) {
			allShows.allShowsBtn().waitForVisible();
		} else {
			//To-Do Search glass Validation
		}
		home.brandLogoImage().waitForPresent(); // TODO - add cont desc or text to this element in androidPlatform project
		Logger.logConsoleMessage("<-------- 1 check point: HomeScreen locator Validation :- Completed ------>");

		//Validate HomeScreen page launch call
		expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap(firstSeriesTitle, "n/a");
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
		Omniture.assertOmnitureValues(expectedMap, actualMap);

		//Validate Megabeacon HomeScreen page launch call
		actualMap = MegabeaconLogUtils.getActualMap("channel", EXPECTED_PARAM_HOME_MAIN_SCREEN);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logConsoleMessage("<----- 2 check point: HomeScreen page call :- Completed ------>");

		//Validate user country check call - Skipped this step if install event not present
		if (OmnitureLogUtils.expectedParamPresent(EXPECTED_PARAM_INSTALL_EVENT)) {
			expectedMap.clear();
			actualMap.clear();
			expectedMap = OmnitureCountryCheckData.buildUserCountryCheckExpectedMap();
			Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_COUNTRY_CHECK);
			Omniture.assertOmnitureValues(expectedMap, actualMap);
			Logger.logConsoleMessage("<----- 3 check point: User country page call :- Completed ------>");
		} else {
			Logger.logConsoleMessage("<----- 3 check point: User country page call :- Skipped ? Not a first launch------>");
		}

		//Validate TVE user authentication status call
		expectedMap.clear();
		actualMap.clear();
		expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("guest user", "no provider");
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
		Omniture.assertOmnitureValues(expectedMap, actualMap);

        //Validate Megabeacon TVE user authentication status call
        actualMap = MegabeaconLogUtils.getActualMap("activity", EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

		Logger.logConsoleMessage("<----- 4 check point: TVE user authentication status call :- Completed ------>");

		//Validate settings screen locator (TV Provider account, Video playback, help)
		settings.settingsBtn().waitForVisible().tap();
		settingsMenu.autoPlayTgl().waitForVisible();
		settingsMenu.autoPlayClipsTgl().waitForVisible();
		settingsMenu.cellularTgl().waitForVisible();
		settingsMenu.backBtn().waitForVisible();
		settingsMenu.contactBtn().waitForVisible();
		settingsMenu.signInBtn().isVisible();
		settingsMenu.tvProviderTtl().isVisible();
		if (!TestRun.isBETINTLApp()) { // TV provider unavailable for BET, Only D2C
   	 		settingsMenu.tvProviderSectionTtl().waitForVisible();
   	 	}
		settingsMenu.videoPlayBackSectionTtl().waitForVisible();
		settingsMenu.helpSectionTtl().waitForVisible();
		if (!settingsMenu.buildVersion().isVisible()) {
			settingsMenu.buildVersion().waitForScrolledDownTo(2, 300); //In case if not visible for small iphone screen
		}
		Logger.logConsoleMessage("<----- 5 check point: Validate settings screen locator :- Completed ------>");

		//Validate settings page call
		expectedMap.clear();
		actualMap.clear();
		expectedMap = OmniturePageData.buildSettingsScreenExpectedMap();
		actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SETTINGS_PAGENAME, EXPECTED_PARAM_SETTINGS_SCREEN);
		Omniture.assertOmnitureValues(expectedMap, actualMap); // Test Is failing here due to null values. TODO - Create tickets
		Logger.logConsoleMessage("<----- 6 check point: Validate settings page call :- Completed ------>");

		//Validate all legal pages are available (Privacy Policy, Terms of Use, Legal terms and Notices ..etc)
		settingsMenu.privacyPolicyBtn().waitForVisible().tap();
		privacyPolicy.privacyPolicyTtl().waitForVisible();
		settingsMenu.backBtn().waitForVisible().tap();
		settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed
		settingsMenu.termsAndConditionsBtn().waitForVisible().tap();
		termsAndConditions.termsOfUseTtl().waitForVisible();
		settingsMenu.backBtn().waitForVisible().tap();
		settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed
		settingsMenu.legalNoticesBtn().waitForVisible().tap();
		legalNotices.legalNoticesTtl().waitForVisible();
		settingsMenu.backBtn().waitForVisible().tap();
		Logger.logConsoleMessage("<----- 7 check point: Validate all legal pages are available :- Completed ------>");

		if (!searchServiceEnabled && AllShowsEnabled) {
			allShows.allShowsBtn().waitForVisible().tap();

			//Validate AllShows page call
			expectedMap.clear();
			actualMap.clear();
			expectedMap = OmniturePageData.buildMainShowsScreenExpectedMap();
			actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_ALL_SHOW_SCREEN);
			Omniture.assertOmnitureValues(expectedMap, actualMap);
			Logger.logConsoleMessage("<----- 8 check point: Validate AllShows page call :- Completed ------>");

	        //Validate Megabeacon AllShows page call
	        actualMap = MegabeaconLogUtils.getActualMap("channel", EXPECTED_PARAM_ALL_SHOW_SCREEN);
	        Omniture.assertOmnitureValues(expectedMap, actualMap);

			//Validate all content type available under all shows screen. (scroll up & down)
   	        allShows.scrollDownToSeriesTile(45, allShowsLastSeriesTitle); // Edge case - If more then 30 series available for AllShows
   	        allShows.scrollUpToSeriesTile(45, allShowsFirstSeriesTitle);
			Logger.logConsoleMessage("<----- 9 check point: Validate all content type available under all shows screen :- Completed ------>");
		} else {
			//To-Do Search screen validation
		}
	}

    @TestCaseId("55659")
    @Features(GroupProps.NAVIGATION_AND_REPORTING)
    @Test(groups = { GroupProps.FULL, GroupProps.NAVIGATION_AND_REPORTING, GroupProps.SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void navigationAndReportingiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
    	chromecastChain.dismissChromecast();
    	
    	//Validate Home screen locator.(Series title, Series description, All Shows, Settings, Brand logo)
    	if (!TestRun.isiOS10()) {
    		home.seriesTtl(firstSeriesTitle).waitForPresent();
        	home.seriesDescriptionTxt(firstSeriesDescription).waitForPresent();
        	home.brandLogoImage().waitForPresent();
    	} else {
    		home.seriesTtl(firstSeriesTitle).waitForVisible();
        	home.seriesDescriptionTxt(firstSeriesDescription).waitForVisible();
    	}
    	settings.settingsBtn().waitForVisible();
    	if (!searchServiceEnabled && AllShowsEnabled) {
			allShows.allShowsBtn().waitForVisible();
		} else {
			//To-Do Search glass Validation
		}
    	Logger.logConsoleMessage("<-------- 1 check point: HomeScreen locator Validation :- Completed ------>");
    	
    	//Validate HomeScreen page launch call 
    	expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap(firstSeriesTitle, "n/a");
    	actualMap = OmnitureLogUtils.getActualMap("channel",EXPECTED_PARAM_HOME_MAIN_SCREEN);
    	Omniture.assertOmnitureValues(expectedMap, actualMap);
    	Logger.logConsoleMessage("<----- 2 check point: HomeScreen page call :- Completed ------>");
    	
    	//Validate user country check call - Skipped this step if install event not present          
    	if (OmnitureLogUtils.expectedParamPresent(EXPECTED_PARAM_INSTALL_EVENT)) {
    		expectedMap.clear();
        	actualMap.clear();
    		expectedMap = OmnitureCountryCheckData.buildUserCountryCheckExpectedMap();
    		Map<String, String> actualMap = OmnitureLogUtils.getActualMap("activity",EXPECTED_PARAM_COUNTRY_CHECK);
    		Omniture.assertOmnitureValues(expectedMap, actualMap);
    		Logger.logConsoleMessage("<----- 3 check point: User country page call :- Completed ------>");
    	} else {
    		Logger.logConsoleMessage("<----- 3 check point: User country page call :- Skipped ? Not a first launch------>");
    	}
    	
    	//Validate TVE user authentication status call
    	expectedMap.clear();
    	actualMap.clear();
   	 	expectedMap = OmnitureTVEData.buildOmnitureTVEUserAuthenticationStatusExpectedMap("guest user", "no provider");
   	 	actualMap = OmnitureLogUtils.getActualMap("activity",EXPECTED_PARAM_TVE_AUTHENTICATION_STATUS);
   	 	Omniture.assertOmnitureValues(expectedMap, actualMap);
   	 	Logger.logConsoleMessage("<----- 4 check point: TVE user authentication status call :- Completed ------>");
   	 	
   	    //Validate settings screen locator (TV Provider account, Video playback, help)
   	 	settings.settingsBtn().waitForVisible().tap();
   	 	settingsMenu.autoPlayTgl().waitForVisible();
   	 	settingsMenu.autoPlayClipsTgl().waitForVisible();
   	 	settingsMenu.cellularTgl().waitForVisible();
   	 	settingsMenu.backBtn().waitForVisible();
   	 	settingsMenu.contactBtn().waitForVisible();
   	 	settingsMenu.signInBtn().isVisible();
   	 	settingsMenu.signOutBtn().isVisible();
   	 	settingsMenu.tvProviderTtl().isVisible();
   	 	settingsMenu.providerLogoImg().isVisible();
   	 	if (!TestRun.isBETINTLApp()) { // TV provider unavailable for BET, Only D2C
   	 		settingsMenu.tvProviderSectionTtl().waitForVisible();
   	 	}
   	 	settingsMenu.videoPlayBackSectionTtl().waitForVisible();
   	 	settingsMenu.helpSectionTtl().waitForVisible();
   	    if (!settingsMenu.buildVersion().isVisible()) {
        	settingsMenu.buildVersion().waitForScrolledDownTo(2, 300); //In case if not visible for small iphone screen
        }
   	 	Logger.logConsoleMessage("<----- 5 check point: Validate settings screen locator :- Completed ------>");
   	 	
   	 	//Validate settings page call
   	 	expectedMap.clear();
   	 	actualMap.clear();
    	expectedMap = OmniturePageData.buildSettingsScreenExpectedMap();
    	actualMap = OmnitureLogUtils.getActualMap("channel",EXPECTED_PARAM_SETTINGS_SCREEN);
    	Omniture.assertOmnitureValues(expectedMap, actualMap);
    	Logger.logConsoleMessage("<----- 6 check point: Validate settings page call :- Completed ------>");
   	 	
    	//Validate all legal pages are available (Privacy Policy, Terms of Use, Legal terms and Notices ..etc)
    	settingsMenu.privacyPolicyBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE);
    	//privacyPolicy.privacyPolicyTtl().waitForVisible();
    	if (TestRun.isPhone()) {
    		if (TestRun.isiOS10()) {
    			navigate.closeBtn().waitForVisible().tap();
    		} else {
    			settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed for iOS 11
    		}
    	}
   	 	settingsMenu.termsAndConditionsBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE);
   	 	//termsAndConditions.termsOfUseTtl().waitForVisible();
   	 	if (TestRun.isPhone()) {
	   	 	if (TestRun.isiOS10()) {
	   	 		navigate.closeBtn().waitForVisible().tap();
			} else {
				settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed for iOS 11
			}
   	 	}
   	 	settingsMenu.legalNoticesBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE);
   	 	//legalNotices.legalNoticesTtl().waitForVisible();
   	 	if (TestRun.isPhone()) {
	   	 	if (TestRun.isiOS10()) {
	   	 		navigate.closeBtn().waitForVisible().tap();
			} else {
				settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed for iOS 11
			}
	 	}
   	 	Logger.logConsoleMessage("<----- 7 check point: Validate all legal pages are available :- Completed ------>");
   	 	
   	 	settingsMenu.backBtn().waitForVisible().tap();
   	 	
   	 	if (!searchServiceEnabled && AllShowsEnabled) {
   	   	 	allShows.allShowsBtn().waitForVisible().tap();
   	   	 	
   	   	 	//Validate AllShows page call
   	   	 	expectedMap.clear();
   	   	 	actualMap.clear();
   	        expectedMap = OmniturePageData.buildMainShowsScreenExpectedMap();
   	        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_ALL_SHOW_SCREEN);
   	        Omniture.assertOmnitureValues(expectedMap, actualMap);
   	        Logger.logConsoleMessage("<----- 8 check point: Validate AllShows page call :- Completed ------>");
   	        
   	        //Validate all content type available under all shows screen. (scroll up & down)
   	        progressBar.loadingSpinnerIcn().pause(StaticProps.MEDIUM_PAUSE).waitForNotPresentOrVisible(); //To prevent all shows loading 
   	        allShows.scrollDownToSeriesTile(NumberOfSeriesAvailableForAllShows, allShowsLastSeriesTitle); // Edge case - If more then 30 series available for AllShows
   	        allShows.scrollUpToSeriesTile(NumberOfSeriesAvailableForAllShows, allShowsFirstSeriesTitle);
   	        Logger.logConsoleMessage("<----- 9 check point: Validate all content type available under all shows screen :- Completed ------>");
   	 	} else {
   	 		//To-Do Search screen validation
   	 	}
    }
}
