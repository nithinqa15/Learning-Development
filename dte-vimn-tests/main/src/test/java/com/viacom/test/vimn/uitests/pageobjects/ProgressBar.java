package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.Interact;

public class ProgressBar {

    private Locator locator;

    //PAGE OBJECT CONSTRUCTOR
    public ProgressBar() {
        locator = new Locator();
    }

    //PAGE OBJECT IDENTIFIERS

    public Interact videoProgressBarIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact loadingSpinnerIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact loadingSpinnerPlaylistIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact loadingSpinnerClipsPlaylistIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact loadingSpinnerPosterIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

}
