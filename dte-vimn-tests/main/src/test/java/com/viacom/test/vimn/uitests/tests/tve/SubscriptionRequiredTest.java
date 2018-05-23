package com.viacom.test.vimn.uitests.tests.tve;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SubscriptionRequiredTest extends TveBaseTest {

    //Declare data
    String defaultProviderName;
    String defaultProviderUsername;
    String defaultProviderPassword;
    String defaultProviderNameAddedToUsernameTxb;
    String defaultProviderNameAddedToPasswordTxb;
    String defaultNoAuthZUsername;
    String defaultNoAuthZPassword;
    Method usernameTxb, passwordTxb;
    Interact usernameTxtb, passwordTxtb;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SubscriptionRequiredTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        defaultProviderName = providerManager.getDefaultProvider();
        defaultNoAuthZUsername = providerManager.getDefaultNoAuthZUsername();
        defaultNoAuthZPassword = providerManager.getDefaultNoAuthZPassword();
        defaultProviderNameAddedToUsernameTxb = providerManager.getDefaultProvider().toLowerCase() + "UsernameTxb";
        defaultProviderNameAddedToPasswordTxb = providerManager.getDefaultProvider().toLowerCase() + "PasswordTxb";
        usernameTxb = SignIn.class.getMethod(defaultProviderNameAddedToUsernameTxb); // Reflection to prepend default TV Provider name to SignIn method
        passwordTxb = SignIn.class.getMethod(defaultProviderNameAddedToPasswordTxb);
    }

    @TestCaseId("35081")
    @Features(GroupProps.TVE)
    @Test(groups = {GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP }) // TODO add Auth-N/No Auth-Z creds for international apps(Config.ParamProps.CC_APP, Config.ParamProps.MTV_APP)
    public void subscriptionRequiredAndroidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().pause(StaticProps.LARGE_PAUSE).tap();
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(defaultProviderName).closeKeyboard().submit();
        usernameTxtb = (Interact) usernameTxb.invoke(signIn); // Equivalent to: signIn.{defaultProviderName}UsernameTxb()
        usernameTxtb.waitForVisible().type(defaultNoAuthZUsername).closeKeyboard();
        passwordTxtb = (Interact) passwordTxb.invoke(signIn);
        passwordTxtb.waitForVisible().tap().type(defaultNoAuthZPassword).closeKeyboard().submit();
        signIn.subscriptionRequiredText().waitForVisible().tap();
        Assert.assertTrue(selectProvider.dontSeeProviderBtn().isPresent(), "Subscription Required Text not present");
    }
}
