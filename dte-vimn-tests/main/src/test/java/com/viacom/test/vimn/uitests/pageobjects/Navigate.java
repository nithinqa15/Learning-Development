package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Navigate {

    private Locator locator;
    
    //PAGE OBJECT CONSTRUCTOR
    public Navigate() {
        locator = new Locator();
    }

    //PAGE OBJECT IDENTIFIERS
    public Interact backBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact closeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact liveTVCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

}
