package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInFromAllProviderListTest extends TveBaseTest {

    //Declare data
    String minorProviderName;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInFromAllProviderListTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.OPTIMUM);
        password = ProviderManager.getProviderPassword(StaticProps.OPTIMUM);
        minorProviderName = ProviderManager.getDefaultProvider();

    }

    @TestCaseId("35076")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void SignInFromAllProvidersListAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(minorProviderName).closeKeyboard();
        selectProvider.minorProviderBtn(minorProviderName).waitForVisible().tap();
        signIn.optimumUsernameTxb().waitForVisible().tap().type(username).closeKeyboard();
        signIn.optimumPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.optimumSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

    @TestCaseId("35076")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void SignInFromAllProvidersListTestiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        if (!series.episodeLockBtn(episodeTitle).isVisible()) {
        	series.scrollDownToEpisodeTtl(episodeTitle);
        }
        series.episodeLockBtn(episodeTitle).waitForVisible().tap();
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        }
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.viewAllProvidersBtn().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
 
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type("Optimum");
        if (keyboard.returnBtn().isVisible()) {
        	keyboard.returnBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);
        } 
        selectProvider.minorProviderBtn("Optimum").waitForVisible().tap();
        signIn.optimumUsernameTxb().waitForVisible().tap().type(username);
        signIn.optimumPasswordTxb().waitForVisible().tap().type(password);
        signIn.optimumForgotIdLink().waitForVisible().tapOffSetElement(0, -30);
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();

        series.backBtn().waitForVisible().tap();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

}
