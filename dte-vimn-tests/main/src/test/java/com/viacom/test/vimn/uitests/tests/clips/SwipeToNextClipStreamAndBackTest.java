package com.viacom.test.vimn.uitests.tests.clips;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ShortFormNotEnabledException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.*;

public class SwipeToNextClipStreamAndBackTest extends BaseTest {

    //Declare page objects and actions
    SplashChain splashChain;
    Home home;
    Series series;
    Clips clips;
    AutoPlayChain autoPlayChain;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;

    //Declare data
    List<String> clipTitles;
    Map<String, List<String>> clipsTitlesBySeries;
    String seriesTitle;
    String clipTitle;
    String nextClipTitle;
    Boolean shortForm;
    Integer numberOfSwipes;


    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SwipeToNextClipStreamAndBackTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        clips = new Clips();
        autoPlayChain = new AutoPlayChain();
        appDataFeed = new AppDataFeed();
        clipsTitlesBySeries = new LinkedHashMap<>();
        chromecastChain = new ChromecastChain();

        String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
        Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
        String mainFeedURL = FeedFactory.getAppMainFeedURL();
        shortForm = appDataFeed.isShortFormEnabled(mainFeedURL);
        if (shortForm) {
            String promoListFeedURL = FeedFactory.getPromoListFeedURL();
            List<String> seriesIDs = appDataFeed.getSeriesIds(promoListFeedURL);
            for (String seriesID : seriesIDs) {
                String clipsURL = FeedFactory.getClipsFeedURL(seriesID);
                String seriesURL = FeedFactory.getSeriesFeedURL(seriesID);
                numberOfSwipes = seriesIDs.indexOf(seriesID) + numberOfSeriesInFeaturedFeed;
                if (appDataFeed.hasShortformContent(clipsURL)) {
                    String seriesTitles = appDataFeed.getEpisodesParentSeriesTitle(seriesURL);
                    clipTitles = appDataFeed.getClipTitles(clipsURL);
                    clipsTitlesBySeries.put(seriesTitles, clipTitles);
                    clipTitles = null;
                    if (clipsTitlesBySeries.size() == 2) {
                        break;
                    }
                }
            }

            seriesTitle = clipsTitlesBySeries.keySet().iterator().next();
            List<List<String>> lists = new ArrayList<>(clipsTitlesBySeries.values());
            clipTitle = lists.get(0).get(0);
            nextClipTitle = lists.get(1).get(0);
        }

    }

    @Deprecated // VB - 9/22/17 - Swipe between series/clips functionality was removed
               // with https://github.com/vimn-north/PlayPlex-Android/pull/3583/
    @TestCaseId("")
    @Features(Config.GroupProps.CLIPS)
    @Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.CLIPS })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS})
    public void SwipeToNextClipStreamAndBackAndroidTest() {

        if (shortForm) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlayClip();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.switchToClips();
            clips.clipTitle(clipTitle).waitForVisible();
            clips.flickToNextClipsView(nextClipTitle);
            clips.flickToPreviousClipsView(clipTitle);
        } else {
            String message = "Short Form is not enabled on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ShortFormNotEnabledException(message);
        }

    }
}