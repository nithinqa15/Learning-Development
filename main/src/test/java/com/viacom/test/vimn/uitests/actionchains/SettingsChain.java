package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;

public class SettingsChain {

    private Settings settings;
    private SettingsMenu settingsMenu;

    public SettingsChain() {
        settings = new Settings();
        settingsMenu = new SettingsMenu();
    }

    public void toSettingsAndBack() {
        settings.settingsBtn().waitForVisible().tap();
        if (settingsMenu.autoPlayTgl().waitForViewLoad().isVisible()) {
            if (TestRun.isAndroid()) {
                settingsMenu.backBtn().waitForVisible().tap();
            } else {
                settingsMenu.backBtn().waitForVisible().tap();
            }
        }
    }

    public String getVersion() {
        String version;
        if (TestRun.isIos()) {
            if (!settingsMenu.buildVersion().isVisible()) {
                settingsMenu.buildVersion().waitForScrolledDownTo(5, 300);
            }
            version = settingsMenu.buildVersion().waitForVisible().getIOSElement().getText();
        } else {
            settings.settingsBtn().waitForVisible().tap().waitForNotPresentOrVisible();
            version = settingsMenu.buildVersion().waitForVisible().getMobileElement().getAttribute(Config.AttributeProps.TEXT);
            settingsMenu.backBtn().waitForVisible().tap().waitForNotPresentOrVisible();
        }
        return version;
    }
}
