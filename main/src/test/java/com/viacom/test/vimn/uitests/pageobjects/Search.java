package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Search {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public Search() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact searchFieldBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backSearchScreenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact clearTextBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact cancelTextBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact allSearchResultsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact liveTVBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
}
