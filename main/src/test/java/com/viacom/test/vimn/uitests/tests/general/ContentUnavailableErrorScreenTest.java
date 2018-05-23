package com.viacom.test.vimn.uitests.tests.general;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ContentUnavailableErrorScreenTest extends BaseTest {

    // Declare page objects/actions
    SplashChain splashChain;
    Home home;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    VideoPlayer videoPlayer;

    // Declare data
	PropertyResult seriesWithPublicEpisode;
	String seriesTitle;
	Integer numberOfSwipes;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ContentUnavailableErrorScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
        splashChain = new SplashChain();
        home = new Home();
        appDataFeed = new AppDataFeed();
        chromecastChain = new ChromecastChain();
        videoPlayer = new VideoPlayer();
    
	    HomePropertyFilter propertyFilter = new HomePropertyFilter(NON_PAGINATED);
	    PropertyResults allSeriesWithPublicEpisodes = propertyFilter.withPublicEpisodes().propertyFilter();
	    seriesWithPublicEpisode = allSeriesWithPublicEpisodes.get(0);
	    seriesTitle = seriesWithPublicEpisode.getPropertyTitle();
	    numberOfSwipes = seriesWithPublicEpisode.getNumOfSwipes();
    } 

    @TestCaseId("34849")
    @Features(GroupProps.GENERAL)
    @Test(groups = { GroupProps.FULL, GroupProps.GENERAL })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void contentUnavailableErrorScreenAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
		home.enterSeriesView(seriesTitle,numberOfSwipes);
        ProxyUtils.rewriteContentUnavailableErrorScreen();
        videoPlayer.contentNotAvailableTxt().waitForVisible();
    }
}
