package com.viacom.test.vimn.uitests.tests.settings;

import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.LoginChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.LiveTV;
import com.viacom.test.vimn.uitests.pageobjects.Home;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;


public class LiveTVToSettingsAndBackTest extends BaseTest {
    //Declare pageobjects and actions
    Home home;
    SettingsMenu settingsMenu;
    SplashChain splashChain;
    ChromecastChain chromecastChain;
    LoginChain  loginChain;
    LiveTV liveTV;
    Settings settings;

    @Factory(dataProvider = Config.StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public LiveTVToSettingsAndBackTest(String runParams) { super.setRunParams(runParams);}

    @BeforeMethod(alwaysRun = true)
    public void setUpTest() {
        splashChain = new SplashChain();
        chromecastChain = new ChromecastChain();
        loginChain = new LoginChain();
        home = new Home();
        settingsMenu = new SettingsMenu();
        liveTV = new LiveTV();
        settings = new Settings();

    }

    @TestCaseId("")
    @Features(Config.GroupProps.SETTINGS)
    @Test(groups = {Config.GroupProps.FULL, Config.GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			      ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.VH1_APP, 
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.PARAMOUNT_APP })
    public void liveTVToSettingsAndBackAndroidTest() {
        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.loginIfNot();

        home.liveTVBtn().waitForVisible().tap();
        liveTV.onNowTtl().waitForVisible();
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.providerLogoImg().waitForVisible();
        settingsMenu.providerLogoImg().goBack();
        liveTV.onNowTtl().waitForVisible();
    }
}
