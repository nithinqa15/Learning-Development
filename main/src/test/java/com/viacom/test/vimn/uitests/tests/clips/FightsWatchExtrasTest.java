package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
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

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class FightsWatchExtrasTest extends BaseTest {
    //declare pageobjects and action chains
    Home home;
    Series series;
    Clips clips;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;
    ChromecastChain chromecastChain;
    AlertsChain alertsChain;
    SplashChain splashChain;
    AutoPlayChain autoplayChain;

    // declare data
    String fightTitle;
    String extrasTitle;
    Boolean shortFormEnabled;
    Integer numberOfSwipes;


    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightsWatchExtrasTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        autoplayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        videoPlayer = new VideoPlayer();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResult propertyResult = propertyFilter.withFight().propertyFilter().getFirstProperty();
            fightTitle = propertyResult.getPropertyTitle();
            extrasTitle = propertyResult.getClips().getFirstClip().getClipTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();
        }
    }

    @TestCaseId("48082")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.FIGHTS_CONTENT})
    @Parameters({ParamProps.ANDROID, ParamProps.PARAMOUNT_APP})
	public void fightsWatchExtarsAndroidTest() {

		if (!shortFormEnabled) {
			String message = "Short form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ "so skipping the test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoplayChain.enableAutoPlay();

			home.enterClipsView(fightTitle, numberOfSwipes);
			clips.verifyClipTitleIsVisible(extrasTitle);
			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		}
	}

    @TestCaseId("48082")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.FIGHTS_CONTENT})
    @Parameters({ParamProps.IOS, ParamProps.PARAMOUNT_APP})
    public void fightsWatchExtrasiOSTest (){

        if (!shortFormEnabled) {
            String message = "Short form is not enabled on "+ TestRun.getAppName() + " " + TestRun.getLocale() + "so skipping the test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException (message);
        } else {
            splashChain.splashAtRest();
            alertsChain.dismissAlerts();
            chromecastChain.dismissChromecast();
            autoplayChain.enableAutoPlay();

            home.enterClipsView(fightTitle, numberOfSwipes);
            clips.verifyClipTitleIsVisible(extrasTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        }
    }
}
