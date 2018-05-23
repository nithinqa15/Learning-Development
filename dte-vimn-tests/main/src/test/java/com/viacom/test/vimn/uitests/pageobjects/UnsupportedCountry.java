package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class UnsupportedCountry {

    private Locator locator;

    public UnsupportedCountry()  {
        locator = new Locator();
    }

    public Interact notAvailableInThisCountryTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public void waitForNotAvailableInThisCountryTxtVisible() {
        this.notAvailableInThisCountryTxt().waitForPresent();
    }

}
