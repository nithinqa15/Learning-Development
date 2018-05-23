package com.viacom.test.vimn.uitests.tests.tve;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ForgotUserIdOrPasswordTest extends TveBaseTest {

    //Declare data
    String defaultProviderName;
    String defaultProviderNameAddedToForgotIdLink;
    String defaultProviderNameAddedToForgotPasswordLink;
    String defaultProviderNameAddedToForgotIdScreen;
    String defaultProviderNameAddedToForgotPasswordScreen;
    Method forgotIdLnk, forgotPasswordLnk, forgotIdSrn, forgotPasswordSrn;
    Interact forgotIdLink, forgotPasswordLink, forgotIdScreen, forgotPasswordScreen;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ForgotUserIdOrPasswordTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() throws NoSuchMethodException {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        defaultProviderName = providerManager.getDefaultProvider();

        defaultProviderNameAddedToForgotIdLink = providerManager.getDefaultProvider().toLowerCase() + "ForgotIdLink";
        forgotIdLnk = SignIn.class.getMethod(defaultProviderNameAddedToForgotIdLink); // Reflection to prepend default TV Provider name to SignIn method

        defaultProviderNameAddedToForgotPasswordLink = providerManager.getDefaultProvider().toLowerCase() + "ForgotPasswordLink";
        forgotPasswordLnk = SignIn.class.getMethod(defaultProviderNameAddedToForgotPasswordLink);

        defaultProviderNameAddedToForgotIdScreen = providerManager.getDefaultProvider().toLowerCase() + "ForgotIdScreen";
        forgotIdSrn = SignIn.class.getMethod(defaultProviderNameAddedToForgotIdScreen);

        defaultProviderNameAddedToForgotPasswordScreen = providerManager.getDefaultProvider().toLowerCase() + "ForgotPasswordScreen";
        forgotPasswordSrn = SignIn.class.getMethod(defaultProviderNameAddedToForgotPasswordScreen);

    }

    @TestCaseId("35080")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void forgotUserIdOrPasswordAndroidTest() throws InvocationTargetException, IllegalAccessException {

        ProxyUtils.disableAds();

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        home.seriesTtl(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(defaultProviderName).closeKeyboard().submit();
        forgotIdLink = (Interact) forgotIdLnk.invoke(signIn); // Equivalent to: signIn.{defaultProviderName}ForgotIdLink()
        forgotIdLink.waitForVisible().pause(StaticProps.LARGE_PAUSE).tap().pause(15000); //Config.StaticProps.XXLARGE_PAUSE)
        forgotIdScreen = (Interact) forgotIdSrn.invoke(signIn);
        Assert.assertTrue(forgotIdScreen.waitForVisible().isPresent(), "Forgot ID Screen not present");

        signIn.backBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().clearText(20).type(defaultProviderName).closeKeyboard().submit();
        forgotPasswordLink = (Interact) forgotPasswordLnk.invoke(signIn);
        forgotPasswordLink.waitForVisible().tap().pause(StaticProps.XXLARGE_PAUSE);
        forgotPasswordScreen = (Interact) forgotPasswordSrn.invoke(signIn);
        Assert.assertTrue(forgotPasswordScreen.waitForVisible().isPresent(), "Forgot Password Screen not present");

    }
}