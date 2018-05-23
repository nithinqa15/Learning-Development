package com.viacom.test.vimn.uitests.tests.ads;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;

import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PrerollsForClipsHomeToClipsTest extends BaseTest {
    //Declare page objects and actions
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    AutoPlayChain clipsAutoPlay;
    Home home;
    Clips clips;
    Ads ads;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;

    //Declare data
    String seriesTitle;
    Integer numOfSwipes;
    String clipTitle;
    Boolean prerollsForClips = false;
    Boolean shortFormEnabled = false;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PrerollsForClipsHomeToClipsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        clipsAutoPlay = new AutoPlayChain();
        home = new Home();
        clips = new Clips();
        ads = new Ads();
        videoPlayer = new VideoPlayer();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        prerollsForClips = MainConfig.isPrerollsForClipsEnabled();

        if (shortFormEnabled && prerollsForClips) {
            PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();
            seriesTitle = propertyResult.getPropertyTitle();
            numOfSwipes = propertyResult.getNumOfSwipes();
            clipTitle = propertyResult.getClips().getFirstClip().getClipTitle();

        }
    }

    @TestCaseId("36105")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = { GroupProps.FULL, GroupProps.VIDEO_ADS })
    @Parameters({ ParamProps.ANDROID, ParamProps.BET_DOMESTIC_APP, 
		    	  ParamProps.TVLAND_APP, ParamProps.PARAMOUNT_APP, 
		    	  ParamProps.CMT_APP, ParamProps.VH1_APP})
    public void prerollsForClipsHomeToClipsAndroidTest() {
        if (shortFormEnabled && prerollsForClips && seriesTitle != null) {

            //Ensure Ads play
            ProxyUtils.rewriteTSLA(1);

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            clipsAutoPlay.enableAutoPlayClip();
            home.flickRightToSeriesThumb(seriesTitle, numOfSwipes);
            home.enterClipsByTappingOnBackground();
            clips.clipTitle(clipTitle).isVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            videoPlayer.playerCtr().waitForPresent().tapCenter();
            ads.pauseAdsBtn().isPresent();
        } else {
            String message = "Prerolls for Clips is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() + " so skipping test";
            Logger.logMessage(message);
            throw new SkipException(message);

        }
    }

    @TestCaseId("36105")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = {GroupProps.BROKEN, GroupProps.VIDEO_ADS})
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, 
		    	  ParamProps.CMT_APP, ParamProps.VH1_APP, 
		    	  ParamProps.PARAMOUNT_APP, ParamProps.BET_DOMESTIC_APP })
    public void prerollsForClipsHomeToClipsiOSTest() {
        // To Do

    }
}
