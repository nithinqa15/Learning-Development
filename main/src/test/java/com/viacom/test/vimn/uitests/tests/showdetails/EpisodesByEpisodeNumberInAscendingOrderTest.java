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
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
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

public class EpisodesByEpisodeNumberInAscendingOrderTest extends BaseTest {

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
    Boolean seriesHasSeasonOrderedByEpisodeNumberAscending = false;
    List<String> episodesTitles;
    Integer seasonNumber;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public EpisodesByEpisodeNumberInAscendingOrderTest(String runParams) {
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
            seriesHasSeasonOrderedByEpisodeNumberAscending = appDataFeed.hasSeasonOrderedByEpisodeNumberAscending(seriesURL);
            if (seriesHasSeasonOrderedByEpisodeNumberAscending) {
                Long seasonNumberLong = appDataFeed.getSeasonOrderedByEpisodeNumberAscendingNumber(seriesURL);
                episodesTitles = appDataFeed.getSortedEpisodeTitlesbyEpisodeNumberInAscendingOrder(seriesURL, seasonNumberLong);
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
    public void episodesByEpisodeNumberInAscendingOrderAndroidTest() {

        if (seriesHasSeasonOrderedByEpisodeNumberAscending) {
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            autoPlayChain.disableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);
            selectSeasonChain.navigateToSeason(seasonNumber);
            series.verifyEpisodesOrder(episodesTitles);
        } else {
            String message = "There's no series with episodes ordered by episode number and in ascending order on " +
                    TestRun.getAppName() + " " + TestRun.getLocale() + " so skipping test";
            Logger.logMessage(message);
            throw new EpisodeOrderException(message);
        }

    }

    @TestCaseId("10902")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS  }) //Need to work with feed for all sort type
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void episodesByEpisodeNumberInAscendingOrderiOSTest() {
    	
        if (seriesHasSeasonOrderedByEpisodeNumberAscending) {
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            autoPlayChain.disableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.scrollUpToSeriesTitle(seriesTitle);
            selectSeasonChain.navigateToSeason(seasonNumber);
            series.verifyEpisodesOrder(episodesTitles);
        } else {
            String message = "There's no series with episodes ordered by episode number and in ascending order on " +
                    TestRun.getAppName() + " " + TestRun.getLocale() + " so skipping test";
            Logger.logMessage(message);
            throw new EpisodeOrderException(message);
        }
    }
  }
