package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SettingsMenu {

    private Locator locator;
    private String appVersion = "";

    // PAGE OBJECT CONSTRUCTOR
    public SettingsMenu() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact tvProviderTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact providerTtl(String providerTtl) {
        return new Interact(locator.getLocator(providerTtl), locator.locatorData);
    }

    public Interact signInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signOutBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact autoPlayTgl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact autoPlayClipsTgl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact cellularTgl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact termsAndConditionsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact legalNoticesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact contactBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact providerLogoImg() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact buildVersion(String buildVersion) {
        return new Interact(locator.getLocator(buildVersion), locator.locatorData);
    }

    public Interact buildVersion() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact pleaseWaitTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact tvProviderSectionTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact videoPlayBackSectionTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact helpSectionTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    // TODO - Add iOS support to this method
    /**********************************************************************************************
     * ANDROID ONLY
     * Gets the app version using adb from the android package.
     *
     * @author Vincent Barresi created October 26, 2016
     * @version 1.0.0 October 26, 2016
     * @return Version number of application.
     ***********************************************************************************************/
    public String getAppVersion() {
        if (TestRun.isAndroid()) {
            Logger.logMessage("Grepping app version from app package.");
            Locator locator = new Locator();
            String command = "adb shell dumpsys package $packageName | grep versionName";

            Runtime run = Runtime.getRuntime();
            try {
                Process process = run.exec(command.replace("$packageName", locator.getAppPackage()));
                process.waitFor();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    appVersion = line.split("=")[1].trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // do nothing
        }
        return appVersion;
    }

    public String getAutoPlayStatus() {
    	if (TestRun.isIos()) {
    		return this.autoPlayTgl().waitForVisible().getMobileElement().getAttribute("value").toLowerCase();
    	}
        return this.autoPlayTgl().waitForVisible().getMobileElement().getAttribute("text").toLowerCase();
    }

    public String getCellVidPlaybackStatus() {
    	if (TestRun.isIos()) {
    		return this.cellularTgl().waitForVisible().getMobileElement().getAttribute("value").toLowerCase();
    	}
        return this.cellularTgl().waitForVisible().getMobileElement().getAttribute("text").toLowerCase();
    }

	public String getAutoPlayClipsStatus() {
		if (TestRun.isIos()) {
    		return this.autoPlayClipsTgl().waitForVisible().getMobileElement().getAttribute("value").toLowerCase();
    	}
        return this.autoPlayClipsTgl().waitForVisible().getMobileElement().getAttribute("text").toLowerCase();
	}
}
