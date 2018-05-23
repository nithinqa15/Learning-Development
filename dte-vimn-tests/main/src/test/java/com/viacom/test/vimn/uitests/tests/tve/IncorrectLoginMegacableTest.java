package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderEnabled;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class IncorrectLoginMegacableTest extends TveBaseTest {

    //Declare page objects/actions
	ProviderEnabled providerEnabled;
    
    //Declare data
    String invalid;
    Boolean megacableIsEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public IncorrectLoginMegacableTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (splashChain == null) {
            super.initializePageObjects();
        }
    	providerEnabled = new ProviderEnabled();
    	
    	invalid = "invalid";
    	megacableIsEnabled = providerEnabled.isMegacableEnabled();
    	
    }

    @TestCaseId("35078")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP })
    public void incorrectLoginMegacableAndroidTest() {
        
    	splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();

        settings.settingsBtn().waitForVisible().tap();
        
        settingsMenu.signInBtn().waitForVisible().tap();
        signIn.goBackToProviders();
        selectProvider.megacableProviderBtn().waitForVisible().tap();
		signIn.megacableUsernameTxb().waitForVisible().tap().type(invalid).closeKeyboard();
        signIn.megacablePasswordTxb().waitForVisible().tap().type(invalid).closeKeyboard();
        signIn.megacableSignInBtn().waitForVisible().tap();
        signIn.megacableFailedLoginTxt().waitForVisible().goBack().goBack();
        settingsMenu.signInBtn().waitForVisible();
        
    }

    @TestCaseId("35078")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.CC_INTL_APP, ParamProps.MTV_INTL_APP, ParamProps.MEXICO })
    public void incorrectLoginMegacableiOSTest() {
    	
    	if (megacableIsEnabled) {
    	
    	splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();
        
        settings.settingsBtn().waitForVisible().tap();
        
        settingsMenu.signInBtn().waitForVisible().tap();
        
        selectProvider.megacableProviderBtn().waitForVisible().tap();
		signIn.megacableUsernameTxb().waitForVisible().tap().type(invalid);
        signIn.megacablePasswordTxb().waitForVisible().tap().type(invalid);
        if (keyboard.doneBtn().isVisible()) {
        	keyboard.doneBtn().waitForVisible().tap();
        } else if (keyboard.GoBtn().isVisible()) {
        	keyboard.GoBtn().waitForVisible().tap();
        }
        signIn.megacableSignInBtn().waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
        signIn.megacableFailedLoginTxt().waitForVisible();
        if (TestRun.isPhone()) {
            signIn.closeBtn().waitForVisible().tap();
        }
        settingsMenu.autoPlayTgl().waitForViewLoad().waitForVisible();
        
     } else {
    		String message = "Megacable is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
     }
    	
    }
    
   }
