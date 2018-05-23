package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Keyboard {

    private Locator locator;

    //PAGE OBJECT CONSTRUCTOR
    public Keyboard() {
        locator = new Locator();
    }

    //PAGE OBJECT IDENTIFIERS
    public Interact closeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact deleteBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact returnBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact previousBtn()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact nextBtn()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact doneBtn()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact GoBtn()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    // iOS Only
    public void goToPasswordField() {
        nextBtn().waitForVisible().tap();
    }

}