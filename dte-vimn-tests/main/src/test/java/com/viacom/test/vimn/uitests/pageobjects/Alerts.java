package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Alerts {

    private Locator locator;

    public Alerts()  {
        locator = new Locator();
    }

    public Interact allowBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact dontAllowBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
}
