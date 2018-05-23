package com.viacom.test.vimn.uitests.tests.omniture.playercalls;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;

import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmniturePlayerData;
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
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CLIP_START;

public class OmnitureClipStartPlayerCallTest extends BaseTest {

    //Declare page objects/actions
    AppDataFeed appDataFeed;
    SplashChain splashChain;
    Home home;
    Clips clips;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    AlertsChain alertsChain;
    VideoPlayer videoPlayer;

    //Declare data
    Map<String, String> expectedMap;
    String seriesTitle;
    String clipTitle;
    Integer numberOfSwipes;
    String mgid;
    String clipDuration;
    Boolean hasClips = false;
    Boolean shortFormIsEnabled = false;
    HomePropertyFilter propertyFilter;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureClipStartPlayerCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        home = new Home();
        clips = new Clips();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        videoPlayer = new VideoPlayer();
        expectedMap = new HashMap<>();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        
        shortFormIsEnabled = MainConfig.isShortFormEnabled();
        if (shortFormIsEnabled) {
            PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();
            hasClips = propertyResult.hasClips();
            seriesTitle = propertyResult.getPropertyTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();
            clipTitle = propertyResult.getClips().getFirstClip().getClipTitle();
            mgid = propertyResult.getClips().getFirstClip().getClipMGID();
            clipDuration = String.valueOf(propertyResult.getClips().getFirstClip().getClipDurationInMilliSeconds()/1000);
        }
    }

    @TestCaseId("35934")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS, GroupProps.OMNITURE, Config.GroupProps.BENTO_SMOKE, GroupProps.VIDEO_PLAYER })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureClipStartPlayerCallAndroidTest() {

        if (shortFormIsEnabled) {
            if (hasClips) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                autoPlayChain.enableAutoPlayClip();
                ProxyUtils.disableAds();

                home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
                home.enterClipsByTappingOnBackground();
                clips.clipTitle(clipTitle).waitForVisible();

                videoPlayer.verifyVideoIsPlayingByScreenshots(5);

                expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(seriesTitle, clipTitle, mgid, clipDuration);
                Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "There are no series with clips on: " + TestRun.getAppName() + " " + TestRun.getLocale()
                        + " so skipping test";
                Logger.logMessage(message);
                throw new NoSeriesWithClipsException(message);
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
                    + " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

    @TestCaseId("35934")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.CLIPS, GroupProps.OMNITURE, GroupProps.VIDEO_PLAYER, GroupProps.BENTO_SMOKE })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureClipStartPlayerCalliOSTest() {
          
		if (shortFormIsEnabled) {
		    if (hasClips) {
		        splashChain.splashAtRest();
		        chromecastChain.dismissChromecast();
		        autoPlayChain.enableAutoPlayClip();
		        ProxyUtils.disableAds();
		
		        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
		        home.watchExtrasBtn().waitForVisible().tap();
		
		        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		
		        expectedMap = OmniturePlayerData.buildClipStartPlayerExpectedMap(seriesTitle, clipTitle, mgid, clipDuration);
		        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_CLIP_START);
		        Omniture.assertOmnitureValues(expectedMap, actualMap);
		    } else {
		        String message = "There are no series with clips on: " + TestRun.getAppName() + " " + TestRun.getLocale()
		                + " so skipping test";
		        Logger.logMessage(message);
		        throw new NoSeriesWithClipsException(message);
		    }
		} else {
		    String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
		            + " so skipping test";
		    Logger.logMessage(message);
		    throw new ShortFormNotEnabledException(message);
		}
    }
}
