package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_BACKGROUND_VIDEO_ENGAGEMENT_ON_HOME_SCREEN;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.BackgroundVideoNotEnabledException;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithBackgroundVideoException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureBackgroundVideoEngagementHomeScreenTest extends BaseTest {

    AppDataFeed appDataFeed;
    Home home;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    VideoPlayer videoPlayer;
    SettingsChain settingsChain;
    HomePropertyFilter propertyFilter;

    Boolean backgroundServiceEnabled = false;
    Boolean hasSeriesWithBackgroundVideo = false;
    String seriesTitle;
    Integer numberOfSwipes;
    String seriesPosition;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureBackgroundVideoEngagementHomeScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        expectedMap = new HashMap<>();
        home = new Home();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        videoPlayer = new VideoPlayer();
        settingsChain = new SettingsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        backgroundServiceEnabled = MainConfig.isBackgroundVideoServiceEnabled();
        if (true) {
            PropertyResults propertyResults = propertyFilter.withBackgroundVideo().propertyFilter();
            hasSeriesWithBackgroundVideo = !propertyResults.isEmpty();
            if (hasSeriesWithBackgroundVideo) {
                PropertyResult propertyResult = propertyResults.getFirstProperty();
                numberOfSwipes = propertyResult.getNumOfSwipes();
                seriesTitle = propertyResult.getPropertyTitle();
                seriesPosition = String.valueOf(propertyResult.getPositionOnCarousel());
            }
        }

    }

    @TestCaseId("35906")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureBackgroundVideoEngagementHomeScreenAndroidTest() {

        if (backgroundServiceEnabled) {
            if (hasSeriesWithBackgroundVideo) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                String version = settingsChain.getVersion();

                home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
                videoPlayer.verifyVideoIsPlayingByScreenshots(5);
                home.enterClipsByTappingOnBackground();

                expectedMap = OmnitureLinkData.buildBackgroundVideoEngagementHomeScreenExpectedMap(seriesTitle, seriesPosition, version);
                actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_BACKGROUND_VIDEO_ENGAGEMENT_ON_HOME_SCREEN);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "There are no series with background video, skipping test.";
                Logger.logMessage(message);
                throw new NoSeriesWithBackgroundVideoException(message);
            }
        } else {
            String message = "Background video is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " so skipping test.";
            Logger.logMessage(message);
            throw new BackgroundVideoNotEnabledException(message);
        }
    }
    
    @TestCaseId("35906")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureBackgroundVideoEngagementHomeScreeniOSTest() {

        if (backgroundServiceEnabled) {
            if (hasSeriesWithBackgroundVideo) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                String version = settingsChain.getVersion();

                home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
                videoPlayer.verifyVideoIsPlayingByScreenshots(5);
                home.enterClipsByTappingOnBackground();

                expectedMap = OmnitureLinkData.buildBackgroundVideoEngagementHomeScreenExpectedMap(seriesTitle, seriesPosition, version);
                actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_BACKGROUND_VIDEO_ENGAGEMENT_ON_HOME_SCREEN);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "There are no series with background video, skipping test.";
                Logger.logMessage(message);
                throw new NoSeriesWithBackgroundVideoException(message);
            }
        } else {
            String message = "Background video is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " so skipping test.";
            Logger.logMessage(message);
            throw new BackgroundVideoNotEnabledException(message);
        }
    }
}
