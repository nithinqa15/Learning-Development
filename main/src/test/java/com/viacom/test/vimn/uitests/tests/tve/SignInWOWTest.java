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

public class SignInWOWTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInWOWTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.WOW);
        password = ProviderManager.getProviderPassword(StaticProps.WOW);

    }

    @TestCaseId("35115")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInWOWAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        selectProvider.wowProviderBtn().waitForVisible().tap();
        signIn.wowUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.wowPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.wowSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35115")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInWOWiOSTest() {

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
        } else if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.wowProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.wowProviderBtn().waitForVisible().tap();
        signIn.wowUsernameTxb().waitForVisible().tap().type(username);
        keyboard.goToPasswordField();
        signIn.wowPasswordTxb().waitForVisible().tap().type(password);
        signIn.wowSignInBtn().waitForVisible().tap();

        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

}

