package com.viacom.test.vimn.uitests.tests.omniture.linkcalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HORIZONTAL_SWIPE;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.filters.ShortformResults;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLinkData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureHorizontalSwipeOnClipsTest extends BaseTest {

    AppDataFeed appDataFeed;
    OmnitureLinkData omnitureLinkData;
    Home home;
    Clips clips;
    SplashChain splashChain;
    HomePropertyFilter propertyFilter;
    SettingsChain settingsChain;

    String seriesTitle;
    String nextSeriesTitle;
    Integer numberOfSwipes;
    String clipTitle;
    String nextClipTitle;
    Integer positionOnCarousel;
    Boolean shortFormEnabled = false;
    String version;
    Map<String, String> expectedMap;
    Map<String, String> dynamicMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureHorizontalSwipeOnClipsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        omnitureLinkData = new OmnitureLinkData();
        expectedMap = new HashMap<>();
        home = new Home();
        clips = new Clips();
        splashChain = new SplashChain();
        dynamicMap = new HashMap<>();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        settingsChain = new SettingsChain();

        String mainFeedURL = FeedFactory.getAppMainFeedURL();
        shortFormEnabled = appDataFeed.isShortFormEnabled(mainFeedURL);
        if (shortFormEnabled) {
            PropertyResults propertyResults = propertyFilter.withClips().propertyFilter();
            PropertyResult firstSeries = propertyResults.getFirstProperty();
            seriesTitle = firstSeries.getPropertyTitle();
            numberOfSwipes = firstSeries.getNumOfSwipes();
            positionOnCarousel = firstSeries.getPositionOnCarousel();

            ShortformResults clipResults = firstSeries.getShortformFilter().clipsFilter();
            clipTitle = clipResults.getClipTitles().get(0);

            PropertyResult nextSeries = propertyResults.get(1);
            nextSeriesTitle = nextSeries.getPropertyTitle();
            ShortformResults nextClipResults = nextSeries.getShortformFilter().clipsFilter();
            nextClipTitle = nextClipResults.getClipTitles().get(0);

        }

        dynamicMap.put("seriesTitle", seriesTitle);
        dynamicMap.put("nextSeriesTitle", nextSeriesTitle);
        dynamicMap.put("seriesPosition", String.valueOf(positionOnCarousel));

    }

    @Deprecated // VB - 9/22/17 - Swipe between series/clips functionality was removed
                // with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureHorizontalSwipeOnClipsAndroidTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();

            version = settingsChain.getVersion();
            dynamicMap.put("version", version);

            home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.clipTitle(clipTitle).flickToNextSeriesFromSeriesView().waitForNotPresentOrVisible();
            clips.clipTitle(nextClipTitle).waitForVisible();

            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HORIZONTAL_SWIPE);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }

    @Deprecated
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.CLIPS, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureHorizontalSwipeOnClipsiOSTest() {

        if (shortFormEnabled) {
            splashChain.splashAtRest();

            home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.clipTitle(clipTitle).flickToNextSeriesFromSeriesView().waitForNotPresentOrVisible();
            clips.clipTitle(nextClipTitle).waitForVisible();

            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HORIZONTAL_SWIPE);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }
    }
}
