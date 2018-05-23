package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class TermsAndConditions {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public TermsAndConditions() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact termsOfUseTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact termsOfUseBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact termsOfUseLnk() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact termsOfUseBrTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact termsOfUseBrBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

}
