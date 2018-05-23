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
import com.viacom.test.vimn.uitests.pageobjects.PrivacyPolicy;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class PrivacyPolicyTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Settings settings;
	SettingsMenu settingsMenu;
    PrivacyPolicy privacyPolicy;
    Navigate navigate;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public PrivacyPolicyTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	privacyPolicy = new PrivacyPolicy();
    	navigate = new Navigate();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    }

    @TestCaseId("35061")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void privacyPolicyAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.privacyPolicyBtn().waitForVisible().tap();

        if (!TestRun.getLocale().equals("pt_br")) {
            privacyPolicy.privacyPolicyTtl().waitForVisible();

            if (TestRun.isTVLandApp() || TestRun.isCMTApp() || TestRun.isVH1App()) {
                privacyPolicy.privacyPolicyLnk().waitForVisible();
                privacyPolicy.adChoicesLnk().waitForVisible().goBack();
            } else {
                privacyPolicy.privacyPolicyBdy().waitForVisible().goBack();
            }
        } else {
            privacyPolicy.privacyPolicyBrTtl().waitForVisible();
            privacyPolicy.privacyPolicyBrBdy().waitForVisible().goBack();
        }

        settingsMenu.privacyPolicyBtn().waitForVisible();
    }

    @TestCaseId("35061")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void privacyPolicyiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.privacyPolicyBtn().waitForVisible().tap();
        if (TestRun.isTVLandApp() || TestRun.isCMTApp()) {
        	privacyPolicy.privacyPolicyLnk().waitForVisible();
        	privacyPolicy.adChoicesLnk().waitForVisible();
        } else {
        	privacyPolicy.privacyPolicyBdy().waitForVisible();
        }
        if (TestRun.isPhone()) {
        	settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed
        }
        
        settingsMenu.privacyPolicyBtn().waitForVisible();

    }

}
