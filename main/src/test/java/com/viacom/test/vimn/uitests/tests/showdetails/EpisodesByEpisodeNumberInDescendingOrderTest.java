package com.viacom.test.vimn.uitests.tests.showdetails;

import java.util.List;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.EpisodeOrderException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class EpisodesByEpisodeNumberInDescendingOrderTest extends BaseTest {

    //Declare page objects/actions
    AppDataFeed appDataFeed;
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    Home home;
    Series series;
    SelectSeasonChain selectSeasonChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    //Declare data
    Integer numberOfSwipes;
    String seriesTitle;
    Boolean seriesHasSeasonOrderedByEpisodeNumberDescending = false;
    List<String> episodesTitles;
    Integer seasonNumber;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public EpisodesByEpisodeNumberInDescendingOrderTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        series = new Series();
        selectSeasonChain = new SelectSeasonChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
        Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
        String promoListFeedURL = FeedFactory.getPromoListFeedURL();
        List<String> seriesIDs = appDataFeed.getSeriesIds(promoListFeedURL);
        for (String seriesID : seriesIDs) {
            String seriesURL = FeedFactory.getSeriesFeedURL(seriesID);
            numberOfSwipes = seriesIDs.indexOf(seriesID) + numberOfSeriesInFeaturedFeed;
            seriesTitle = appDataFeed.getEpisodesParentSeriesTitle(seriesURL);
            seriesHasSeasonOrderedByEpisodeNumberDescending = appDataFeed.hasSeasonOrderedByEpisodeNumberDescending(seriesURL);
            if (seriesHasSeasonOrderedByEpisodeNumberDescending) {
                Long seasonNumberLong = appDataFeed.getSeasonOrderedByEpisodeNumberDescendingNumber(seriesURL);
                episodesTitles = appDataFeed.getSortedEpisodeTitlesbyEpisodeNumberInDescendingOrder(seriesURL, seasonNumberLong);
                if (episodesTitles.size() != 0) {
                    seasonNumber = seasonNumberLong.intValue();
                    break;
                }
            }
        }
    }

    @TestCaseId("10902")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void episodesByEpisodeNumberInDescendingOrderAndroidTest() {

        if (seriesHasSeasonOrderedByEpisodeNumberDescending) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(seasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.verifyEpisodesOrder(episodesTitles);
        } else {
            String message = "There's no series with episodes ordered by episode number and in descending order on " +
                    TestRun.getAppName() + " " + TestRun.getLocale() + " so skipping test";
            Logger.logMessage(message);
            throw new EpisodeOrderException(message);
        }

    }

    @TestCaseId("10902")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS  }) //Need to work with feed for all sort type
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void episodesByEpisodeNumberInDescendingOrderiOSTest() {

        if (seriesHasSeasonOrderedByEpisodeNumberDescending) {
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            autoPlayChain.disableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(seasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.verifyEpisodesOrder(episodesTitles);
        } else {
            String message = "There's no series with episodes ordered by episode number and in descending order on " +
                    TestRun.getAppName() + " " + TestRun.getLocale() + " so skipping test";
            Logger.logMessage(message);
            throw new EpisodeOrderException(message);
        }
     }
  }
