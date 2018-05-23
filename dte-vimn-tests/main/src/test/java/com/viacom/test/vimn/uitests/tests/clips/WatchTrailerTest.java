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


/**
 * Created by chengh on 1/19/18.
 */
public class WatchTrailerTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    AutoPlayChain autoplayChain;
    AlertsChain alertschain;
    ChromecastChain chromecastChain;
    Home home;
    Clips clips;
    Series series;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;

    //Declare data
    String mainFeedURL;
    String trailerTitle;
    String movieTitle;
    Integer numberOfSwipes;
    Boolean shortFormEnabled = true;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public WatchTrailerTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        autoplayChain = new AutoPlayChain();
        alertschain = new AlertsChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        videoPlayer = new VideoPlayer();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResult propertyResult = propertyFilter.withMovie().propertyFilter().getFirstProperty();

            movieTitle = propertyResult.getPropertyTitle();
            trailerTitle = propertyResult.getClips().getFirstClip().getClipTitle();
            numberOfSwipes = propertyResult.getNumOfSwipes();
        }
    }

    @TestCaseId("47610")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.MOVIES_CONTENT})
    @Parameters({ParamProps.ANDROID, ParamProps.PARAMOUNT_APP})
	public void watchTrailerAndroidTest() {

		if (!shortFormEnabled) {
			String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			Logger.logMessage(message);
			throw new ShortFormNotEnabledException(message);
		} else {
			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();
			autoplayChain.enableAutoPlayClip();

			home.enterTrailersView(movieTitle, numberOfSwipes);
			clips.verifyClipTitleIsVisible(trailerTitle);

			videoPlayer.verifyVideoIsPlayingByScreenshots(5);
		}
	}

    @TestCaseId("47610")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.MOVIES_CONTENT})
    @Parameters({Config.ParamProps.IOS, ParamProps.PARAMOUNT_APP})
    public void watchTraileriOSTest() {

        if(!shortFormEnabled) {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
        else {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            alertschain.dismissAlerts();
            autoplayChain.enableAutoPlayClip();

            home.enterTrailersView(movieTitle, numberOfSwipes);
            clips.verifyClipTitleIsVisible(trailerTitle);

            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        }
    }
}

