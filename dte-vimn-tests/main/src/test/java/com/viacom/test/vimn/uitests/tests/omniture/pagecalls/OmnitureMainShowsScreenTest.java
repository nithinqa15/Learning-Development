package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.*;

import java.util.Map;

import com.viacom.test.vimn.common.omniture.OmniturePageData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureMainShowsScreenTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Home home;
    Series series;
    VideoPlayer videoPlayer;
    Navigate navigate;
    AppDataFeed appDataFeed;
    AlertsChain alertschain;
    AllShows allshows;

    //Declare data
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureMainShowsScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        navigate = new Navigate();
        allshows = new AllShows();
        appDataFeed = new AppDataFeed();
        alertschain = new AlertsChain();
    }

    @TestCaseId("35887")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureMainShowsScreenAndroidTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        allshows.enterAllShows();

        expectedMap = OmniturePageData.buildMainShowsScreenExpectedMap();
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_ALL_SHOW_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }

    @TestCaseId("35887")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.ALL_SHOWS, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureMainShowsScreeniOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        allshows.enterAllShows();

        expectedMap = OmniturePageData.buildMainShowsScreenExpectedMap();
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_ALL_SHOW_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
