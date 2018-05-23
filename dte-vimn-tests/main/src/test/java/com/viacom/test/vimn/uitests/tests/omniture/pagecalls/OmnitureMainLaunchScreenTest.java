package com.viacom.test.vimn.uitests.tests.omniture.pagecalls;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;
import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_HOME_MAIN_SCREEN;

import java.util.Map;

import com.viacom.test.vimn.common.filters.ShortformResult;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyFilter;
import com.viacom.test.vimn.uitests.actionchains.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmniturePageData;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.pageobjects.AllShows;
import com.viacom.test.vimn.uitests.pageobjects.Clips;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class OmnitureMainLaunchScreenTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Home home;
    Series series;
    Clips clips;
    VideoPlayer videoPlayer;
    AppDataFeed appDataFeed;
    AlertsChain alertschain;
    AllShows allShows;
    LoginChain loginChain;
    LiveTV liveTV;
    ChromecastChain chromecastChain;
    PropertyFilter propertyFilter;
    SettingsChain settingsChain;

    //Declare data
    String seriesTitle;
    String clipTitle;
    Integer numberOfSwipes;
    Map<String, String> expectedMap;
    Map<String, String> actualMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureMainLaunchScreenTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        home = new Home();
        series = new Series();
        videoPlayer = new VideoPlayer();
        appDataFeed = new AppDataFeed();
        allShows = new AllShows();
        alertschain = new AlertsChain();
        loginChain = new LoginChain();
        liveTV = new LiveTV();
        clips = new Clips();
        chromecastChain = new ChromecastChain();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);
        settingsChain = new SettingsChain();

        PropertyResult propertyResult = propertyFilter.withClips().propertyFilter().getFirstProperty();

        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwipes = propertyResult.getNumOfSwipes();

        ShortformResult clipResult = propertyResult.getShortformFilter().clipsFilter().getFirstClip();
        clipTitle = clipResult.getClipTitle();

    }

    @TestCaseId("35871")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void omnitureMainLaunchScreenAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap(seriesTitle, "n/a");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        actualMap.clear();

        ProxyUtils.clearNetworkLogs();
        settingsChain.toSettingsAndBack();
        expectedMap.replace("prevPageName", "MainScreen - P1");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        actualMap.clear();

        // Removed from execution due to https://jira.mtvi.com/browse/VAA-5419
        // TODO - GF 27/09/2017 - Turn on execution once https://jira.mtvi.com/browse/VAA-5419 is fixed
        /*ProxyUtils.clearNetworkLogs();
        allShows.enterAllShows();
        allShows.exitAllShows();
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        expectedMap.put(PREVPAGNAME, "All Show Grid");
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        ProxyUtils.clearNetworkLogs();
        home.enterClipsView(seriesTitle, numberOfSwipes);
        clips.clipTitle(clipTitle).waitForVisible().goBack();
        expectedMap.put(PREVPAGNAME, "MainScreen - P1");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);*/

    }

    @TestCaseId("35871")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.HOME_SCREEN, GroupProps.OMNITURE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void omnitureMainLaunchScreeniOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();

        expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap("no-franchise", "N/A");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        //back to Home from settings
        ProxyUtils.clearNetworkLogs();
        expectedMap.clear();
        actualMap.clear();
        settingsChain.toSettingsAndBack();

        expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap("no-franchise", "Settings");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        //back to Home from series details page (All shows page)
        ProxyUtils.clearNetworkLogs();
        expectedMap.clear();
        actualMap.clear();
        allShows.enterAllShows();
        allShows.allShowsBackBtn().waitForVisible().tap();

        expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap("no-franchise", "All Show Grid");
        actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

        //back to Home from clip reel page
        ProxyUtils.clearNetworkLogs();
        expectedMap.clear();
        actualMap.clear();
        if (clipTitle != null) {
            home.watchExtrasBtn().waitForVisible().tap();
            clips.clipPlayBtn(clipTitle).waitForViewLoad().pause(StaticProps.MEDIUM_PAUSE);
            clips.clipCloseBtn().waitForVisible().tap();

            expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap("no-franchise", "Show/"+ seriesTitle);
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
            Omniture.assertOmnitureValues(expectedMap, actualMap);

        } else {
            //Skip exception not used because test should be continue and finish until end
            String message = "Clips not available for featured series on " + TestRun.getAppName() + " " + TestRun.getLocale() +
                    " so skipping test";
            Logger.logMessage(message);
        }

        //back to Home from EPG page (Live TV)
        ProxyUtils.clearNetworkLogs();
        expectedMap.clear();
        actualMap.clear();
        if (!TestRun.isTVLandApp() && !TestRun.isBETINTLApp()) {
            loginChain.loginIfNot();
            home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
            liveTV.liveTVCloseBtn().waitForVisible().tap();

            expectedMap = OmniturePageData.buildMainLaunchScreenExpectedMap("no-franchise", "");
            actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_HOME_MAIN_SCREEN);
            Omniture.assertOmnitureValues(expectedMap, actualMap);
        }
    }
}
