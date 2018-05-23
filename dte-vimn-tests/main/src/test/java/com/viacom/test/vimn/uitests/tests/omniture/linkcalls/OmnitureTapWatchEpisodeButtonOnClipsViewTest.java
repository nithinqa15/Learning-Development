package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_TAP_WATCH_EPISODE_BUTTON_CLIPS_VIEW;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoClipWithRelatedEpisodeException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureTapWatchEpisodeButtonOnClipsViewTest extends BaseTest {

    AppDataFeed appDataFeed;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    SettingsChain settingsChain;
    Home home;
    Clips clips;
    AlertsChain alertsChain;
    HomePropertyFilter propertyFilter;

    String seriesTitle;
    String clipTitle;
    String version;
    Integer numberOfSwipes;
    Integer numberOfFlicks;
    Boolean shortFormEnabled = false;
    Boolean hasClipWithRelatedEpisode = false;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;
    String seriesPosition;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureTapWatchEpisodeButtonOnClipsViewTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        settingsChain = new SettingsChain();
        home = new Home();
        clips = new Clips();
        alertsChain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        expectedMap = new HashMap<>();

        String mainFeed = FeedFactory.getAppMainFeedURL();
        shortFormEnabled = appDataFeed.isShortFormEnabled(mainFeed);
        if (shortFormEnabled) {
        	   	PropertyResults propertyResults = propertyFilter.withClips().propertyFilter();
        	   	for (int i=0; i<propertyResults.size(); i++) {
        	   		if (!hasClipWithRelatedEpisode) {
        	   	   		for (int j=0; j<propertyResults.get(i).getClips().size(); j++) {
            	   			hasClipWithRelatedEpisode = propertyResults.get(i).getClips().get(j).hasRelatedEpisodes();
                	   		if (hasClipWithRelatedEpisode) {
                	   			seriesTitle = propertyResults.get(i).getPropertyTitle();
                	   			numberOfSwipes = propertyResults.get(i).getNumOfSwipes();
                	   			clipTitle = propertyResults.get(i).getClips().get(j).getClipTitle();
                	   			numberOfFlicks = propertyResults.get(i).getClips().get(j).getClipPosition();
                	   			break;
                	   		}
        	   	   		}
        	   		} else {
        	   			break;
        	   		}
        	
        	   	}
        }
    }

    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureTapWatchEpisodeButtonOnClipsViewAndroidTest() {

        if (shortFormEnabled) {
            if (hasClipWithRelatedEpisode) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                home.flickRightToSeriesThumb(seriesTitle, numberOfFlicks);
                home.enterClipsByTappingOnBackground();

                clips.tapOnWatchEpisodeButton(clipTitle, numberOfFlicks);

                expectedMap = OmnitureLinkData.buildOmnitureTapWatchEpisodeButtonOnClipsViewExpectedMap(seriesTitle, null, clipTitle, seriesPosition);
                actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TAP_WATCH_EPISODE_BUTTON_CLIPS_VIEW);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "";
                Logger.logMessage(message);
                throw new NoClipWithRelatedEpisodeException(message);
            }
        } else {
            String message = "";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }
    
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureTapWatchEpisodeButtonOnClipsViewiOSTest() {
    	
    	  splashChain.splashAtRest();
          alertsChain.dismissAlerts();

        if (shortFormEnabled) {
            if (hasClipWithRelatedEpisode) {
                splashChain.splashAtRest();

                home.flickRightToSeriesThumb(seriesTitle, numberOfFlicks);
                home.enterClipsByTappingOnBackground();
                home.watchExtrasBtn().pause(StaticProps.MEDIUM_PAUSE);

                clips.tapOnWatchEpisodeButton(clipTitle, numberOfFlicks);

                expectedMap = OmnitureLinkData.buildOmnitureTapWatchEpisodeButtonOnClipsViewExpectedMap(seriesTitle, version, clipTitle, seriesPosition);
                actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_TAP_WATCH_EPISODE_BUTTON_CLIPS_VIEW);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "";
                Logger.logMessage(message);
                throw new NoClipWithRelatedEpisodeException(message);
            }
        } else {
            String message = "";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }
}
