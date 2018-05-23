package com.viacom.test.vimn.uitests.tests.tve.smokesuite;

import com.viacom.test.vimn.common.util.Config;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.DontSeeProvider;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class CheckDontSeeProviderFromProviderListTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	LoginChain loginChain;
	Settings settings;
	SettingsMenu settingsMenu;
	SelectProvider selectProvider;
	DontSeeProvider dontSeeProvider;
	SignIn signIn;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
	
    //Declare data
    String providerName;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public CheckDontSeeProviderFromProviderListTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {
    	
    	splashChain = new SplashChain();
    	loginChain = new LoginChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	selectProvider = new SelectProvider();
    	dontSeeProvider = new DontSeeProvider();
    	signIn = new SignIn();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    	
    	providerName = ProviderManager.getDefaultProvider();
    	
    }

    @TestCaseId("C53420+C53408")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE_SMOKE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
	  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
	  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
	  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void checkDontSeeProviderFromProviderListAndriodTest() {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();

            settings.settingsBtn().waitForVisible().tap();
            settingsMenu.signInBtn().waitForVisible().tap();
            selectProvider.viewAllProvidersBtn().waitForVisible().tap();
            selectProvider.typeProviderNameTxb()
                    .waitForVisible()
                    .tap()
                    .clearText(50)
                    .tap()
                    .type("unknown")
                    .closeKeyboard()
                    .pause(StaticProps.LARGE_PAUSE);
            selectProvider.firstProviderOption().waitForVisible().tap();
            dontSeeProvider.freePreviewBtn().waitForVisible();
            DriverManager.getAndroidDriver().navigate().back();   
            selectProvider.typeProviderNameTxb().waitForVisible();
            selectProvider.dontSeeProviderBtn().waitForVisible();

    }

}
