package com.viacom.test.vimn.uitests.tests.apptentive;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.ApptentiveNotEnabledException;
import com.viacom.test.vimn.common.exceptions.DeviceSettingsException;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Apptentive;
import com.viacom.test.vimn.uitests.pageobjects.Email;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class VisitMessageCenterTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
    ChromecastChain chromecastChain;
	Settings settings;
	SettingsMenu settingsMenu;
	Email email;
    AlertsChain alertschain;
    Apptentive apptentive;
    
    //Declare data
    Boolean apptentiveEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public VisitMessageCenterTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	chromecastChain = new ChromecastChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	email = new Email();
        alertschain = new AlertsChain();
        apptentive = new Apptentive();

        apptentiveEnabled = MainConfig.isApptentiveEnabled();
    }

    @TestCaseId("35050")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.APPTENTIVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void visitMessageCenterAndroidTest() {

		if (apptentiveEnabled) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();

			settings.settingsBtn().waitForVisible().tap();
			settingsMenu.contactBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);

			apptentive.profileBtn().waitForVisible();
			apptentive.messageCenterHeader().waitForVisible();
			apptentive.apptentiveCloseBtn().waitForVisible();
			apptentive.newMessageBtn().waitForVisible();
			apptentive.attachBtn().waitForVisible();
			apptentive.sendBtn().waitForVisible();

		} else {
			String message = "Apptentive is not enabled for " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";

			throw new ApptentiveNotEnabledException(message);
		}
	}

    @TestCaseId("35050")
    @Features(GroupProps.APPTENTIVE)
    @Test(groups = { GroupProps.FULL, GroupProps.APPTENTIVE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void visitMessageCenteriOSTest() {
        
    	if (apptentiveEnabled) {
    		
        	splashChain.splashAtRest();
        	alertschain.dismissAlerts();
        	
            settings.settingsBtn().waitForVisible().tap();
            
            settingsMenu.contactBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);
            //@PV - email not configured in device
            if (email.CouldNotSendBtn().isVisible()) {
            	String message = "Email not configured in local device";
                Logger.logMessage(message);
                throw new DeviceSettingsException(message);
            } else {
            	apptentive.profileBtn().waitForVisible();
            	apptentive.messageCenterHeader().waitForVisible();
            	//apptentive.apptentiveCloseBtn().waitForVisible(); // There are two same type found by predicate in Tablet. Don't know how to handle that.
            	if (apptentive.composeBtn().isVisible()) {
            		apptentive.newMessageBtn().waitForVisible();
            	} else {
            		apptentive.newMessageTxt().waitForVisible();
                	apptentive.attachBtn().waitForVisible();
                	apptentive.sendBtn().waitForVisible();
            	}
            	
        	}
    	} else {
			String message = "Apptentive is not enabled for " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			
			throw new ApptentiveNotEnabledException(message);
    	}
    }
}
