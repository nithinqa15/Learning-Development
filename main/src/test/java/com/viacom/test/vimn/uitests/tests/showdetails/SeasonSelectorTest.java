package com.viacom.test.vimn.uitests.tests.showdetails;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Ads;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SeasonSelectorTest extends BaseTest {

    //DECLARE page objects/actions
    SplashChain splashChain;
    AutoPlayChain autoPlayChain;
    SelectSeasonChain selectSeasonChain;
    AppDataFeed appDataFeed;
    Home home;
    VideoPlayer videoPlayer;
    Series series;
    Ads ads;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;


    //Declare data
    String seriesTitle;
    List<Long> seasonNumbers;
    Map<Long, ArrayList<String>> episodeTitlesBySeasonMap;
    ArrayList<String> episodeTitlesList;
    Boolean multipleSeasons;
    String episodeTitle;
    String episodeDescription;
    String seriesURL;
    Integer numberOfSwipes;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SeasonSelectorTest(String runParams) {
        super.setRunParams(runParams);
    }

    @DataProvider()
    static public Object[][] dataProvider() {
        return new Object[][] {
                new Object[] { Config.ParamProps.PHONE },
                new Object[] { Config.ParamProps.TABLET }
        };
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        selectSeasonChain = new SelectSeasonChain();
        appDataFeed = new AppDataFeed();
        home = new Home();
        videoPlayer = new VideoPlayer();
        series = new Series();
        ads = new Ads();
        seasonNumbers = new ArrayList<>();
        episodeTitlesBySeasonMap = new LinkedHashMap<>();
        episodeTitlesList = new ArrayList<>();
        multipleSeasons = false;
        episodeTitle = null;
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
        Integer numberOfSeriesInFeaturedFeed = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
        String promoListFeedURL = FeedFactory.getPromoListFeedURL();
        List<String> seriesIDs = appDataFeed.getSeriesIds(promoListFeedURL);
        for (String seriesID : seriesIDs) {
            seriesURL = FeedFactory.getSeriesFeedURL(seriesID);
            numberOfSwipes = seriesIDs.indexOf(seriesID) + numberOfSeriesInFeaturedFeed;
            seriesTitle = appDataFeed.getEpisodesParentSeriesTitle(seriesURL);
            seasonNumbers = new ArrayList<>();
            seasonNumbers.addAll(appDataFeed.getAllEpisodesSeasonNumbers(seriesURL));
            if (seasonNumbers.size() > 1) {
                multipleSeasons = true;
                for (int i = 0; i < seasonNumbers.size() - 1; i++) {
                	episodeTitlesList = new ArrayList<>();
                    episodeTitlesList.addAll(appDataFeed.getEpisodeTitlesBySeason(seriesURL, seasonNumbers.get(i).intValue()));
                    episodeTitlesBySeasonMap.put(seasonNumbers.get(i), episodeTitlesList);
                    episodeTitlesList = null;
                }
                break;
            } else {
                multipleSeasons = false;
                episodeTitlesList = new ArrayList<>();
                episodeTitlesList.addAll(appDataFeed.getEpisodeTitlesBySeason(seriesURL, seasonNumbers.get(0).intValue()));
                break;
            }
        }
    }
    // Test broken at the setup
    @TestCaseId("34813")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.SHOW_DETAILS })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void SeasonSelectorAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        if (multipleSeasons) {
            for (Long key : episodeTitlesBySeasonMap.keySet()) {
                selectSeasonChain.navigateToSeason(key.intValue());
                series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
                for (ArrayList<String> episodes : episodeTitlesBySeasonMap.values()) {
                    for (String episodeTitle : episodes) {
                        episodeDescription = appDataFeed.getEpisodeDescription(seriesURL, episodeTitle);
                        if (series.episodeTtl(episodeTitle).isVisible() || series.episodeDescriptionTxt(episodeDescription).isVisible()) {
                            // do nothing
                        } else {
                            Assert.fail();
                        }
                    }
                }
            }
        } else {
            for (String episodeTitle : episodeTitlesList) {
                episodeDescription = appDataFeed.getEpisodeDescription(seriesURL, episodeTitle);
                if (series.episodeTtl(episodeTitle).isVisible() || series.episodeDescriptionTxt(episodeDescription).isVisible()) {
                    // do nothing
                } else {
                    Assert.fail();
                }
            }
        }
    }
    
    //This TC only failed if Feed not maintain Ascending order for Episodes
    @TestCaseId("34813")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.SHOW_DETAILS  }) //need to check feed for sort type
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void SeasonSelectoriOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        if (multipleSeasons) {
            for (Long key : episodeTitlesBySeasonMap.keySet()) {
                selectSeasonChain.navigateToSeason(key.intValue());
                series.seriesTtl(seriesTitle).waitForViewLoad().waitForScrolledTo();
                for (ArrayList<String> episodes : episodeTitlesBySeasonMap.values()) {
                    for (String episodeTitle : episodes) {
                        episodeDescription = appDataFeed.getEpisodeDescription(seriesURL, episodeTitle);
                        if (series.episodeTtl(episodeTitle).isVisible() || series.episodeDescriptionTxt(episodeDescription).isVisible()) {
                            // do nothing
                        } else {
                        	series.scrollDownToEpisodeTtl(episodeTitle);
                        }
                    }
                }
            }
        } else {
            for (String episodeTitle : episodeTitlesList) {
                episodeDescription = appDataFeed.getEpisodeDescription(seriesURL, episodeTitle);
                if (series.episodeTtl(episodeTitle).isVisible() || series.episodeDescriptionTxt(episodeDescription).isVisible()) {
                    // do nothing
                } else {
                    series.scrollDownToEpisodeTtl(episodeTitle);
                }
            }
        }
    }

}