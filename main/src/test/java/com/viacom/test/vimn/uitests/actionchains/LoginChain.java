package com.viacom.test.vimn.uitests.actionchains;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.pageobjects.Keyboard;
import com.viacom.test.vimn.uitests.pageobjects.SelectProvider;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.support.ProviderManager;

public class LoginChain {

	private Settings settings;
	private SettingsMenu settingsMenu;
	private SelectProvider selectProvider;
	private SignIn signIn;
	private Keyboard keyboard;
	private AlertsChain alertschain;
	
	private String provider;
	private String username;
	private String password;
	
    //ACTION CHAIN CONSTRUCTOR
    public LoginChain() {
        settings = new Settings();
        settingsMenu = new SettingsMenu();
        selectProvider = new SelectProvider();
        signIn = new SignIn();
        keyboard = new Keyboard();
        alertschain = new AlertsChain();
    }

    //ACTION CHAIN METHODS
    public void logoutIfLoggedIn() {
    	settings.settingsBtn().waitForVisible().tap();
		settingsMenu.autoPlayTgl().waitForViewLoad().waitForVisible();
        if (TestRun.isAndroid()) {
            if (settingsMenu.pleaseWaitTxt().isVisible()) {
                settingsMenu.pleaseWaitTxt().waitForNotPresentOrVisible();
            }
        }
        if (settingsMenu.signOutBtn().pause(StaticProps.LARGE_PAUSE).isPresent()) {
    		settingsMenu.signOutBtn().waitForVisible().tap();
    	}
    	if (TestRun.isAndroid()) {
    	    settingsMenu.signInBtn().waitForVisible();
            DriverManager.getAndroidDriver().navigate().back();
    	} else {
    		settingsMenu.signInBtn().waitForVisible();
    		settingsMenu.backBtn().waitForVisible().tap();
    	}
    }
    
    public void loginIfNot() {
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.autoPlayTgl().waitForViewLoad().waitForVisible();
        if (TestRun.isAndroid()) {
            if (settingsMenu.pleaseWaitTxt().isVisible()) {
                settingsMenu.pleaseWaitTxt().waitForNotPresentOrVisible();
            }
        }
        if (settingsMenu.signInBtn().pause(StaticProps.LARGE_PAUSE).isPresent()) {
        	settingsMenu.signInBtn().waitForVisible().tap();
        	alertschain.dismissAlerts();
            defaultLogin();
            if (TestRun.isIos()) {
                signIn.successTxt().waitForNotPresent().pause(StaticProps.MEDIUM_PAUSE);
                settingsMenu.backBtn().waitForVisible().tap();
            }
        } else {
        	if (TestRun.isAndroid()) {
        	    settingsMenu.signOutBtn().waitForVisible();
                DriverManager.getAndroidDriver().navigate().back();
        	} else {
        		settingsMenu.signOutBtn().waitForVisible();
                settingsMenu.backBtn().waitForVisible().tap();
        	}
        }
    }
    
    public void defaultLogin() {
    	provider = ProviderManager.getDefaultProvider();
    	username = ProviderManager.getDefaultUsername();
        password = ProviderManager.getDefaultPassword();
    	if (provider.equals(StaticProps.DISH)) {
            if (TestRun.isIos()) {
                if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.megacableProviderBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
                }
            } else {
                if (signIn.signInText().isVisible()) {
                    signIn.backBtn().waitForVisible().tap();
                }
            }
    		selectProvider.dishProviderBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE);
    		signIn.dishUsernameTxb().waitForVisible().type(username).closeKeyboard();
            signIn.dishPasswordTxb().waitForVisible().type(password).closeKeyboard();
            signIn.dishSignInBtn().waitForVisible().tap();
            signIn.successTxt().waitForVisible().waitForNotPresent();
    	} else if (provider.equals(StaticProps.OPTIMUM)) {
            if (TestRun.isIos()) {
                if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
                }
            } else {
                if (signIn.signInText().isVisible()) {
                    DriverManager.getAndroidDriver().navigate().back();
                }
            }
    		selectProvider.optimumProviderBtn().waitForPresent().tapCenter();
    		if (TestRun.isIos()) {
    			try { 
    				signIn.optimumUsernameTxb().waitForVisible().tap().type(username).pause(StaticProps.XLARGE_PAUSE);
    				signIn.optimumPasswordTxb().waitForVisible().tap().type(password);
    				signIn.optimumSignInBtn().waitForVisible().tap();
    			} catch (Exception e) {
    				if (keyboard.GoBtn().isVisible(10) && !keyboard.returnBtn().isVisible()) {
    					keyboard.GoBtn().waitForVisible().tap();
    				} else {
    					keyboard.returnBtn().waitForVisible().tap();
    				}
    			}
            } else {
            	signIn.optimumUsernameTxb().waitForVisible().tap().type(username);
                signIn.optimumPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
            	signIn.optimumSignInBtn().hideKeyboard().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE);
            	//signIn.successTxt().waitForVisible().waitForNotPresent();
            }
            
    	} else if (provider.equals(StaticProps.DIRECTTV)) {
            if (TestRun.isIos()) {
                if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.directTVProviderBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
                }
            } else {
                if (signIn.signInText().waitForViewLoad().isVisible()) {
                    signIn.backBtn().waitForVisible().tap();
                }
            }
    		selectProvider.directTVProviderBtn().waitForVisible().tap();
    		signIn.directTVUsernameTxb().waitForVisible().tap().type(username);
            signIn.directTVPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
            signIn.directTVSignInBtn().hideKeyboard().waitForVisible().tap();
            signIn.successTxt().waitForVisible().waitForNotPresent();
    	} else if (provider.equals(StaticProps.SKY)) {
            if (TestRun.isIos()) {
                if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.skyProviderBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
                }
            } else {
                if (signIn.signInText().waitForViewLoad().isVisible()) {
                    DriverManager.getAndroidDriver().navigate().back();
                }
            }
            selectProvider.skyProviderBtn().waitForVisible().tap();
            signIn.skyUsernameTxb().waitForVisible().tap().type(username);
            signIn.skyPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
            signIn.skySignInBtn().hideKeyboard().waitForVisible().tap();
            signIn.successTxt().waitForVisible().waitForNotPresent();
        }

    }

    /**
     * This method taps on the default provider and username text box for the current region
     * fetched from TestRun.getLocale() and LocaleDataConfig.json
     * @author: Sidney Thelusma
     */
    public void findAndTapProviderBtn() {
        switch (TestRun.getLocale()) {
            case "en_us":
            	 if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.optimumProviderBtn().isVisible()) {
                     selectProvider.backBtn().waitForVisible().tap();
                 }
                selectProvider.optimumProviderBtn().waitForPresent().tapCenter();
                signIn.optimumUsernameTxb().waitForVisible().tap();
                break;
            case "es_mx":
                selectProvider.dishProviderBtn().waitForVisible().tap();
                signIn.dishUsernameTxb().waitForVisible().tap();
                break;
            case "es_ar":
                selectProvider.directTVProviderBtn().waitForVisible().tap();
                signIn.directTVUsernameTxb().waitForVisible().tap();
                break;
            case "es_cl":
                selectProvider.directTVProviderBtn().waitForVisible().tap();
                signIn.directTVUsernameTxb().waitForVisible().tap();
                break;
            case "es_co":
                selectProvider.directTVProviderBtn().waitForVisible().tap();
                signIn.directTVUsernameTxb().waitForVisible().tap();
                break;
            case "es_pe":
                selectProvider.directTVProviderBtn().waitForVisible().tap();
                signIn.directTVUsernameTxb().waitForVisible().tap();
                break;
            case "es_ve":
                selectProvider.directTVProviderBtn().waitForVisible().tap();
                signIn.directTVUsernameTxb().waitForVisible().tap();
                break;
            case "es_cr":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "es_sv":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "es_gt":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "es_hn":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "es_ni":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "pt_br":
                selectProvider.claroProviderBtn().waitForVisible().tap();
                signIn.claroUsernameTxb().waitForVisible().tap();
                break;
            case "en_sg":
                selectProvider.singtelProviderBtn().waitForVisible().tap();
                signIn.singtelUsernameTxb().waitForVisible().tap();
                break;
            //TODO: Add regions for MTV North when possible. See MC-5126
        }
    }
    
    /**
     * Appium methods Close and LaunchApp resets the data of the App.
     * This method restarts the App using adb commands.
     * 
     */
    public void restartApp() {
        if (TestRun.isAndroid()) {
            Logger.logMessage("App Restart");
            Runtime run = Runtime.getRuntime();
            try {
            		String appPackageID = TestRun.getAppPackageID();
                Process process = run.exec("adb shell am force-stop "
                		+ appPackageID);
                process.waitFor();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                Thread.sleep(1000);
                String androidLaunchActivity = TestRun.getAndroidLaunchActivity();
                process = run.exec("adb shell am start -n "
                		+ appPackageID +"/"+ androidLaunchActivity);
                process.waitFor();
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // do nothing
        }
    }

}