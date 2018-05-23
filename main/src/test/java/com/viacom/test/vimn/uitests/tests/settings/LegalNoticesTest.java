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
import com.viacom.test.vimn.uitests.pageobjects.LegalNotices;
import com.viacom.test.vimn.uitests.pageobjects.Navigate;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class LegalNoticesTest extends BaseTest {

    //Declare page objects/actions
    SplashChain splashChain;
	Settings settings;
	SettingsMenu settingsMenu;
	LegalNotices legalNotices;
	Navigate navigate;
	ChromecastChain chromecastChain;
	AlertsChain alertschain;
    
	@Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public LegalNoticesTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	legalNotices = new LegalNotices();
    	navigate = new Navigate();
    	chromecastChain = new ChromecastChain();
    	alertschain = new AlertsChain();
    }

    @TestCaseId("35068")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void legalNoticesAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.legalNoticesBtn().waitForVisible().tap();

        if (!TestRun.getLocale().equals("pt_br")) {
            legalNotices.legalNoticesTtl().waitForVisible();

            if (TestRun.isTVLandApp()) {
                legalNotices.closedCaptionLnk().waitForVisible();
                legalNotices.endUserAgreementLnk().waitForVisible().goBack();
            } else {
                legalNotices.legalNoticesBdy().waitForVisible().goBack();
            }
        } else {
            legalNotices.legalNoticesBrTtl().waitForVisible();
            legalNotices.legalNoticesBrBdy().waitForVisible().goBack();
        }

        settingsMenu.legalNoticesBtn().waitForVisible();
    }

    @TestCaseId("35068")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void legalNoticesiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.legalNoticesBtn().waitForVisible().tap();
        legalNotices.legalNoticesTtl().waitForVisible();
        if (TestRun.isTVLandApp() || TestRun.isCMTApp()) {
        	legalNotices.closedCaptionLnk().waitForVisible();
        	legalNotices.endUserAgreementLnk().waitForVisible();
        } else {
        	legalNotices.legalNoticesBdy().waitForVisible();
        }
        if (TestRun.isPhone()) {
        	settings.settingsBtn().waitForVisible().tap(); //Back button locator name changed
        }
        
        settingsMenu.legalNoticesBtn().waitForVisible();
        
    }
    
}
