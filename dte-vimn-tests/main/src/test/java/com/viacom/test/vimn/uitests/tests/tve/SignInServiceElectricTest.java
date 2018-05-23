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

public class SignInServiceElectricTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInServiceElectricTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.SERVICEELECTRIC);
        password = ProviderManager.getProviderPassword(StaticProps.SERVICEELECTRIC);

    }

    @TestCaseId("35106")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInServiceElectricAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.serviceElectricProviderBtn().waitForVisible().tap();
        signIn.serviceElectricUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.serviceElectricPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.serviceElectricSignInBtn().waitForVisible().tap();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        signIn.waitForSigningInText();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    // Service Electric Is No Longer In MVPD Picker
    @TestCaseId("35106")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInServiceElectriciOSTest() {

        splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.episodeLockBtn(episodeTitle).waitForScrolledTo().tap();
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        selectProvider.serviceElectricProviderBtn().waitForVisible().tap();
        signIn.serviceElectricUsernameTxb().waitForVisible().tap().type(username);
        signIn.serviceElectricPasswordTxb().waitForVisible().tap().type(password);
        signIn.serviceElectricSignInBtn().waitForVisible().tap();
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForScrolledTo();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

}

