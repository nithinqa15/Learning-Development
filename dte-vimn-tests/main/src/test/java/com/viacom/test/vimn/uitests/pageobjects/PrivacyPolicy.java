package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class PrivacyPolicy {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public PrivacyPolicy() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact privacyPolicyBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact privacyPolicyLnk() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact adChoicesLnk() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyBrTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyBrBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
}
