package com.viacom.test.vimn.uitests.tests.settings;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.BaseTest;
import com.viacom.test.vimn.common.exceptions.DeviceSettingsException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.actionchains.AlertsChain;
import com.viacom.test.vimn.uitests.actionchains.ChromecastChain;
import com.viacom.test.vimn.uitests.actionchains.SplashChain;
import com.viacom.test.vimn.uitests.pageobjects.Apptentive;
import com.viacom.test.vimn.uitests.pageobjects.Contact;
import com.viacom.test.vimn.uitests.pageobjects.Email;
import com.viacom.test.vimn.uitests.pageobjects.GMail;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.feeds.MainConfig;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ContactTest extends BaseTest {

    //Declare page objects/actions
	SplashChain splashChain;
	Settings settings;
	SettingsMenu settingsMenu;
	GMail gmail;
	Email email;
    Contact contact;
    LocaleDataFactory localeDataFactory;
    ChromecastChain chromecastChain;
    BrandDataFactory brandDataFactory;
    AlertsChain alertschain;
    Apptentive apptentive;
    
    //Declare data
    String supportEmail;
    String subjectTxt;
    String bodyTxt;
    Boolean apptentiveEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ContactTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

    	splashChain = new SplashChain();
    	settings = new Settings();
    	settingsMenu = new SettingsMenu();
    	gmail = new GMail();
    	email = new Email();
        contact = new Contact();
        localeDataFactory = new LocaleDataFactory();
        chromecastChain = new ChromecastChain();
        brandDataFactory = new BrandDataFactory();
        alertschain = new AlertsChain();
        apptentive = new Apptentive();

        supportEmail = contact.getSupportEmail();
        subjectTxt = contact.getSubjectTxt();
        bodyTxt = contact.getBodyTxt();
        
        apptentiveEnabled = MainConfig.isApptentiveEnabled();
    }


    //GF - 02/06/2017 - Set to broken until all devices in lab are correctly signed in to google accounts.
    @TestCaseId("35063")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.BROKEN, GroupProps.SETTINGS })
    @Parameters({ ParamProps.ANDROID, ParamProps.ALL_APPS })
    public void contactAndroidTest() {
        
    	splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.contactBtn().waitForVisible().tap();
        gmail.gMailTxt().waitForVisible().tap();
        gmail.toTxb().waitForScrolledTo().waitForText(supportEmail);
        gmail.subjectTxb(subjectTxt).isVisible();
        gmail.bodyTxb().waitForScrolledTo().closeKeyboard().goBack();
        // TODO - os and device specific checks.
        
        settingsMenu.contactBtn().waitForVisible();
        
    }

    @TestCaseId("35063")
    @Features(GroupProps.SETTINGS)
    @Test(groups = { GroupProps.FULL, GroupProps.SETTINGS  })
    @Parameters({ ParamProps.IOS, ParamProps.ALL_APPS })
    public void contactiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        
        settings.settingsBtn().waitForVisible().tap();
        
        settingsMenu.contactBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);
        if (email.CouldNotSendBtn().isVisible()) {
        	  String message = "Email not configured in local device";
              Logger.logMessage(message);
              throw new DeviceSettingsException(message);
        } else if (!apptentiveEnabled) {
        	   email.toTxb().waitForText(supportEmail);
        	   email.subjectTxb().waitForText(subjectTxt);
               email.cancelBtn().waitForVisible().tap();
               email.deleteDraftBtn().waitForVisible().tap();
               // TODO - additional body, os and device specific checks.
               
          settingsMenu.contactBtn().waitForVisible();
    	} else {
        	apptentive.profileBtn().waitForVisible();
        	apptentive.messageCenterHeader().waitForVisible();
        	apptentive.apptentiveCloseBtn().waitForVisible();
        	apptentive.newMessageBtn().waitForVisible();
        	apptentive.attachBtn().waitForVisible();
        	apptentive.sendBtn().waitForVisible();
    	}
        
    }
    
}
