package com.viacom.test.vimn.uitests.tests.ads;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.AdsBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class LearnMoreTest extends AdsBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public LearnMoreTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
        if (seriesTitle == null) {
            adsSetupTest();
        }
    }

    @TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = {GroupProps.FULL, GroupProps.VIDEO_ADS})
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void learnMoreAndroidTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);
        ProxyUtils.disableTve();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        videoPlayer.playFromBeginningBtn().waitForNotPresent();
        videoPlayer.playerCtr().waitForViewLoad().waitForPresent().tapCenter();

        // Android Activity before clicking on learn more
        String activityBeforeClickingOnLearnMore = DriverManager.getAndroidDriver().currentActivity();
        Logger.logMessage("Activity before clicking on learn more button: " + activityBeforeClickingOnLearnMore);
        ads.learnMoreBtn().waitForViewLoad().waitForVisible().tap();

        // Android Activity after clicking on learn more
        String activityAfterClickingOnLearnMore = DriverManager.getAndroidDriver().currentActivity();
        Logger.logMessage("Activity after clicking on learn more button: " + activityAfterClickingOnLearnMore);
        Logger.logMessage("Did clicking on the learn more button open a new browser window with the ad's link? "
                + activityAfterClickingOnLearnMore != activityAfterClickingOnLearnMore);
        Assert.assertNotEquals(activityBeforeClickingOnLearnMore, activityAfterClickingOnLearnMore);
    }

    @TestCaseId("")
    @Features(GroupProps.VIDEO_ADS)
    @Test(groups = {GroupProps.BROKEN, GroupProps.VIDEO_ADS}) //revisit. Player control & TSLA rewrite issue
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP, 
				  ParamProps.BET_DOMESTIC_APP, ParamProps.CMT_APP, 
				  ParamProps.CC_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
				  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })

    public void LearnMoreiOSTest() {

        //Ensure Ads play
        ProxyUtils.rewriteTSLA(0);

        splashChain.splashAtRest();
        autoPlayChain.enableAutoPlay();

        home.flickRightToSeriesThumb(seriesTitle, numberOfSwipes);
        home.fullEpisodesBtn().waitForVisible().tap();
        home.fullEpisodesBtn().waitForPlayerLoad();
        series.scrollUpToSeriesTitle(seriesTitle);

        ads.learnMoreBtn().waitForVisible().tap();
    }
}