package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class SignInBrighthouseTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInBrighthouseTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(Config.StaticProps.BRIGHTHOUSE);
        password = ProviderManager.getProviderPassword(Config.StaticProps.BRIGHTHOUSE);

    }

    @TestCaseId("35108")
    @Features(Config.GroupProps.TVE)
    @Test(groups = {Config.GroupProps.BROKEN, Config.GroupProps.TVE})
    @Parameters({Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void signInBrighthouseAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.brighthouseProviderBtn().waitForVisible().tap();
        signIn.brighthouseUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.brighthousePasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.brighthouseSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.playCtrBtn().pause(StaticProps.MEDIUM_PAUSE).waitForNotPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35108")
    @Features(Config.GroupProps.TVE)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, Config.ParamProps.CMT_APP, Config.ParamProps.PARAMOUNT_APP })
    public void signInBrighthouseiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollDownToEpisodeTtl(episodeTitle);
        series.episodeLockBtn(episodeTitle).waitForVisible().tap();
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.brighthouseProviderBtn().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        selectProvider.brighthouseProviderBtn().waitForVisible().tap();
        signIn.brighthouseUsernameTxb().waitForVisible().tap().type(username);
        signIn.brighthousePasswordTxb().waitForVisible().tap().type(password);
        signIn.brighthousePasswordTxb().pause(StaticProps.MEDIUM_PAUSE);
        signIn.brighthouseSignInBtn().waitForVisible().tap();
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        series.backBtn().waitForVisible().tap();
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
}

