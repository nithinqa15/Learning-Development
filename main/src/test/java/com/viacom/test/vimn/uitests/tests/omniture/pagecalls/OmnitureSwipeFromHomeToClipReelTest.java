package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoSeriesWithClipsException;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_SWIPE_FROM_HOME_TO_CLIP_REEL;

public class OmnitureSwipeFromHomeToClipReelTest extends BaseTest {

    AppDataFeed appDataFeed;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    OmniturePageData omniturePageData;
    Home home;
    Clips clips;

    String seriesTtl;
    String clipTitle;
    Boolean shortFormEnabled = false;
    Boolean seriesHasShortForms = false;
    Integer numberOfSwipes;
    Map<String, String> expectedMap;
    Map<String, String> dynamicMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureSwipeFromHomeToClipReelTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        omniturePageData = new OmniturePageData();
        home = new Home();
        clips = new Clips();
        expectedMap = new HashMap<>();
        dynamicMap = new HashMap<>();

        Integer seriesPosition = null;

        String mainFeedURL = FeedFactory.getAppMainFeedURL();
        shortFormEnabled = appDataFeed.isShortFormEnabled(mainFeedURL);
        if (shortFormEnabled) {
            List<String> seriesTitles = new ArrayList<>();
            List<String> seriesIDs = new ArrayList<>();
            String featuredFeed = FeedFactory.getFeaturedPropertyFeedURL();
            String promoFeed = FeedFactory.getPromoListFeedURL();
            seriesIDs.addAll(appDataFeed.getSeriesIds(featuredFeed));
            seriesIDs.addAll(appDataFeed.getSeriesIds(promoFeed));
            for (String seriesID : seriesIDs) {
                String seriesURL = FeedFactory.getSeriesFeedURL(seriesID);
                String clipsURL = FeedFactory.getClipsFeedURL(seriesID);
                seriesTtl = appDataFeed.getPropertyTitle(seriesURL);
                seriesTitles.add(seriesTtl);
                seriesHasShortForms = appDataFeed.hasShortformContent(clipsURL);
                if (seriesHasShortForms) {
                    clipTitle = appDataFeed.getClipTitles(clipsURL).get(0);
                    numberOfSwipes = seriesIDs.indexOf(seriesID);
                    seriesPosition = seriesTitles.size();
                    break;
                }
            }
        }

        dynamicMap.put("seriesTitle", seriesTtl);
        dynamicMap.put("seriesPosition", String.valueOf(String.valueOf(seriesPosition)));

    }

    @Deprecated
    @TestCaseId("")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.CLIPS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureSwipeFromHomeToClipReelAndroidTest() {

        if (shortFormEnabled) {
            if (seriesHasShortForms) {
                splashChain.splashAtRest();
                chromecastChain.dismissChromecast();

                home.enterClipsViewSwipingUp(seriesTtl, numberOfSwipes);

                clips.clipTitle(clipTitle).waitForVisible();

                Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_SWIPE_FROM_HOME_TO_CLIP_REEL);
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

