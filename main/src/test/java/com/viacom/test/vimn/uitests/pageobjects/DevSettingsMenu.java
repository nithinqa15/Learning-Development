package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class DevSettingsMenu {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public DevSettingsMenu() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact clearBalaBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact clearBalaPopUp() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact yesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact wifiBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact airplaneModeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    //iOS Only
    public void disableAirplaneModeiOS() {
    	if (airplaneModeBtn().waitForVisible().hasAttribute("value", "true") || airplaneModeBtn().waitForVisible().hasAttribute("value", "1")) {
            airplaneModeBtn().waitForVisible().tap();
    	}
    }
    
    //iOS Only
    public void disableWifiModeiOS() {
    	if (wifiBtn().waitForVisible().hasAttribute("value", "true") || wifiBtn().waitForVisible().hasAttribute("value", "1")) {
    		wifiBtn().waitForVisible().tap();
    	}
    }
    
    //iOS Only
    public void enableAirplaneModeiOS() {
    	if (airplaneModeBtn().waitForVisible().hasAttribute("value", "false") || airplaneModeBtn().waitForVisible().hasAttribute("value", "0")) {
            airplaneModeBtn().waitForVisible().tap();
    	}
    }
    
    //iOS Only
    public void enableWifiModeiOS() {
    	if (wifiBtn().waitForVisible().hasAttribute("value", "false") || wifiBtn().waitForVisible().hasAttribute("value", "0")) {
    		wifiBtn().waitForVisible().tap();
    	}
    }
}
