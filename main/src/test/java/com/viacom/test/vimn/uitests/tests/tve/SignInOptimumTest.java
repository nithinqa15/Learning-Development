package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInOptimumTest extends TveBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInOptimumTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        
        username = ProviderManager.getProviderUsername(StaticProps.OPTIMUM);
        password = ProviderManager.getProviderPassword(StaticProps.OPTIMUM);
        
    }

    @TestCaseId("35114")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })

    public void signInOptimumAndroidTest() { 

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.optimumProviderBtn().waitForVisible().tap();
        signIn.optimumUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.optimumPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.optimumSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35114")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })

    public void signInOptimumiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();
        
        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
        series.tapEpisodeLockBtn(episodeTitle);
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        } else if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        if(!TestRun.isiOS10()) {
        	selectProvider.optimumProviderBtn().waitForPresent().tap();
        } else {
        	selectProvider.optimumProviderBtn().waitForVisible().tap();
        }
        signIn.optimumUsernameTxb().waitForVisible().tap().type(username);
        keyboard.goToPasswordField();
        signIn.optimumPasswordTxb().waitForVisible().tap().type(password);
        //Hacky method to tap onto the sign button
        if (keyboard.GoBtn().isVisible()) {
            keyboard.GoBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        } else if (keyboard.doneBtn().isVisible()) {
            keyboard.doneBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        }
        signIn.successTxt().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
}