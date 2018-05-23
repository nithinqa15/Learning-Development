package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;

import org.testng.Assert;

public class CellularToggleChain {

    Settings settings;
    SettingsMenu settingsMenu;

    // ACTION CHAIN CONSTRUCTOR
    public CellularToggleChain() {
        settings = new Settings();
        settingsMenu = new SettingsMenu();
    }

    // ACTION CHAIN METHODS
    public void enableCellular() {
        settings.settingsBtn().waitForVisible().tap();
        String celullarTglStatus = settingsMenu.getCellVidPlaybackStatus();
        if (celullarTglStatus.equals("off") || celullarTglStatus.equals("false") || celullarTglStatus.equals("0")) {
            settingsMenu.cellularTgl().waitForVisible().tap();
            if (settingsMenu.getCellVidPlaybackStatus().equals(celullarTglStatus)) {
                Assert.fail();
            }
        }
        Logger.logMessage("Cellular toggle is ON");
        settingsMenu.backBtn().waitForVisible().tap();
    }

    public void disableCellular() {
        settings.settingsBtn().waitForVisible().tap();
        String celullarTglStatus = settingsMenu.getCellVidPlaybackStatus();
        if (celullarTglStatus.equals("on") || celullarTglStatus.equals("true") || celullarTglStatus.equals("1")) {
            settingsMenu.cellularTgl().waitForVisible().tap();
            if (settingsMenu.getCellVidPlaybackStatus().equals(celullarTglStatus)) {
                Assert.fail();
            }
        }
        Logger.logMessage("Cellular toggle is OFF");
        settingsMenu.backBtn().waitForVisible().tap();
    }



}