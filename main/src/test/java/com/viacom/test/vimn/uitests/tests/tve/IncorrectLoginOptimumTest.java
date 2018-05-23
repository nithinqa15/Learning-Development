package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
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

public class IncorrectLoginOptimumTest extends TveBaseTest {
    
    //Declare data
    String invalid;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public IncorrectLoginOptimumTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (splashChain == null) {
            super.initializePageObjects();
        }
    	
    	invalid = "invalid";
    	
    }

    @TestCaseId("35078")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
	  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
	  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
	  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void incorrectLoginOptimumAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        settings.settingsBtn().waitForVisible().tap();

        settingsMenu.signInBtn().waitForVisible().tap();
        signIn.goBackToProviders();
        selectProvider.optimumProviderBtn().waitForVisible().tap();
        signIn.optimumUsernameTxb().waitForVisible().tap().type(invalid);
        signIn.optimumPasswordTxb().hideKeyboard().waitForVisible().tap().type(invalid);
        signIn.optimumSignInBtn().hideKeyboard().waitForVisible().tap();
        signIn.optimumFailedLoginTxt().waitForVisible();
        signIn.backBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        selectProvider.optimumProviderBtn().waitForVisible().goBack();
        settingsMenu.signInBtn().waitForVisible();

    }

    @TestCaseId("35078")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void incorrectLoginOptimumiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        settings.settingsBtn().waitForVisible().tap();
        
        settingsMenu.signInBtn().waitForVisible().tap();
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        } else if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.optimumProviderBtn().waitForVisible().tap();
        signIn.optimumUsernameTxb().waitForVisible().tap().pause(1000).type(invalid);
        keyboard.goToPasswordField();
        signIn.optimumPasswordTxb().waitForVisible().tap().pause(1000).type(invalid);
        //Hacky method to tap onto the sign button
        if (keyboard.GoBtn().isVisible()) {
            keyboard.GoBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        } else if (keyboard.doneBtn().isVisible()) {
            keyboard.doneBtn().waitForVisible().tap().pause(StaticProps.LARGE_PAUSE);
        }
        signIn.optimumFailedLoginTxt().waitForVisible();
        
    }
    
}
