package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Settings {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public Settings() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact moreOptionsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact settingsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact devSettingsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact toolBar() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

}
