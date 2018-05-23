package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import io.appium.java_client.MobileElement;


public class Ads {

    private Locator locator;

    //PAGE OBJECT CONTSTRUCTOR
    public Ads() {
        locator = new Locator();
    }

    //PAGE OBJECT IDENTIFIERS
    public Interact playAdsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact pauseAdsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact learnMoreBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact durationTimer() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blackAdOverlay() { return new Interact(locator.getLocator(),locator.locatorData);}

    public Interact fwControlsBar() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    private Integer getTimeRemaining() {
        if (durationTimer().isVisible()) {
            MobileElement durationElement = durationTimer().waitForPresent().getMobileElement();
            String duration = durationElement.getText();
            Logger.logMessage(duration);
            Integer intDuration = Integer.parseInt(duration.replaceAll("\\D+",""));
            return intDuration;
        } else {
            return null;
        }
    }

    public void waitForAd(Integer adSlot) {
        Integer adDuration = ProxyUtils.getSpecificAdDuration(adSlot);
        if (learnMoreBtn().waitForPlayerLoad().isVisible()) {
            try {
                Logger.logMessage("Waiting for " + adDuration + " second ad to complete");
                Thread.sleep(adDuration * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.logMessage("There is no ad playing.");
        }
    }
}
