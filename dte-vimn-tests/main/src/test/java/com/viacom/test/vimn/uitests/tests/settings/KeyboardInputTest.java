package com.viacom.test.vimn.uitests.tests.settings;

import org.json.JSONException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.Keyboard;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class KeyboardInputTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
    Settings settings;
    SettingsMenu settingsMenu;
    Home home;
    Keyboard keyboard;
    LoginChain loginChain;
    SelectProvider selectProvider;
    SignIn signIn;
    AlertsChain alertschain;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public KeyboardInputTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        splashChain = new SplashChain();
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        home = new Home();
        keyboard = new Keyboard();
        selectProvider = new SelectProvider();
        signIn = new SignIn();
        loginChain = new LoginChain();
        alertschain = new AlertsChain();
    }

    @TestCaseId("")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = { Config.GroupProps.FULL, Config.GroupProps.SETTINGS})
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.ALL_APPS })
    public void KeyboardInputiOSTest() throws JSONException {
        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        loginChain.findAndTapProviderBtn();

        if (keyboard.nextBtn().isVisible() && keyboard.deleteBtn().isVisible() &&
                keyboard.previousBtn().isVisible() && keyboard.GoBtn().isVisible()) {
            Logger.logMessage("Verified that Keyboard is pulled up and the previous, next, delete," +
                    " and go buttons are present");
        }
        else {
            Logger.logMessage("One or more of the expected " +
                    "keys are missing from the keyboard.");
            org.testng.Assert.fail();
        }
    }

}

