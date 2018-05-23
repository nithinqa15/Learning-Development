package com.viacom.test.vimn.uitests.tests.tve.smokesuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class CheckLoginWithAuthNNoAuthZTest extends TveBaseTest {

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
    public CheckLoginWithAuthNNoAuthZTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        defaultProviderName = ProviderManager.getDefaultProvider();
        defaultNoAuthZUsername = ProviderManager.getDefaultNoAuthZUsername();
        defaultNoAuthZPassword = ProviderManager.getDefaultNoAuthZPassword();
        defaultProviderNameAddedToUsernameTxb = ProviderManager.getDefaultProvider().toLowerCase() + "UsernameTxb";
        defaultProviderNameAddedToPasswordTxb = ProviderManager.getDefaultProvider().toLowerCase() + "PasswordTxb";
        usernameTxb = SignIn.class.getMethod(defaultProviderNameAddedToUsernameTxb); // Reflection to prepend default TV Provider name to SignIn method
        passwordTxb = SignIn.class.getMethod(defaultProviderNameAddedToPasswordTxb);
    }

    @TestCaseId("35081")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP }) 
    public void checkLoginWithAuthNNoAuthZAndroidTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().pause(StaticProps.LARGE_PAUSE).tap();
        
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(defaultProviderName).closeKeyboard().submit();
        usernameTxtb = (Interact) usernameTxb.invoke(signIn); 
        usernameTxtb.waitForVisible().type(defaultNoAuthZUsername).closeKeyboard();
        passwordTxtb = (Interact) passwordTxb.invoke(signIn);
        passwordTxtb.waitForVisible().tap().type(defaultNoAuthZPassword).closeKeyboard().submit();
        signIn.subscriptionRequiredText().waitForVisible().tap();
    
        String authzErrorCode = ProxyLogUtils.getValueFromResponseHeaderByKey(Config.StaticProps.ADOBE_AUTHORIZE_URL, Config.StaticProps.UNAUTHORIZE_HEADER);
        Assert.assertTrue(authzErrorCode.equalsIgnoreCase("noAuthz"), "User is Authenticated and Authorized");

    }
}

