package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MVPDRecallTest extends TveBaseTest {

    //Declare data
    String defaultProviderName;
    String defaultProviderUsername;
    String defaultProviderPassword;
    String defaultProviderNameAddedToUsernameTxb;
    String defaultProviderNameAddedToPasswordTxb;
    Method usernameTxb, passwordTxb;
    Interact usernameTxtb, passwordTxtb;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public MVPDRecallTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        defaultProviderName = providerManager.getDefaultProvider();
        defaultProviderUsername = providerManager.getDefaultUsername();
        defaultProviderPassword = providerManager.getDefaultPassword();
        defaultProviderNameAddedToUsernameTxb = providerManager.getDefaultProvider().toLowerCase() + "UsernameTxb";
        defaultProviderNameAddedToPasswordTxb = providerManager.getDefaultProvider().toLowerCase() + "PasswordTxb";
        usernameTxb = SignIn.class.getMethod(defaultProviderNameAddedToUsernameTxb); // Reflection to prepend default TV Provider name to SignIn method
        passwordTxb = SignIn.class.getMethod(defaultProviderNameAddedToPasswordTxb);
    }

    @TestCaseId("35071")
    @Features(GroupProps.TVE)
    @Test(groups = {GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void mvpdRecallAndroidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        //Sign-in via MVPD Screen
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().pause(StaticProps.LARGE_PAUSE).tap();
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(defaultProviderName).closeKeyboard().submit();
        usernameTxtb = (Interact) usernameTxb.invoke(signIn); // Equivalent to: signIn.{defaultProviderName}UsernameTxb()
        usernameTxtb.waitForVisible().type(defaultProviderUsername).closeKeyboard();
        passwordTxtb = (Interact) passwordTxb.invoke(signIn);
        passwordTxtb.waitForVisible().tap().type(defaultProviderPassword).closeKeyboard().submit();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();

        // Sign-in directly via TV Provider Screen due to MVPD Recall
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signOutBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        usernameTxtb.waitForVisible().type(defaultProviderUsername).closeKeyboard();
        passwordTxtb.waitForVisible().tap().type(defaultProviderPassword).closeKeyboard().submit();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
    }
}
