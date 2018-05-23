package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.DevSettingsMenu;

public class DevSettingsChain {

    private Settings settings;
    private DevSettingsMenu devSettingsMenu;

    //ACTION CHAIN CONSTRUCTOR
    public DevSettingsChain() {
        settings = new Settings();
        devSettingsMenu = new DevSettingsMenu();
    }

    //ACTION CHAIN METHODS

    public void launchBala() {

        this.enterDevSettings();
        devSettingsMenu.clearBalaBtn().waitForScrolledTo().waitForVisible().tap();
        devSettingsMenu.clearBalaPopUp().waitForVisible();
        devSettingsMenu.yesBtn().waitForVisible().tap();

    }

    private void enterDevSettings() {
        int x = settings.toolBar().waitForVisible().getMobileElement().getLocation().x;
        int y = settings.toolBar().waitForVisible().getMobileElement().getLocation().y;

        settings.toolBar().longPress(x, y, 3000);
    }
}
