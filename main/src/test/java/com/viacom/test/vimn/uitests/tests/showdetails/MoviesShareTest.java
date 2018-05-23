package com.viacom.test.vimn.uitests.tests.showdetails;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Share;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class MoviesShareTest extends BaseTest {

    SplashChain splashChain;
    AlertsChain alertsChain;
    AutoPlayChain autoPlayChain;
    ChromecastChain chromecastChain;
    Navigate navigate;
    Home home;
    Series movie;
    Share share;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;

    String movieTitle;
    Integer numberOfSwipes;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MoviesShareTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        chromecastChain = new ChromecastChain();
        navigate = new Navigate();
        home = new Home();
        movie = new Series();
        share = new Share();
        videoPlayer = new VideoPlayer();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult movieResult = propertyFilter.withMovie().propertyFilter().getFirstProperty();
        movieTitle = movieResult.getPropertyTitle();
        numberOfSwipes = movieResult.getNumOfSwipes();
    }

    @TestCaseId("48071")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = {Config.GroupProps.BROKEN, Config.GroupProps.MOVIES_CONTENT, Config.GroupProps.SHARE})
    @Parameters({Config.ParamProps.ANDROID, ParamProps.PARAMOUNT_APP})
    public void moviesShareAndroidTest() {
        //TO-DO
    }

    @TestCaseId("48071")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.MOVIES_CONTENT, Config.GroupProps.SHARE})
    @Parameters({Config.ParamProps.IOS, ParamProps.PARAMOUNT_APP})
    public void moviesShareiOSTest() {

        ProxyUtils.disableAds();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        alertsChain.dismissAlerts();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(movieTitle, numberOfSwipes);
        movie.movieBtn().waitForVisible().tap();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        
        share.shareBtn().waitForVisible().tap();
        share.ShareActivityListScreen().waitForVisible();
        share.AddtoNotesBtn().waitForVisible().tap();
        
        if (share.upgradeNotesAlert().isVisible(10)) { // Only exception case
        	//share.alertCancelBtn().waitForVisible().tap(); 
        	share.goToNotesBtn().waitForVisible().tap();
        	share.returnToParamountBtn().waitForVisible().tap(); //we can add more specific once we do for other brand
        }
        movie.seriesTtl(movieTitle).waitForPresent();
    }
}

