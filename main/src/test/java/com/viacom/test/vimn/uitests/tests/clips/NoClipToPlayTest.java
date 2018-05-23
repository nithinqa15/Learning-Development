package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithEpisodesOnlyException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class NoClipToPlayTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    Home home;
    Clips clips;
    Series series;
    ChromecastChain chromecastChain;
    HomePropertyFilter homePropertyFilter;
    AlertsChain alertsChain;

    //Declare data
    String seriesTitle;
    Boolean brandHasShortForm = false;
    Integer numberOfSwipes;
    Boolean hasSeriesWithEpisodesOnly = false;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public NoClipToPlayTest(String runParams) {super.setRunParams(runParams);}

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        clips = new Clips();
        series = new Series();
        chromecastChain = new ChromecastChain();
        homePropertyFilter = new HomePropertyFilter(NON_PAGINATED);
        alertsChain = new AlertsChain();

        brandHasShortForm = MainConfig.isShortFormEnabled();
        if (brandHasShortForm) {
            PropertyResult propertyResult = homePropertyFilter.withEpisodes().withEpisodesOnly().propertyFilter().getFirstProperty();
            if (propertyResult != null) {
            	hasSeriesWithEpisodesOnly = true;
                seriesTitle = propertyResult.getPropertyTitle();
                numberOfSwipes = propertyResult.getNumOfSwipes();
            } 
        }

    }

    @TestCaseId("")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void noClipToPlayAndroidTest() {

        if (brandHasShortForm) {
            if (hasSeriesWithEpisodesOnly) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                home.enterSeriesView(seriesTitle, numberOfSwipes);
                series.verifySeriesHasNoClipsTab();
            } else {
                throw new NoSeriesWithEpisodesOnlyException("All series have shortform content. Skipping test execution.");
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }
    
    @TestCaseId("")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.CLIPS})
    @Parameters({Config.ParamProps.IOS, Config.ParamProps.ALL_APPS})
    public void noClipToPlayiOSTest() {

        if (brandHasShortForm) {
            if (hasSeriesWithEpisodesOnly) {
                splashChain.splashAtRest();
                alertsChain.dismissAlerts();

                home.enterSeriesView(seriesTitle, numberOfSwipes);
                series.verifySeriesHasNoClipsTab();
            } else {
                throw new NoSeriesWithEpisodesOnlyException("All series have shortform content. Skipping test execution.");
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }

}
