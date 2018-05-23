package com.viacom.test.vimn.uitests.tests.share;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.filters.HomePropertyFilter;
import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.*;
import com.viacom.test.vimn.uitests.pageobjects.*;
import com.viacom.test.vimn.uitests.support.AppDataFeed;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class ShareButtonHiddenKidsComplianceTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    AlertsChain alertschain;
    Home home;
    Share share;
    VideoPlayer videoPlayer;
    ChromecastChain chromecastChain;
    AutoPlayChain autoPlayChain;
    HomePropertyFilter propertyFilter;

    //Declare data
    String seriesTitle;
    Integer numberOfSwips;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ShareButtonHiddenKidsComplianceTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        chromecastChain  = new ChromecastChain();
        alertschain = new AlertsChain();
        autoPlayChain = new AutoPlayChain();
        home = new Home();
        videoPlayer = new VideoPlayer();
        share = new Share();

        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResult propertyResult = propertyFilter.withEpisodes().withPublicEpisodes().propertyFilter().getFirstProperty();
        seriesTitle = propertyResult.getPropertyTitle();
        numberOfSwips = propertyResult.getNumOfSwipes();
    }

    // Kevin Dannenberg: Marking tests as broken until we have Nick and Nick Jr builds

    @TestCaseId("C74062")
    @Features(GroupProps.SHARE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHARE })
    @Parameters({ ParamProps.ANDROID, ParamProps.NICK_APP, ParamProps.NICK_JR_APP })
    public void shareButtonHiddenKidsComplianceAndroidTest() {

        // TODO - Implement shareButtonHiddenKidsComplianceAndroidTest

    }

    @TestCaseId("C74062")
    @Features(GroupProps.SHARE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SHARE })
    @Parameters({ ParamProps.IOS, ParamProps.NICK_APP, ParamProps.NICK_JR_APP })
    public void shareButtonHiddenKidsComplianceiOSTest() {
    	
    	splashChain.splashAtRest();
    	chromecastChain.dismissChromecast();
    	alertschain.dismissAlerts();
        autoPlayChain.disableAutoPlay();
        ProxyUtils.disableAds();

        // Validate share button is not visible before video playback
        home.enterSeriesView(seriesTitle, numberOfSwips);
        videoPlayer.verifyVideoIsNotPlayingByScreenshots(5);
        share.shareBtn().waitForNotPresent();
        videoPlayer.largePlayBtn().waitForVisible().tap();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        // Validate share button is not visible during video playback
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        share.shareBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.maximizeBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        share.shareBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        videoPlayer.minimizeBtn().waitForVisible().tap();
        videoPlayer.playerCtr().waitForPresent().tapCenter();
        share.shareBtn().waitForNotPresent();

    }
}
