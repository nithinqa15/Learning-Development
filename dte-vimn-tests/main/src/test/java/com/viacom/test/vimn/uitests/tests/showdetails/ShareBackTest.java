package com.viacom.test.vimn.uitests.tests.showdetails;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
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
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.Share;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ShareBackTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
    AutoPlayChain autoPlayChain;
	LoginChain loginChain;
	Home home;
	Series series;
	Settings settings;
	Share share;
    VideoPlayer videoPlayer;
	AppDataFeed appDataFeed;
    SelectSeasonChain selectSeasonChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    HomePropertyFilter propertyFilter;
    
    //Declare data
    String seriesTitle;
    String episodeTitle;
    Integer episodeSeasonNumber;
    Integer numberOfSwipes;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ShareBackTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
    	loginChain = new LoginChain();
    	home = new Home();
    	series = new Series();
    	settings = new Settings();
    	share = new Share();
        videoPlayer = new VideoPlayer();
    	appDataFeed = new AppDataFeed();
        selectSeasonChain = new SelectSeasonChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

    }

    @TestCaseId("10909")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void shareBackAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);

        series.shareBtn().waitForVisible().tap();
        share.verifyCanShareViaEmail();
        series.seriesTtl(seriesTitle).waitForNotPresent();
        
    }

    @TestCaseId("10909")
    @Features(GroupProps.SHOW_DETAILS)
    @Test(groups = { GroupProps.FULL, GroupProps.SHOW_DETAILS })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void shareBackiOSTest() {

    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        autoPlayChain.disableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        share.shareBtn().waitForVisible().tap();
        share.copyBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForPresent();

    }
    
}
