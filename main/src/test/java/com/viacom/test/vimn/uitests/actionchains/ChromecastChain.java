package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.pageobjects.Home;

public class ChromecastChain {

    Home home;

    // ACTION CHAIN CONSTRUCTOR
    public ChromecastChain() {
        home = new Home();
    }

    // ACTION CHAIN METHODS
    private void tapOnChromecastIcon() {
        if (home.chromecastTxt().isVisible()) {
            Logger.logMessage("Dismissing Chromecast coachmark screen");
            if (TestRun.isIos()) {
            	home.chromecastOKbtn().waitForVisible().tap();
            } else {
            	home.chromecastBtn().waitForVisible().tap();
                home.chromecastTxt().waitForNotPresentOrVisible();
            }
            Logger.logMessage("Chromecast Dismissed");
        }
    }

    private void selectDeviceToCastTo() {
        if (home.castToTxt().isVisible(5)) {
            Logger.logMessage("Selecting device to cast");
            home.castToDeviceBtn().waitForVisible().tap().waitForNotPresentOrVisible();
            Logger.logMessage("Device successfully selected");
        }
    }

    public void dismissChromecast() {
        if (TestRun.isVH1App() || TestRun.isParamountApp()) {
            tapOnChromecastIcon();
            if (TestRun.isAndroid()) {
            	selectDeviceToCastTo();
            }
        }
    }
}
