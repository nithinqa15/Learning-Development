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

public class SendMessageTest extends BaseTest {

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
    String msgContent = "Hello! Test from QA" ;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SendMessageTest(String runParams) {
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

    @TestCaseId("35049")
    @Features(GroupProps.APPTENTIVE)
    @Test(groups = { GroupProps.FULL, GroupProps.APPTENTIVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void SendMessageAndroidTest() {
        
		if (apptentiveEnabled) {

			splashChain.splashAtRest();
			chromecastChain.dismissChromecast();

			settings.settingsBtn().waitForVisible().tap();
			settingsMenu.contactBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);

			if (apptentive.detailedFeedbackBtn().isVisible()) { // First message to message center
				apptentive.detailedFeedbackBtn().waitForVisible().tap().type(msgContent);
			} else { // second or more messages to message center
				apptentive.composeBtn().waitForVisible().tap().type(msgContent);
			}

			// scrolldown the message list if its more than one
			apptentive.sendBtn().scrolledDownOnApptentiveMessageWindow(5, 300).tap();

			// Check Who are we Diag present or not
			if (apptentive.whoAreWeDiag().isVisible(2)) {
				apptentive.skipBtn().scrolledDownOnApptentiveMessageWindow(5, 300).tap();
			}

			apptentive.respondMessageBtn().waitForVisible();

		} else {
			String message = "Apptentive is not enabled for " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";

			throw new ApptentiveNotEnabledException(message);
		}
        
    }

    @TestCaseId("35049")
    @Features(GroupProps.APPTENTIVE)
    @Test(groups = { GroupProps.FULL, GroupProps.APPTENTIVE  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void SendMessageiOSTest() {
        
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
            	
            	if (apptentive.detailedFeedbackBtn().isVisible()) { //First message to message center
            		apptentive.detailedFeedbackBtn().waitForVisible().tap().type(msgContent);
            	} else { // second or more messages to message center
            		apptentive.composeBtn().waitForVisible().tap().type(msgContent);
            	}
            	
            	apptentive.sendBtn().scrolledDownOnApptentiveMessageWindow(5, 300).tap(); // scrolldown the message list if its more then one
            	
            	//Check Who are we Diag present or not
            	if (apptentive.whoAreWeDiag().isVisible(2)) {
            		apptentive.skipBtn().scrolledDownOnApptentiveMessageWindow(5, 300).tap();	
            	}
            	
            	apptentive.respondMessageBtn().waitForVisible();
        	}
            
    	} else {
			String message = "Apptentive is not enabled for " + TestRun.getAppName() + " " + TestRun.getLocale()
					+ " so skipping test";
			
			throw new ApptentiveNotEnabledException(message);
    	}
    }
}
