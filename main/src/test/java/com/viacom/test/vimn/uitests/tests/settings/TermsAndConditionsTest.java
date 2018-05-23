package com.viacom.test.vimn.uitests.tests.settings;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.TermsAndConditions;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class TermsAndConditionsTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Settings settings;
	SettingsMenu settingsMenu;
	TermsAndConditions termsAndConditions;
	Navigate navigate;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
    
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public TermsAndConditionsTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	termsAndConditions = new TermsAndConditions();
    	navigate = new Navigate();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    }

    @TestCaseId("35062")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void termsAndConditionsAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.termsAndConditionsBtn().waitForVisible().tap();

        if (!TestRun.getLocale().equals("pt_br")) {
            termsAndConditions.termsOfUseTtl().waitForVisible();
            if (TestRun.isTVLandApp() || TestRun.isCMTApp() || TestRun.isVH1App()) {
                termsAndConditions.termsOfUseLnk().waitForVisible().goBack();
            } else {
                termsAndConditions.termsOfUseBdy().waitForVisible().goBack();
            }
        } else {
            termsAndConditions.termsOfUseBrTtl().waitForVisible();
            termsAndConditions.termsOfUseBrBdy().waitForVisible().goBack();
        }

        settingsMenu.termsAndConditionsBtn().waitForVisible();
    }

    @TestCaseId("35062")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void termsAndConditionsiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.termsAndConditionsBtn().waitForVisible().tap();
        if (TestRun.isTVLandApp() || TestRun.isCMTApp()) {
        	termsAndConditions.termsOfUseLnk().waitForVisible();
        } else {
        	termsAndConditions.termsOfUseBdy().waitForVisible();
        }
        if (TestRun.isPhone()) {
        	settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed
        }
        
        settingsMenu.termsAndConditionsBtn().waitForVisible();
        
    }
    
}
