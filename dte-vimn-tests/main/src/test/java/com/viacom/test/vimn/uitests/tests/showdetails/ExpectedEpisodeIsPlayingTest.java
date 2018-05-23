package com.viacom.test.vimn.uitests.tests.showdetails;

import java.util.HashMap;
import java.util.List;

import com.viacom.test.vimn.common.filters.LongformResult;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.MediagenLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SelectSeasonChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ExpectedEpisodeIsPlayingTest extends BaseTest {

    SplashChain splashChain;
    LoginChain loginChain;
    Home home;
    Series series;
    AppDataFeed appDataFeed;
    SelectSeasonChain selectSeasonChain;
    AutoPlayChain autoPlayChain;
    VideoPlayer videoPlayer;
    MediagenLogUtils mediagenLogUtils;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    String seriesTitle;
    Integer numberOfSwipes;
    HashMap<String, String> episodes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ExpectedEpisodeIsPlayingTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        home = new Home();
        series = new Series();
        appDataFeed = new AppDataFeed();
        selectSeasonChain = new SelectSeasonChain();
        autoPlayChain = new AutoPlayChain();
        videoPlayer = new VideoPlayer();
        mediagenLogUtils = new MediagenLogUtils();
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
            episodes = appDataFeed.getMgidEpisodesTtlMap(seriesURL);
            if (episodes != null) {
                break;
            }
        }
    }

    @TestCaseId("")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void expectedEpisodeIsPlayingAndroidTest() {

        ProxyUtils.disableAds();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);

        series.scrollUpToSeriesTitle(seriesTitle);
        episodes.forEach((seriesTtl,mgid) -> {
            ProxyUtils.clearNetworkLogs();
            series.tapEpisodePlayBtn(seriesTtl);
            mediagenLogUtils.hasMediagenCall(mgid);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.tapEpisodePauseBtn(seriesTtl);
        });

    }

    @TestCaseId("")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHOW_DETAILS  }) //Need to check feed 
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void expectedEpisodeIsPlayingiOSTest() {

        ProxyUtils.disableAds();

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.loginIfNot();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        episodes.forEach((seriesTtl,mgid) -> {
            ProxyUtils.clearNetworkLogs();
            series.tapEpisodePlayBtn(seriesTtl);
            mediagenLogUtils.hasMediagenCall(mgid);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            series.tapEpisodePauseBtn(seriesTtl);
        });
     }
  }
