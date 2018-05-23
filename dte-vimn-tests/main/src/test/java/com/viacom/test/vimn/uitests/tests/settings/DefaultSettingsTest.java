package com.viacom.test.vimn.uitests.tests.settings;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.AutoPlayChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class DefaultSettingsTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Settings settings;
    SettingsMenu settingsMenu;
    Home home;
    ChromecastChain chromecastChain;
    AlertsChain alertschain;
    AutoPlayChain autoplaychain;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public DefaultSettingsTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        home = new Home();
        chromecastChain = new ChromecastChain();
        alertschain = new AlertsChain();
        autoplaychain = new AutoPlayChain();
    }

    @TestCaseId("35058")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.ALL_APPS })
    public void defaultSettingsAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();

        if (settingsMenu.autoPlayTgl().waitForVisible().hasAttribute("text", "ON")) {
            Logger.logMessage("Autoplay is ON");
        } else {
            Assert.fail("Autoplay toggle is OFF");
        }
        if (settingsMenu.cellularTgl().waitForVisible().hasAttribute("text", "ON")) {
            Logger.logMessage("Cellular is ON");
        } else {
            Assert.fail("Cellular toggle is OFF");
        }

    }

    @TestCaseId("35058")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SETTINGS  })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void DefaultSettingsiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        autoplaychain.enableAutoPlay();
        autoplaychain.enableAutoPlayClip();
        
        settings.settingsBtn().waitForVisible().tap();

            if (settingsMenu.autoPlayTgl().waitForVisible().hasAttribute("value", "true") || settingsMenu.autoPlayTgl().waitForVisible().hasAttribute("value", "1")) {
                Logger.logMessage("Autoplay toggle is ON");
            } else {
                Assert.fail("Autoplay toggle is OFF");
            }
            if (settingsMenu.cellularTgl().waitForVisible().hasAttribute("value", "true") || settingsMenu.cellularTgl().waitForVisible().hasAttribute("value", "1")) {
                Logger.logMessage("Cellular toggle is ON");
            } else {
                Assert.fail("Cellular toggle is OFF");
            }
            if (settingsMenu.autoPlayClipsTgl().waitForVisible().hasAttribute("value", "true") || settingsMenu.autoPlayClipsTgl().waitForVisible().hasAttribute("value", "1")) {
                Logger.logMessage("Autoplay Extras toggle is ON");
            } else {
                Assert.fail("Autoplay Extras toggle is OFF");
            }
    }

}


