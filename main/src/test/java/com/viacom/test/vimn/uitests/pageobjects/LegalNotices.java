package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class LegalNotices {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public LegalNotices() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact legalNoticesTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact legalNoticesBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact closedCaptionLnk() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact endUserAgreementLnk() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact legalNoticesBrTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact legalNoticesBrBdy() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
}
