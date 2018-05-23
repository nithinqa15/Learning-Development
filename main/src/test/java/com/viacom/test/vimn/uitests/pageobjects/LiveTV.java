package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class LiveTV {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public LiveTV() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact liveTVCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact onNowTtl() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact onNowSeriesTtl(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }
    
    public Interact onNowEpisodeTtl(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }
    
    public Interact onNowAirTimeTxt(String time) {
        return new Interact(locator.getLocator(time), locator.locatorData);
    }
    
    public Interact onNowEpisodeDescriptionTxt(String description) {
        return new Interact(locator.getLocator(description), locator.locatorData);
    }

    public Interact onNowArrowBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
}
