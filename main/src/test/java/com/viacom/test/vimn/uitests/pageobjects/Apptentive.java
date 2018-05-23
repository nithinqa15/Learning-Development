package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Apptentive {

    private Locator locator;

    public Apptentive()  {
        locator = new Locator();
    }

    public Interact profileBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact messageCenterHeader() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact apptentiveCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact attachBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact sendBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact newMessageBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact newMessageTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact detailedFeedbackBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact discardBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact composeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact respondMessageBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact skipBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact whoAreWeDiag() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
}
