package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_VERTICAL_SWIPE;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
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
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureVerticalSwipeOnClipsTest extends BaseTest {

    AppDataFeed appDataFeed;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    Home home;
    Clips clips;
    SettingsChain settingsChain;
    HomePropertyFilter propertyFilter;

    String seriesTtl;
    String clipTitle;
    String version;
    Boolean shortFormEnabled = false;
    Boolean seriesHasShortForms = false;
    Integer numberOfSwipes;
    String seriesPosition;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureVerticalSwipeOnClipsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        clips = new Clips();
        expectedMap = new HashMap<>();
        settingsChain = new SettingsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        shortFormEnabled = MainConfig.isShortFormEnabled();
        if (shortFormEnabled) {
            PropertyResults propertyResults = propertyFilter.withClips().propertyFilter();
            seriesHasShortForms = !propertyResults.isEmpty();
            if (seriesHasShortForms) {
                PropertyResult propertyResult = propertyResults.getFirstProperty();
                seriesTtl = propertyResult.getPropertyTitle();
                numberOfSwipes = propertyResult.getNumOfSwipes();
                seriesPosition = String.valueOf(propertyResult.getPositionOnCarousel());
                clipTitle = propertyResult.getClips().getFirstClip().getClipTitle();
            }
        }

    }

    @Deprecated
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureVerticalSwipeOnClipsAndroidTest() {

        if (shortFormEnabled) {
            if (seriesHasShortForms) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();
                version = settingsChain.getVersion();

                home.flickRightToSeriesThumb(seriesTtl, numberOfSwipes);
                home.watchExtrasBtn().waitForVisible().tap();

                clips.clipTitle(clipTitle).waitForVisible();
                clips.clipTitle(clipTitle).scrollDownOnClipsView(StaticProps.NORMAL_SCROLL).waitForNotPresentOrVisible();

                expectedMap = OmnitureLinkData.buildVerticalSwipeOnClipsExpectedMap(seriesTtl, seriesPosition, version);
                Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_VERTICAL_SWIPE);
                Omniture.assertOmnitureValues(expectedMap, actualMap);
            } else {
                String message = "There are no series with clips on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                        " so skipping test";
                Logger.logMessage(message);
                throw new NoSeriesWithClipsException(message);
            }
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

}
