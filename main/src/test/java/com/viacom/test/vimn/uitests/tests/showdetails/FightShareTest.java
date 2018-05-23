package com.viacom.test.vimn.uitests.tests.showdetails;

import com.viacom.test.vimn.common.filters.HomePropertyFilter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.filters.PropertyResult;
import com.viacom.test.vimn.common.filters.PropertyResults;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.NoEventContentException;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
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
import com.viacom.test.vimn.uitests.support.feeds.FeedFactory;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static com.viacom.test.vimn.common.filters.Pagination.NON_PAGINATED;

public class FightShareTest extends BaseTest {

    SplashChain splashChain;
    AlertsChain alertsChain;
    AutoPlayChain autoPlayChain;
    ChromecastChain chromecastChain;
    Navigate navigate;
    Home home;
    Series fight;
    Share share;
    VideoPlayer videoPlayer;
    HomePropertyFilter propertyFilter;

    //Declare data
    String firstFightTitle;
    Integer numberOfSwips;
    Boolean hasPlaylist;
    Integer numberOfSwipes;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public FightShareTest(String runParams) { super.setRunParams(runParams); }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        splashChain = new SplashChain();
        autoPlayChain = new AutoPlayChain();
        alertsChain = new AlertsChain();
        chromecastChain = new ChromecastChain();
        navigate = new Navigate();
        home = new Home();
        fight = new Series();
        share = new Share();
        videoPlayer = new VideoPlayer();
        propertyFilter = new HomePropertyFilter(NON_PAGINATED);

        PropertyResults fightResults = propertyFilter.withFight().propertyFilter();
        PropertyResult firstFight = fightResults.getFirstProperty();
        firstFightTitle = firstFight.getPropertyTitle();
        Logger.logConsoleMessage("First Fight title on homescreen : " +  firstFightTitle);
        hasPlaylist = firstFight.hasPlaylist();
        Logger.logConsoleMessage("First Fight title has event : " + hasPlaylist);
        numberOfSwips = firstFight.getNumOfSwipes();
    }

    @TestCaseId("48083")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = {Config.GroupProps.BROKEN, Config.GroupProps.FIGHTS_CONTENT, Config.GroupProps.SHARE})
    @Parameters({Config.ParamProps.ANDROID, ParamProps.PARAMOUNT_APP})
    public void fightShareAndroidTest() {
        //TO-DO
    }

    @TestCaseId("48083")
    @Features(Config.GroupProps.SHOW_DETAILS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.FIGHTS_CONTENT, Config.GroupProps.SHARE})
    @Parameters({Config.ParamProps.IOS, ParamProps.PARAMOUNT_APP})
    public void fightShareiOSTest() {
    	
      	if (!hasPlaylist) {
    		String message = "There is a no Playlist present or only extras available for fight content on:" + TestRun.getAppName() + " " + TestRun.getLocale()
				+ " in promolistfeed url : " + FeedFactory.getPromoListFeedURL() + " so test failed";
    			Logger.logMessage(message);
    			throw new NoEventContentException(message);
    	} else {
            ProxyUtils.disableAds();

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            alertsChain.dismissAlerts();
            autoPlayChain.enableAutoPlay();

            home.flickRightToSeriesThumb(firstFightTitle, numberOfSwips);
            fight.specialBtn().waitForVisible().tap();
            videoPlayer.playFromBeginningBtn().waitForNotPresent();
            
            share.shareBtn().waitForVisible().tap();
            share.ShareActivityListScreen().waitForVisible();
            share.AddtoNotesBtn().waitForVisible().tap();
            
            if (share.upgradeNotesAlert().isVisible(10)) { // Only exception case
            	//share.alertCancelBtn().waitForVisible().tap(); 
            	share.goToNotesBtn().waitForVisible().tap();
            	share.returnToParamountBtn().waitForVisible().tap(); //we can add more specific once we do for other brand
            }
            fight.seriesTtl(firstFightTitle).waitForPresent();
    	}
    }
}

