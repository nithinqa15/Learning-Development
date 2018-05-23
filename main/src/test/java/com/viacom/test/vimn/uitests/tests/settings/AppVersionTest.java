package com.viacom.test.vimn.uitests.tests.settings;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class AppVersionTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    LoginChain loginChain;
    Locator locator;
    Settings settings;
    SettingsMenu settingsMenu;
    Home home;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;

    //Declare data
    String appVersion;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public AppVersionTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        loginChain = new LoginChain();
        locator = new Locator();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        home = new Home();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
    }

    @TestCaseId("35064")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.BROKEN })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void AppVersionAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        settings.settingsBtn().waitForVisible().tap();

        appVersion = settingsMenu.getAppVersion();
        settingsMenu.buildVersion(appVersion).waitForVisible();

    }
    
    //PV - Broken until https://jira.mtvi.com/browse/VAA-3993 is solved
    @TestCaseId("35064")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SETTINGS })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void AppVersioniOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        settings.settingsBtn().waitForVisible().tap();

        appVersion = settingsMenu.getAppVersion();
        //PV - If Phone screen small then we need to support this method with scroll (iPhone 5S in lab)
        if (!settingsMenu.buildVersion(appVersion).isVisible()) {
        	settingsMenu.buildVersion(appVersion).waitForScrolledDownTo(5, 300);
        } 
        String Version_and_Build_Number = settingsMenu.buildVersion(appVersion).waitForVisible().getIOSElement().getText();
        Logger.logMessage("App Version & Build Number :  " + Version_and_Build_Number);
     }
  }