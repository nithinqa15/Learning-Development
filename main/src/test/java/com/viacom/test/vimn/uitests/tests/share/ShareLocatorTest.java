package com.viacom.test.vimn.uitests.tests.share;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.ProgressBar;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.Share;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ShareLocatorTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    LoginChain loginChain;
    AlertsChain alertschain;
    Series series;
    Home home;
    Share share;
    VideoPlayer videoPlayer;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    ProgressBar progressBar;
    AutoPlayChain autoPlayChain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String seriesTitle;
    String[] seriesTitleNew;
    String episodeTitle;
    Integer numberOfSwips;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ShareLocatorTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        alertschain = new AlertsChain();
        series = new Series();
        home = new Home();
        share = new Share();
        videoPlayer = new VideoPlayer();
        appDataFeed = new AppDataFeed();
        chromecastChain  = new ChromecastChain();
        progressBar = new ProgressBar();
        autoPlayChain = new AutoPlayChain();

        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwips = propertyResult.getNumOfSwipes();
    }

    @TestCaseId("")
    @Features(GroupProps.SHARE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHARE })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, 
		    	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
		    	  ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
			      ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP })
    public void shareLocatorAndroidTest() {

        // TODO - implement shareLocatorAndroidTest

    }

    @TestCaseId("")
    @Features(GroupProps.SHARE)
    @Test(groups = { GroupProps.FULL, GroupProps.SHARE })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP,
		  	      ParamProps.MTV_DOMESTIC_APP, ParamProps.CMT_APP,
			      ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP })
    public void shareLocatoriOSTest() {
    	
    	splashChain.splashAtRest();
    	chromecastChain.dismissChromecast();
    	alertschain.dismissAlerts();
        autoPlayChain.enableAutoPlay();
        ProxyUtils.disableAds();
        
        home.enterSeriesView(seriesTitle, numberOfSwips);
        series.fullEpisodesBtn().waitForVisible().tap();
        videoPlayer.playFromBeginningBtn().waitForNotPresent();

        //Validate Share activity list screen
        share.shareBtn().waitForVisible().tap();
        share.ShareActivityListScreen().waitForVisible();
        share.copyBtn().waitForVisible();
        share.AddtoNotesBtn().waitForVisible();
        share.moreBtn().waitForVisible();
        //share.iCloudBtn().waitForVisible(); // Button name diff in iOS 10 & 11. revisit after #665 PR merged.
        
        //Navigate share screen to player view & validate video playing
        if (TestRun.isPhone()) {
        	share.MessageBtn().waitForVisible();
        	//share.cancelBtn().waitForVisible().tap(); // Failed to found locator with same type, 
        	share.goToNotesBtn().waitForVisible().tap();
        } else {
        	 share.shareBtn().waitForPresent().tapCenter();
        }
    }
}
