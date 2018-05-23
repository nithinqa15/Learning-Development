package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class XfinityErrorFeedbackTest extends TveBaseTest {

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public XfinityErrorFeedbackTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderInvalidUsername(Config.StaticProps.XFINITY);
        password = ProviderManager.getProviderInvalidPassword(Config.StaticProps.XFINITY);
        providerName = ProviderManager.getProvider(Config.StaticProps.XFINITY);

    }

    @TestCaseId("34983")
    @Features(Config.GroupProps.TVE)
    @Test(groups = { Config.GroupProps.DEPRECATED, Config.GroupProps.TVE })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.TVLAND_APP, Config.ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void xfinityErrorFeedbackAndroidTest() {

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
        xFinity.signInBtn().waitForVisible().tap();
        Assert.assertTrue(xFinity.emptyEmailMsg().isPresent(), "Empty Email Error Message is Not Present.");

        xFinity.okAlertBtn().waitForPresent().tap();
        xFinity.emailTxb().waitForVisible().tap().type(username).closeKeyboard();
        xFinity.signInBtn().waitForVisible().tap();
        Assert.assertTrue(xFinity.emptyPasswordMsg().isPresent(), "Empty Password Error Message is Not Present.");

        xFinity.okAlertBtn().waitForPresent().tap();
        xFinity.passwordTxb().waitForVisible().tap().type(password).closeKeyboard();
        xFinity.signInBtn().waitForVisible().tap();
        Assert.assertTrue(xFinity.invalidEmailPasswordTxt().isPresent(), "Accepted invalid credentials.");

        xFinity.okAlertBtn().waitForPresent().tap();
        xFinity.signUpBtn().waitForVisible().tap();
        xFinity.signUpEmailTxb().waitForVisible().tap().type(username).closeKeyboard();
        xFinity.passwordTxb().waitForVisible().tap().type(password).closeKeyboard();
        xFinity.dateOfBirthTxb().waitForVisible().tap();
        xFinity.okAlertBtn().waitForVisible().tap();
        xFinity.signUpBtn().waitForVisible().tap();
        Assert.assertTrue(xFinity.signInRegisterBtn().isPresent(), "Not Brought back to Xfinity Sign-In Screen, thus accepted invalid-DoB.");

    }
}


