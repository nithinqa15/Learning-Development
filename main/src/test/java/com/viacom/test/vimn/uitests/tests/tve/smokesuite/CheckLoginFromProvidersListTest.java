package com.viacom.test.vimn.uitests.tests.tve.smokesuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class CheckLoginFromProvidersListTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Settings settings;
	SettingsMenu settingsMenu;
	SelectProvider selectProvider;
	SignIn signIn;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	
    //Declare data
    String providerName;
    String username;
    String password;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public CheckLoginFromProvidersListTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	selectProvider = new SelectProvider();
    	signIn = new SignIn();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    	
    	providerName = ProviderManager.getDefaultProvider();
    	username = ProviderManager.getProviderUsername(StaticProps.OPTIMUM);
        password = ProviderManager.getProviderPassword(StaticProps.OPTIMUM);
    	
    }

    @TestCaseId("C53422")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
	  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
	  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
	  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })

    public void checkLoginFromProvidersListAndroidTest() {
    	
            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();

            settings.settingsBtn().waitForVisible().tap();
            settingsMenu.signInBtn().waitForVisible().tap();
            selectProvider.viewAllProvidersBtn().waitForVisible().tap();
            selectProvider.typeProviderNameTxb().waitForVisible().clearText(50).type(providerName).closeKeyboard().pause(3000);
            selectProvider.firstProviderOption().waitForVisible().tap();
            
            signIn.optimumUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.optimumPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.optimumSignInBtn().waitForVisible().tap();
            signIn.successTxt().waitForVisible().waitForNotPresent();

    }

}


