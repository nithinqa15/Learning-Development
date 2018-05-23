package com.viacom.test.vimn.uitests.tests.omniture.playercalls;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.omniture.Omniture;
import com.viacom.test.vimn.common.omniture.OmnitureLogUtils;
import com.viacom.test.vimn.common.omniture.OmniturePlayerData;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.MrssDataFeed;
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.util.HashMap;
import java.util.Map;

import static com.viacom.test.vimn.common.omniture.OmnitureConstants.ExpectedParams.EXPECTED_PARAM_LIVE_STREAM_START;

public class OmnitureLiveStreamStartPlayerTest extends BaseTest {

    AppDataFeed appDataFeed;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    Home home;
    AutoPlayChain autoPlayChain;
    LoginChain loginChain;
    Settings settings;
    SettingsMenu settingsMenu;
    MrssDataFeed mrssDataFeed;
    SelectProvider selectProvider;

    String id;
    Map<String, String> expectedMap;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public OmnitureLiveStreamStartPlayerTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        appDataFeed = new AppDataFeed();
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        home = new Home();
        autoPlayChain = new AutoPlayChain();
        selectProvider = new SelectProvider();
        loginChain = new LoginChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();

        expectedMap = new HashMap<>();

        String liveTVFeedURL = FeedFactory.getLiveTvSeriesFeedUrl();

        id = appDataFeed.getLiveTvId(liveTVFeedURL);

    }

    @TestCaseId("35933")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.FULL, GroupProps.LIVE_TV, GroupProps.OMNITURE, Config.GroupProps.BENTO_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP,
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
			  	  ParamProps.PARAMOUNT_APP })
    public void omnitureLiveStreamStartPlayerAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        loginChain.defaultLogin();

        home.liveTVBtn().waitForVisible().tap().waitForNotPresentOrVisible();

        mrssDataFeed = new MrssDataFeed(id);
        String seriesTitle = mrssDataFeed.getTitle();
        String segmentMgid = mrssDataFeed.getFirstSegmentMgid();
        String numberOfSegments = String.valueOf(mrssDataFeed.getNumberOfSegments());

        expectedMap = OmniturePlayerData.buildLiveStreamStartPlayerExpectedMap(seriesTitle, segmentMgid, numberOfSegments);
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_LIVE_STREAM_START);
        Omniture.assertOmnitureValues(expectedMap, actualMap);

    }

    @TestCaseId("35933")
    @Features(GroupProps.OMNITURE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.LIVE_TV, GroupProps.OMNITURE, GroupProps.BENTO_SMOKE }) // Broken until fix default login
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP,
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP,
			  	  ParamProps.PARAMOUNT_APP })
    public void omnitureLiveStreamStartPlayeriOSTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        loginChain.loginIfNot();
        home.liveTVBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);

        mrssDataFeed = new MrssDataFeed(id);
        String seriesTitle = mrssDataFeed.getTitle();
        String segmentMgid = mrssDataFeed.getFirstSegmentMgid();
        String numberOfSegments = String.valueOf(mrssDataFeed.getNumberOfSegments());

        expectedMap = OmniturePlayerData.buildLiveStreamStartPlayerExpectedMap(seriesTitle, segmentMgid, numberOfSegments);
        Map<String, String> actualMap = OmnitureLogUtils.getActualMap(EXPECTED_PARAM_LIVE_STREAM_START);
        Omniture.assertOmnitureValues(expectedMap, actualMap);
    }
}
