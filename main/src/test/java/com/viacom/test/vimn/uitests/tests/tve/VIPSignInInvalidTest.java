package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class VIPSignInInvalidTest extends TveBaseTest {
    
    //Declare data
    String invalid;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public VIPSignInInvalidTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (xFinity == null) {
            super.initializePageObjects();
        }
    	
    	invalid = "invalid";

    }

    @TestCaseId("34993")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.VH1_APP, ParamProps.CMT_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPSignInInvalidAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        signIn.goBackToProviders();
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible().tap();
        xFinity.emailTxb().waitForVisible().tap().type(invalid).closeKeyboard();
        xFinity.passwordTxb().waitForVisible().tap().type(invalid).closeKeyboard();
        xFinity.signInBtn().waitForVisible().tap();
        xFinity.invalidEmailPasswordTxt().waitForVisible();
        xFinity.okayBtn().waitForVisible().tap();
        xFinity.signInBtn().waitForVisible();
        // Last 3 steps descoped because UIAutomator fails to locate TVLand button even when visible
        //xFinity.TVLandBtn().waitForVisible().goBack();
        //selectProvider.xfinityProviderBtn().waitForVisible().goBack();
        //settingsMenu.signInBtn().waitForVisible();

    }

    @TestCaseId("34993")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPSignInInvalidiOSTest() {
        
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
        xFinity.emailTxb().waitForVisible().tap().type(invalid);
        xFinity.passwordTxb().waitForVisible().tap().type(invalid).pause(StaticProps.XLARGE_PAUSE);
        xFinity.signInBtn().waitForVisible().tap();
        xFinity.invalidEmailPasswordTxt().waitForVisible();
        xFinity.okAlertBtn().waitForVisible().tap();

        xFinity.signInCancelBtn().waitForVisible().tap();
        xFinity.signInRegisterBtn().waitForVisible();
        selectProvider.backBtn().waitForVisible().tap();
        if (TestRun.isPhone()) {
            signIn.closeBtn().waitForVisible().tap();
        }
        settingsMenu.autoPlayTgl().waitForVisible();
        
    }
    
}
