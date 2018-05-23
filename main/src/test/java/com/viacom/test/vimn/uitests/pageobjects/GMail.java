package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class GMail {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public GMail() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact toTxb() { return new Interact(locator.getLocator(), locator.locatorData); }
    
    public Interact subjectTxb(String subjectTxt) {
        return new Interact(locator.getLocator(subjectTxt), locator.locatorData);
    }
    
    public Interact bodyTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact gMailIcon() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact gMailTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

}
