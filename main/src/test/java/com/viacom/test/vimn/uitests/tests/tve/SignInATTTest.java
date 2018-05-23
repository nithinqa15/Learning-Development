package com.viacom.test.vimn.uitests.tests.tve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInATTTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInATTTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.ATT);
        password = ProviderManager.getProviderPassword(StaticProps.ATT);

    }

    @TestCaseId("35107")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInATTAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.attProviderBtn().waitForVisible().tap();
        signIn.attUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.attPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.attSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35107")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE  }) //Sign in webview complication
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInATTiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollDownToEpisodeTtl(episodeTitle);
        series.episodeLockBtn(episodeTitle).waitForVisible().tap();
       
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        } else if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.attProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.attProviderBtn().waitForVisible().tap();
        signIn.attUsernameTxb().waitForVisible().tap().type("Modelshop425@att.net");
        keyboard.goToPasswordField();
        signIn.attPasswordTxb().waitForVisible().tap().type("ShopID425");
        if (keyboard.GoBtn().isVisible()) {
            keyboard.GoBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        } else if (keyboard.doneBtn().isVisible()) {
            keyboard.doneBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        }
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
     }
  }

