package com.viacom.test.vimn.uitests.tests.smokesuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.*;

import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.SmokeBaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.CommandExecutorUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import java.util.Map;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class VideoPlaybackTogglePlayPauseTest extends SmokeBaseTest {

	Map<String, String> expectedMap;
	Map<String, String> actualMap;
	 
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public VideoPlaybackTogglePlayPauseTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	smokeSetupTest();
    }
    
    @TestCaseId("73454")
    @Features(GroupProps.VIDEOPLAYBACK_ON_OFF_VIDEO_PLAY_PAUSE)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEOPLAYBACK_ON_OFF_VIDEO_PLAY_PAUSE, GroupProps.SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void videoPlaybackTogglePlayPauseAndroidTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
    	chromecastChain.dismissChromecast();
    	ProxyUtils.disableAds();
    	ProxyUtils.disableTve();
    	
    	//Precondition for public/private Episode & clip
    	if (firstEventWithPublicLongformAndShortform == null && firstEventWithPublicOrPrivateLongformAndShortform != null) {
    		loginChain.loginIfNot();
    	}
    	
    	//Precondition for toggle reporting
    	autoPlayChain.disableAllVideoPlaybackToggle();
    	ProxyRESTManager.clearLog();
    	
    	autoPlayChain.enableAllVideoPlaybackToggle();
    	
        //Cellular Video Playback ON Reporting
        expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_ON); 
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 1 check point: Cellular Video Playback ON Reporting :- Completed ------>");
		clearMap();
		
		//Autoplay ON Reporting
		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
		Logger.logMessage("<-------- 2 check point: Autoplay ON Reporting :- Completed ------>");
		clearMap();
		
		//AutoplayClips ON Reporting
		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 3 check point: AutoplayClips ON Reporting :- Completed ------>");
		clearMap();
		
		//Verify video is playing based on video playback toggle ON
		if (firstEventWithPublicLongformAndShortform != null) {
			home.enterSeriesView(firstEventWithPublicLongformAndShortform, EventWithPublicLongformAndShortformSwips);
			series.tapEpisodePlayBtn(episodeTitle);
			series.waitForEpisodeLoad(episodeTitle);
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 4 check point: Autoplay ON - Video Playing :- Completed ------>");
			series.tapEpisodePauseBtn(episodeTitle);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 5 check point: Full Episode Video Pause :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 6 check point: AutoplayClips ON - Video Playing :- Completed ------>");
			videoPlayer.playerCtr().waitForPresent().tapCenter();
			videoPlayer.smallPauseBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 7 check point: Clip Video Pause :- Completed ------>");
			clips.backBtn().waitForVisible().tap();
		} else if (firstEventWithPublicOrPrivateLongformAndShortform != null) {
			home.enterSeriesView(firstEventWithPublicOrPrivateLongformAndShortform, EventWithPublicOrPrivateLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlayerLoad();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 4 check point: Autoplay ON - Video Playing :- Completed ------>");
			series.tapEpisodePauseBtn(publicOrPrivateEpisodeTitle);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 5 check point: Full Episode Video Pause :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 6 check point: AutoplayClips ON - Video Playing :- Completed ------>");
			videoPlayer.playerCtr().waitForPresent().tapCenter();
			videoPlayer.smallPauseBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 7 check point: Clip Video Pause :- Completed ------>");
			clips.backBtn().waitForVisible().tap();
		}
		
		autoPlayChain.disableAllVideoPlaybackToggle();
		//Cellular Video Playback OFF Reporting
		expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_CELL_ANDROID_VID_PLAYBACK_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 8 check point: Cellular Video Playback OFF Reporting :- Completed ------>");
		clearMap();
		
		//Autoplay OFF Reporting
		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 9 check point: Autoplay OFF Reporting :- Completed ------>");
		clearMap();
		
		//AutoplayClips OFF Reporting
		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 10 check point: AutoplayClips OFF Reporting :- Completed ------>");
		clearMap();
    	
		//Verify video is NOT playing based on video playback toggle OFF
		if (firstEventWithPublicLongformAndShortform != null) {
			home.enterSeriesView(firstEventWithPublicLongformAndShortform, EventWithPublicLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForVisible().waitForPlayerLoad();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 11 check point: Autoplay OFF - Video NOT Playing :- Completed ------>");
			videoPlayer.largePlayBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 12 check point: Full Episode Video Play :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 13 check point: AutoplayClips OFF - Video NOT Playing :- Completed ------>");
			clips.clipPlayBtn(clipTitle).waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 14 check point: clip Video Play :- Completed ------>");
			clips.backBtn().waitForVisible().tap();
		} else if (firstEventWithPublicOrPrivateLongformAndShortform != null) {
			home.enterSeriesView(firstEventWithPublicOrPrivateLongformAndShortform, EventWithPublicOrPrivateLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlayerLoad();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 11 check point: Autoplay OFF - Video NOT Playing :- Completed ------>");
			videoPlayer.largePlayBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 12 check point: Full Episode Video Play :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 13 check point: AutoplayClips OFF - Video NOT Playing :- Completed ------>");
			clips.clipPlayBtn(publicOrPrivateClipTitle).waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 14 check point: clip Video Play :- Completed ------>");
			clips.backBtn().waitForVisible().tap();
		}
    }
    
    @TestCaseId("73454")
    @Features(GroupProps.VIDEOPLAYBACK_ON_OFF_VIDEO_PLAY_PAUSE)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEOPLAYBACK_ON_OFF_VIDEO_PLAY_PAUSE, GroupProps.SMOKE})
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void videoPlaybackTogglePlayPauseiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
    	chromecastChain.dismissChromecast();
    	ProxyUtils.disableAds();
    	
    	//Precondition for public/private Episode & clip
    	if (firstEventWithPublicLongformAndShortform == null && firstEventWithPublicOrPrivateLongformAndShortform != null && !TestRun.isBETINTLApp()) {
    		loginChain.loginIfNot();
    	}
    	
    	//Precondition for toggle reporting
    	autoPlayChain.disableAllVideoPlaybackToggle();
    	ProxyRESTManager.clearLog();
    	
    	autoPlayChain.enableAllVideoPlaybackToggle();
    	//Cellular Video Playback ON Reporting
    	expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 1 check point: Cellular Video Playback ON Reporting :- Completed ------>");
		clearMap();
		
		//Autoplay ON Reporting
		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAY_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap); 
		Logger.logMessage("<-------- 2 check point: Autoplay ON Reporting :- Completed ------>");
		clearMap();
		
		//AutoplayClips ON Reporting
		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_ON);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 3 check point: AutoplayClips ON Reporting :- Completed ------>");
		clearMap();
		
		//Verify video is playing based on video playback toggle ON
		if (firstEventWithPublicLongformAndShortform != null && !TestRun.isBETINTLApp()) {
			home.enterSeriesView(firstEventWithPublicLongformAndShortform, EventWithPublicLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForPlayerLoad();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForPlaylistLoad();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 4 check point: Autoplay ON - Video Playing :- Completed ------>");
			series.tapEpisodePauseBtn(episodeTitle);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 5 check point: Full Episode Video Pause :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 6 check point: AutoplayClips ON - Video Playing :- Completed ------>");
			videoPlayer.playerCtr().waitForPresent().tapCenter();
			videoPlayer.smallPauseBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 7 check point: Clip Video Pause :- Completed ------>");
			clips.clipCloseBtn().waitForVisible().tap();
		} else if (firstEventWithPublicOrPrivateLongformAndShortform != null && !TestRun.isBETINTLApp()) {
			home.enterSeriesView(firstEventWithPublicOrPrivateLongformAndShortform, EventWithPublicOrPrivateLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlayerLoad();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlaylistLoad();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 4 check point: Autoplay ON - Video Playing :- Completed ------>");
			series.tapEpisodePauseBtn(publicOrPrivateEpisodeTitle);
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 5 check point: Full Episode Video Pause :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap();
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 6 check point: AutoplayClips ON - Video Playing :- Completed ------>");
			videoPlayer.playerCtr().waitForPresent().tapCenter();
			videoPlayer.smallPauseBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 7 check point: Clip Video Pause :- Completed ------>");
			clips.clipCloseBtn().waitForVisible().tap();
		}
		
		autoPlayChain.disableAllVideoPlaybackToggle();
		//Cellular Video Playback OFF Reporting
		expectedMap = OmnitureLinkData.buildCellularVideoPlaybackExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_CELL_IOS_VID_PLAYBACK_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 8 check point: Cellular Video Playback OFF Reporting :- Completed ------>");
		clearMap();
		
		//Autoplay OFF Reporting
		expectedMap = OmnitureLinkData.buildAutoplayExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAY_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 9 check point: Autoplay OFF Reporting :- Completed ------>");
		clearMap();
		
		//AutoplayClips OFF Reporting
		expectedMap = OmnitureLinkData.buildAutoplayClipsExpectedMap(CommandExecutorUtils.getVersionCode(), EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		actualMap = OmnitureLogUtils.getActualMap("actName",EXPECTED_PARAM_AUTOPLAYCLIPS_TOGGLE_OFF);
		Omniture.assertOmnitureValues(expectedMap, actualMap);
		Logger.logMessage("<-------- 10 check point: AutoplayClips OFF Reporting :- Completed ------>");
		clearMap();
    	
		//Verify video is NOT playing based on video playback toggle OFF
		if (firstEventWithPublicLongformAndShortform != null && !TestRun.isBETINTLApp()) {
			home.enterSeriesView(firstEventWithPublicLongformAndShortform, EventWithPublicLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForPlayerLoad();
			series.seriesTtl(firstEventWithPublicLongformAndShortform).waitForPlaylistLoad();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 11 check point: Autoplay OFF - Video NOT Playing :- Completed ------>");
			videoPlayer.largePlayBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 12 check point: Full Episode Video Play :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 13 check point: AutoplayClips OFF - Video NOT Playing :- Completed ------>");
			clips.clipPlayBtn(clipTitle).waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 14 check point: clip Video Play :- Completed ------>");
			clips.clipCloseBtn().waitForVisible().tap();
		} else if (firstEventWithPublicOrPrivateLongformAndShortform != null && !TestRun.isBETINTLApp()) {
			home.enterSeriesView(firstEventWithPublicOrPrivateLongformAndShortform, EventWithPublicOrPrivateLongformAndShortformSwips);
			series.fullEpisodesBtn().waitForVisible().tap();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForVisible();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlayerLoad();
			series.seriesTtl(firstEventWithPublicOrPrivateLongformAndShortform).waitForPlaylistLoad();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 11 check point: Autoplay OFF - Video NOT Playing :- Completed ------>");
			videoPlayer.largePlayBtn().waitForVisible().tap().pause(StaticProps.MINOR_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 12 check point: Full Episode Video Play :- Completed ------>");
			series.watchXtrasBtn().waitForVisible().tap();
			videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
			Logger.logMessage("<-------- 13 check point: AutoplayClips OFF - Video NOT Playing :- Completed ------>");
			clips.clipPlayBtn(publicOrPrivateClipTitle).waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE); //Until media control disappear
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
			Logger.logMessage("<-------- 14 check point: clip Video Play :- Completed ------>");
			clips.clipCloseBtn().waitForVisible().tap();
		}
    }
    
    public void clearMap() {
    	expectedMap.clear();
   	 	actualMap.clear();
    }
}
