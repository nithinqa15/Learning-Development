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
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SettingsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class BackToHomeFromLiveTVTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    LoginChain loginChain;
    Home home;
    LiveTV liveTV;
    AppDataFeed appDataFeed;
    SettingsChain settingsChain;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    //Declare data
    String liveSeriesTitle;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public BackToHomeFromLiveTVTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        home = new Home();
        liveTV = new LiveTV();
        appDataFeed = new AppDataFeed();
        settingsChain = new SettingsChain();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String liveTVFeedURL = FeedFactory.getLiveTvScheduleFeedUrl();
        HashMap<String, String> liveTVDetails = appDataFeed.getCurrentLiveTVDetails(liveTVFeedURL);
        liveSeriesTitle = liveTVDetails.get("SeriesTitle");
    }

    @TestCaseId("11931")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.HOME_SCREEN, GroupProps.FULL })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void backToHomeFromLiveTVAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();
        settingsChain.toSettingsAndBack(); // Dirty hack to make Live TV label show up

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowSeriesTtl(liveSeriesTitle).waitForViewLoad().waitForVisible(); // this line takes longer than usual to execute
        liveTV.liveTVCloseBtn().waitForVisible().tap();
        home.liveTVBtn().waitForVisible();

    }

    @TestCaseId("11931")
    @Features(GroupProps.LIVE_TV)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void backToHomeFromLiveTViOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.loginIfNot();

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowSeriesTtl(liveSeriesTitle).waitForVisible();
        liveTV.liveTVCloseBtn().waitForVisible().tap();
        home.liveTVBtn().waitForVisible();
    }
}
