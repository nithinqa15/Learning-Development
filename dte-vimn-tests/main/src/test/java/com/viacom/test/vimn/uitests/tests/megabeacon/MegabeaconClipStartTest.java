package com.viacom.test.vimn.uitests.tests.megabeacon;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.megabeacon.MegabeaconLogUtils;
import com.viacom.test.vimn.common.megabeacon.MegabeaconPlayerData;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_CLIP_START;

public class MegabeaconClipStartTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Home home;
    Clips clips;
    Series series;
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

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MegabeaconClipStartTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        chromecastChain = new ChromecastChain();
        autoPlayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        videoPlayer = new VideoPlayer();
        expectedMap = new HashMap<>();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormIsEnabled = MainConfig.isShortFormEnabled();
        if (shortFormIsEnabled) {
            PropertyResult seriesResult = propertyFilter.withClips().propertyFilter().getFirstProperty();
            hasClips = seriesResult.hasClips();
            seriesTitle = seriesResult.getPropertyTitle();
            numberOfSwipes = seriesResult.getNumOfSwipes();
            clipTitle = seriesResult.getClips().getFirstClip().getClipTitle();
            mgid = seriesResult.getClips().getFirstClip().getClipMGID();
            clipDuration = String.valueOf(seriesResult.getClips().getFirstClip().getClipDurationInMilliSeconds() / 1000);
        }
    }

	//@TestCaseId("35934")
	@Features(Config.GroupProps.MEGABEACON)
	@Test(groups = { Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE, Config.GroupProps.CLIPS, Config.GroupProps.VIDEO_PLAYER })
	@Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
	        Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
	        Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
	public void megabeaconClipStartPlayerCallAndroidTest() {
	
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
	            Map<String, String> actualMap = MegabeaconLogUtils.getActualMap("activity", EXPECTED_PARAM_CLIP_START);
	            expectedMap = MegabeaconPlayerData.buildClipStartPlayerExpectedMap(seriesTitle, clipTitle, mgid, clipDuration);
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
        
    //@TestCaseId("35934")
    @Features(Config.GroupProps.MEGABEACON)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.BENTO_SMOKE, Config.GroupProps.CLIPS, Config.GroupProps.VIDEO_PLAYER })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.TVLAND_APP, Config.ParamProps.CC_INTL_APP, Config.ParamProps.CC_DOMESTIC_APP,
            Config.ParamProps.MTV_INTL_APP, Config.ParamProps.MTV_DOMESTIC_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP, Config.ParamProps.BET_DOMESTIC_APP })
    public void megabeaconClipStartPlayerCalliOSTest() {

        if (shortFormIsEnabled) {
            if (hasClips) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                autoPlayChain.enableAutoPlayClip();
                ProxyUtils.disableAds();

                home.enterSeriesView(seriesTitle, numberOfSwipes);
                series.watchXtrasBtn().waitForVisible().tap();
                clips.clipTitle(clipTitle).waitForVisible();

                videoPlayer.verifyVideoIsPlayingByScreenshots(5);
                clipDuration = clipDuration + ".0"; // Actual value coming from feed with .0
                Map<String, String> actualMap = MegabeaconLogUtils.getActualMap("activity", EXPECTED_PARAM_CLIP_START);
                expectedMap = MegabeaconPlayerData.buildClipStartPlayerExpectedMap(seriesTitle, clipTitle, mgid, clipDuration);
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
