package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class VIPSignInCancelTest extends TveBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public VIPSignInCancelTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (xFinity == null) {
            super.initializePageObjects();
        }

    }

    /* GF - 29/06/2017 - TVLandBtn element can't be located after coming back from SignIn view.
    Since this issue is only reproducible on Android 7 devices, I'm skipping the test if device used
    Mathes this version. */
    @TestCaseId("34988")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE})
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPSignInCancelAndroidTest() {

        if (!TestRun.isAndroid7()) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();

            settings.settingsBtn().waitForVisible().tap();
            settingsMenu.signInBtn().waitForVisible().tap();
            signIn.goBackToProviders();
            selectProvider.xfinityProviderBtn().waitForVisible().tap();
            xFinity.signInRegisterBtn().waitForVisible().tap();
            xFinity.emailTxb().closeKeyboard().waitForVisible().goBack();
            xFinity.signInRegisterBtn().waitForVisible().goBack();
            selectProvider.xfinityProviderBtn().waitForVisible().goBack();
            settingsMenu.signInBtn().waitForVisible();
        } else {
            throw new SkipException("Test broken on Android 7 devices for unknown reason.");
        }
    }

    @TestCaseId("34988")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPSignInCanceliOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.xfinityProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        
        xFinity.signInRegisterBtn().waitForVisible().tap();
        xFinity.signInCancelBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible();
        selectProvider.backBtn().waitForVisible().tap();
        if (TestRun.isPhone()) {
            signIn.closeBtn().waitForVisible().tap();
        }
        settingsMenu.autoPlayTgl().waitForVisible();
        
    }
    
}
