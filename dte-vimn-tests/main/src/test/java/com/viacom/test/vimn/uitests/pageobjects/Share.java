package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;

public class Share {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public Share() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact shareBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact shareTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact gmailBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact emailBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact cancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact moreBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact doneBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact ShareActivityListScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact TwitterBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact FacebookBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact iCloudBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact MessageBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact AddtoNotesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact copyBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact shareSeriesTitle(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }
    
    public Interact upgradeNotesAlert() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact alertCancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact goToNotesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact returnToParamountBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    //PAGE METHODS

    //ANDROID ONLY
    public void verifyCanShareViaEmail() {
        if (TestRun.isAndroid()) {
            if (!gmailBtn().waitForViewLoad().isVisible()) {
                emailBtn().goBack().waitForNotPresent();
            } else {
                gmailBtn().goBack().waitForNotPresent();
            }
        }
    }

}
