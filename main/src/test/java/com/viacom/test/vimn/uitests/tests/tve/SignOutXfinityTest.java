package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class SignOutXfinityTest  extends TveBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignOutXfinityTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(Config.StaticProps.XFINITY);
        password = ProviderManager.getProviderPassword(Config.StaticProps.XFINITY);
        providerName = ProviderManager.getProvider(Config.StaticProps.XFINITY);

    }

    @TestCaseId("34989")
    @Features(Config.GroupProps.TVE)
    @Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.TVE })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void signOutXfinityAndroidTest() {

        ProxyUtils.disableAds();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesTtl(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible().tap();
        xFinity.emailTxb().waitForVisible().tap().type(username).closeKeyboard();
        xFinity.passwordTxb().waitForVisible().tap().type(password).closeKeyboard();
        xFinity.signInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        series.backBtn().waitForVisible().tap();
        home.seriesTtl(seriesTitle).isPresent();
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signOutBtn().waitForVisible().tap();
        settingsMenu.signInBtn().isPresent();
    }
}
