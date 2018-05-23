package com.viacom.test.vimn.uitests.tests.homescreen;

import java.util.HashMap;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TapBackgroundTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Home home;
	Series series;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    
    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TapBackgroundTest(String runParams) { super.setRunParams(runParams); }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	home = new Home();
    	series = new Series();
        appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String promoListFeedURL = FeedFactory.getPromoListFeedURL();
        seriesTitle = appDataFeed.getPropertyTitles(promoListFeedURL).get(0);
        HashMap<String, String> seriesDetails = appDataFeed.getSeriesDetails(promoListFeedURL, seriesTitle);
        episodeTitle = appDataFeed.getEpisodeTitles(FeedFactory.getSeriesFeedURL(seriesDetails.get("SeriesID"))).get(0);
        String featuredPropertyFeedURL = FeedFactory.getFeaturedPropertyFeedURL();
        numberOfSwipes = appDataFeed.getSeriesIds(featuredPropertyFeedURL).size();
    }
    
    //Deprecated since this scenario is covered in another test.
    @TestCaseId("11925")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.ANDROID })
    public void tapBackgroundAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        
        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.seriesTtl(seriesTitle).tapOffSetElement(0, -200);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.episodeTtl(episodeTitle).waitForScrolledTo();
        
    }

    //Deprecated since this scenario is covered in another test.
    @TestCaseId("11925")
    @Features(GroupProps.HOME_SCREEN)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.HOME_SCREEN })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void tapBackgroundiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.seriesTtl(seriesTitle).tapOffSetElement(0, -200);
    }
    
}
