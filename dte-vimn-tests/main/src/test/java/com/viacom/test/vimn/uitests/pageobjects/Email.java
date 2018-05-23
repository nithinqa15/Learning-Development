package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Email {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public Email() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact toTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact subjectTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact cancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact deleteDraftBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact CouldNotSendBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
}
