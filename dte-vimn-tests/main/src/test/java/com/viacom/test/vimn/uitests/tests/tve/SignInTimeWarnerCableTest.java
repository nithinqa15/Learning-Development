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
@Deprecated
public class SignInTimeWarnerCableTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInTimeWarnerCableTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.TIMEWARNERCABLE);
        password = ProviderManager.getProviderPassword(StaticProps.TIMEWARNERCABLE);

    }

    @TestCaseId("35112")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInTimeWarnerCableAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.timeWarnerCableProviderBtn().waitForVisible().tap();
        signIn.timeWarnerCableUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.timeWarnerCablePasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.timeWarnerCableSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

    @TestCaseId("35112")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInTimeWarnerCableiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollDownToEpisodeTtl(episodeTitle);
        series.episodeLockBtn(episodeTitle).waitForVisible().tap();
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.timeWarnerCableProviderBtn().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        selectProvider.timeWarnerCableProviderBtn().waitForVisible().tap();
        if (com.viacom.test.core.util.TestRun.isXCUITest()) {
            signIn.timeWarnerCableUsernameTxb().waitForVisible().tap().type("tve093");
            keyboard.goToPasswordField();
            signIn.timeWarnerCablePasswordTxb().waitForVisible().tap().type("V7MsZ9sh");
            signIn.timeWarnerCableSignInBtn().waitForVisible().tap();
        } else {
            signIn.timeWarnerCableUsernameTxb().waitForVisible().tap().type("tve093");
            signIn.timeWarnerCablePasswordTxb().hideKeyboard().waitForVisible().tap().type("V7MsZ9sh");
            signIn.timeWarnerCableSignInBtn().hideKeyboard().waitForVisible().tap();
        }
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

}

