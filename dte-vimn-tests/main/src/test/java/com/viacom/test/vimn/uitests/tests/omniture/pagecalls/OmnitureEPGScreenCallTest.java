package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_EPG_SCREEN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureEPGScreenCallTest extends BaseTest {

    SplashChain splashChain;
    LoginChain loginChain;
    Home home;
    LiveTV liveTV;
    AppDataFeed appDataFeed;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    String seriesTtl;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureEPGScreenCallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        home = new Home();
        liveTV = new LiveTV();
        appDataFeed = new AppDataFeed();
        expectedMap = new HashMap<>();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();

        String liveTvFeed = FeedFactory.getLiveTvScheduleFeedUrl();
        String featuredFeedUrl = FeedFactory.getFeaturedPropertyFeedURL();
        String promoFeedUrl = FeedFactory.getPromoListFeedURL();
        List<String> seriesIDs = new ArrayList<>();
        seriesIDs.addAll(appDataFeed.getSeriesIds(featuredFeedUrl));
        seriesIDs.addAll(appDataFeed.getSeriesIds(promoFeedUrl));

        HashMap<String, String> liveTVDetails = appDataFeed.getCurrentLiveTVDetails(liveTvFeed);
        seriesTtl = liveTVDetails.get("SeriesTitle");

    }

    // GF - 26/09/2017 - Can't find call on HarLog even though I can find it when testing manually
    @TestCaseId("35881")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void omnitureEPGScreenCallAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowSeriesTtl(seriesTtl).waitForVisible();

        expectedMap = OmniturePageData.buildEPGScreenCallExpectedMap(seriesTtl);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPG_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }

    @TestCaseId("35881")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV, GroupProps.OMNITURE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void omnitureEPGScreenCalliOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.loginIfNot();

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowSeriesTtl(seriesTtl).waitForVisible();

        expectedMap = OmniturePageData.buildEPGScreenCallExpectedMap(seriesTtl);
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_EPG_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
