package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Bala {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public Bala() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact acceptAndContinueBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact reviewUpdatesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact updatedTermsTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact privacyPolicyTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact termsOfUseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact termsOfUseTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact changesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact changesTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact faqsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact faqsTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact mustAcceptConditionsTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact retryBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact adChoicesTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    //iOS Only
    public void waitUntilMainConfigRefreshTime(int configRefreshTime) {
      	for (int i=0; i< configRefreshTime; i++) {
    			new Interact(null, null).pause(StaticProps.LARGE_PAUSE);
    	}
    }
    
    //iOS Only
    public void waitUntilAppResetTime(int appRefreshTime) {
    	for (int i=0; i< appRefreshTime; i++) {
    		if (this.acceptAndContinueBtn().isVisible()) {
    			break ;
    		} else {
    			new Interact(null, null).pause(StaticProps.LARGE_PAUSE);
    		}
    	}
    }
}
