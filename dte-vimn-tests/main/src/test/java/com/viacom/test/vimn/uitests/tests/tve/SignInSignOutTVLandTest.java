package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.ProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class SignInSignOutTVLandTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInSignOutTVLandTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.XFINITY);
        password = ProviderManager.getProviderPassword(StaticProps.XFINITY);

    }

    @TestCaseId("")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP })
    public void signInSignOutTVLandAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible().tap();
        xFinity.emailTxb().waitForVisible().tap().type(username).closeKeyboard();
        xFinity.passwordTxb().waitForVisible().tap().type(password).closeKeyboard();
        xFinity.signInBtn().waitForVisible().tap();
        xFinity.xFinitySignInSuccessTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signOutBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible();
    }

    @TestCaseId("")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP })
    public void signInSignOutTVLandiOSTest() {

        splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();

        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        series.episodeTtl(episodeTitle).waitForScrolledTo();
        series.episodeLockBtn(episodeTitle).waitForScrolledTo().tap();
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.xfinityProviderBtn().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible().tap();
        xFinity.emailTxb().waitForVisible().tap().type(username);
        xFinity.passwordTxb().waitForVisible().tap().type(password);
        xFinity.signInBtn().waitForVisible().tap();
        //signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }

}
