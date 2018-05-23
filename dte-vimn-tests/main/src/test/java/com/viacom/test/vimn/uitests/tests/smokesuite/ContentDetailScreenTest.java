package com.viacom.test.vimn.uitests.tests.smokesuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SHOW_DETAILS_VIEW;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CLIP_START;

import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.SmokeBaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmniturePlayerData;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import java.util.Map;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ContentDetailScreenTest extends SmokeBaseTest {

	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	 
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ContentDetailScreenTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	smokeSetupTest();
    }
    
    // BROKEN until https://jira.mtvi.com/browse/VAA-6866, https://jira.mtvi.com/browse/DSS-17092 gets fixed
    @TestCaseId("55660")
    @Features(GroupProps.CONTENT_DETAIL_SCREEN)
    @Test(groups = { GroupProps.BROKEN, GroupProps.CONTENT_DETAIL_SCREEN, GroupProps.SMOKE})
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void contentDetailScreenAndroidTest() {

		splashChain.splashAtRest();
		chromecastChain.dismissChromecast();

		ProxyUtils.disableAds();

		if (firstSeriesTitle != null) {
			// Validate Series detail screen
			home.flickRightToSeriesThumb(firstSeriesTitle, seriesSwips);
			home.seriesThumbBtn(firstSeriesTitle).waitForVisible().tap();

			if (seriesHasFullEpisodes && seriesHasExtras) {
				series.fullEpisodesBtn().waitForVisible().tap();
				series.watchXtrasBtn().waitForVisible().tap();
				Logger.logConsoleMessage("<--- 1 check point: Series Detail Screen Validation :- Completed --->");
						
				// Validate Series Detail Show Screen Page Reporting
				expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstSeriesTitle, seriesPosition.toString());				
				actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
				Omniture.assertOmnitureValues(expectedMap, actualMap);
				clearMap();
				Logger.logConsoleMessage("<--- 2 check point: Series Detail Show Screen Page Reporting Call Validation :- Completed --->");					
				/*
				 * // Validate Clip Tab Change Link Reporting 
				 * expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstSeriesTitle, null, null);
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap(); 
				 * Logger.logConsoleMessage("<----- 3 check point: Clip Tab Change Link Reporting Call For Series Validation :- Completed ---->");
				 * 
				 * // Validate Clip Start Player Reporting Call For Series 
				 * expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstSeriesTitle, null, null, null); 
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap();
				 * clips.clipCloseBtn().waitForVisible().tap(); 
				 * Logger.logConsoleMessage("<-------- 4 check point: Clip Start Player Reporting Call For Series Validation :- Completed ------>");
				 */
				ProxyRESTManager.clearLog();// Reset proxy log
			}
			series.backBtn().waitForVisible().tap(); 
			home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, seriesSwips);
		}

		if (firstMovieTitle != null) {
			// Validate Movie detail screen
			home.flickRightToSeriesThumb(firstMovieTitle, movieSwips);
			if (movieHasVideo && movieHasTrailer) {
				series.movieBtn().waitForVisible().tap();
				series.trailerBtn().waitForVisible().tap();
				Logger.logConsoleMessage("<---- 5 check point: Movie Detail Screen Validation :- Completed ---->");					

				// Validate Movie Detail Screen Page Reporting
				expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstMovieTitle, moviePosition.toString());					
				actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
				Omniture.assertOmnitureValues(expectedMap, actualMap);
				clearMap();
				Logger.logConsoleMessage("<----- 6 check point: Movie Detail Screen Page Reporting Call Validation :- Completed ---->");					
				/*
				 * // Validate Clip Tab Change Link Reporting Call For Movie 
				 * expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstMovieTitle, null, null);
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap(); 
				 * Logger.logConsoleMessage("<----- 7 check point: Clip Tab Change Link Reporting Call For Movie Validation :- Completed ---->");
				 * 
				 * // Validate Clip Start Player Reporting Call For Movie 
				 * expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstSeriesTitle, null, null, null); 
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap();
				 * clips.clipCloseBtn().waitForVisible().tap(); 
				 * Logger.logConsoleMessage("<-------- 8 check point: Clip Start Player Reporting Call For Movie Validation :- Completed ------>");
				 */
				ProxyRESTManager.clearLog();// Reset proxy log
			}
			series.backBtn().waitForVisible().tap(); // Added
			home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, movieSwips);
		}

		if (firstFightTitle != null) {
			// Validate Fight detail screen
			home.flickRightToSeriesThumb(firstFightTitle, fightSwips);
			if (fightHasVideo && fightHasExtras) {
				series.specialBtn().waitForVisible().tap();
				series.watchXtrasBtn().waitForVisible().tap();
				Logger.logConsoleMessage("<-------- 9 check point: Fight Detail Screen Validation :- Completed ------>");
						
				// Validate Fight Detail Screen Page Reporting Call
				expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstFightTitle, fightPosition.toString());					
				actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW);
				Omniture.assertOmnitureValues(expectedMap, actualMap);
				clearMap();
				Logger.logConsoleMessage("<---- 10 check point: Fight Detail Screen Page Reporting Call Validation :- Completed ---->");						
				/*
				 * // Validate Clip Tab Change Link Reporting Call For Fight 
				 * expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstFightTitle, null, null); 
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap();
				 * Logger.logConsoleMessage("<---- 11 check point: Clip Tab Change Link Reporting Call For Fight Validation :- Completed ---->");
				 * 
				 * // Validate Clip Start Player Reporting Call 
				 * expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstFightTitle, null, null, null); 
				 * actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
				 * Omniture.assertOmnitureValues(expectedMap, actualMap); 
				 * clearMap();
				 * clips.clipCloseBtn().waitForVisible().tap(); 
				 * Logger.logConsoleMessage("<------ 12 check point: Clip Start Player Reporting Call For Fight Validation :- Completed ---->");
				 */
				ProxyRESTManager.clearLog();// Reset proxy log
			}
			home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, fightSwips);
		}
	}
    
    @TestCaseId("55660")
    @Features(GroupProps.CONTENT_DETAIL_SCREEN)
    @Test(groups = { GroupProps.FULL, GroupProps.CONTENT_DETAIL_SCREEN, GroupProps.SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void contentDetailScreeniOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
    	chromecastChain.dismissChromecast();
    	
    	ProxyUtils.disableAds();
    	   	
    	if (firstSeriesTitle !=null) {
        	//Validate Series detail screen
        	home.flickRightToSeriesThumb(firstSeriesTitle, seriesSwips);
        	home.seriesThumbBtn(firstSeriesTitle).waitForVisible().tap();
        	
        	if (seriesHasFullEpisodes && seriesHasExtras) {
        		series.fullEpisodesBtn().waitForVisible().tap();
        		series.watchXtrasBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 1 check point: Series Detail Screen Validation :- Completed ------>");
        		
        		//Validate Series Detail Show Screen Page Reporting
            	expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstSeriesTitle, seriesPosition.toString());
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 2 check point: Series Detail Show Screen Page Reporting Call Validation :- Completed ------>");
        		
            	//Validate Clip Tab Change Link Reporting
        		expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstSeriesTitle, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 3 check point: Clip Tab Change Link Reporting Call For Series Validation :- Completed ------>");
        		
            	//Validate Clip Start Player Reporting Call For Series
        		expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstSeriesTitle, null, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
        		clips.clipCloseBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 4 check point: Clip Start Player Reporting Call For Series Validation :- Completed ------>");
        		ProxyRESTManager.clearLog();//Reset proxy log
        	}
        	home.waitUntilHomeScreenDisplay(); // To avoid any Alert pop up 
        	home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, seriesSwips);
    	}
    	
    	if (firstMovieTitle !=null) {
        	//Validate Movie detail screen
        	home.flickRightToSeriesThumb(firstMovieTitle, movieSwips);
        	if (movieHasVideo && movieHasTrailer) {
        		series.movieBtn().waitForVisible().tap();
        		series.trailerBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 5 check point: Movie Detail Screen Validation :- Completed ------>");
        		
        		//Validate Movie Detail Screen Page Reporting
            	expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstMovieTitle, moviePosition.toString());
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 6 check point: Movie Detail Screen Page Reporting Call Validation :- Completed ------>");
        		
            	//Validate Clip Tab Change Link Reporting Call For Movie
        		expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstMovieTitle, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 7 check point: Clip Tab Change Link Reporting Call For Movie Validation :- Completed ------>");
        		
            	//Validate Clip Start Player Reporting Call For Movie
        		expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstSeriesTitle, null, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
        		clips.clipCloseBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 8 check point: Clip Start Player Reporting Call For Movie Validation :- Completed ------>");
        		ProxyRESTManager.clearLog();//Reset proxy log
        	}
        	home.waitUntilHomeScreenDisplay(); // To avoid any Alert pop up 
        	home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, movieSwips);
    	}

    	if (firstFightTitle !=null) {
        	//Validate Fight detail screen
        	home.flickRightToSeriesThumb(firstFightTitle, fightSwips);
        	if (fightHasVideo && fightHasExtras) {
        		series.specialBtn().waitForVisible().tap();
        		series.watchXtrasBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 9 check point: Fight Detail Screen Validation :- Completed ------>");
        		
        		//Validate Fight Detail Screen Page Reporting Call
            	expectedMap = OmniturePageData.buildSeriesDetailsViewExpectedMap(firstFightTitle, fightPosition.toString());
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SHOW_DETAILS_VIEW_iOS);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 10 check point: Fight Detail Screen Page Reporting Call Validation :- Completed ------>");
        		
            	//Validate Clip Tab Change Link Reporting Call For Fight
        		expectedMap = OmnitureLinkData.buildEpisodeToClipTabChangeExpectedMap(firstFightTitle, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPISODE_TO_CLIP_TAB_CHANGE);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
            	Logger.logConsoleMessage("<-------- 11 check point: Clip Tab Change Link Reporting Call For Fight Validation :- Completed ------>");
        		
            	//Validate Clip Start Player Reporting Call
        		expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(firstFightTitle, null, null, null);
            	actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
            	Omniture.assertOmnitureValues(expectedMap, actualMap);
            	clearMap();
        		clips.clipCloseBtn().waitForVisible().tap();
        		Logger.logConsoleMessage("<-------- 12 check point: Clip Start Player Reporting Call For Fight Validation :- Completed ------>");
        		ProxyRESTManager.clearLog();//Reset proxy log
        	}
        	home.waitUntilHomeScreenDisplay(); // To avoid any Alert pop up 
        	home.flickLeftToSeriesThumb(firstTitleOnHomeScreen, fightSwips);
    	}

    }
    
    public void clearMap() {
    	expectedMap.clear();
   	 	actualMap.clear();
    }
}
