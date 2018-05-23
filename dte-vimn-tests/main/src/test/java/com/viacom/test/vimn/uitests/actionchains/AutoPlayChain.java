package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.uitests.pageobjects.Settings;
import com.viacom.test.vimn.uitests.pageobjects.SettingsMenu;

import org.testng.Assert;

public class AutoPlayChain {

	Settings settings;
	SettingsMenu settingsMenu;
	
    // ACTION CHAIN CONSTRUCTOR
    public AutoPlayChain() {
        settings = new Settings();
        settingsMenu = new SettingsMenu();
    }

    // ACTION CHAIN METHODS
    public void enableAutoPlay() {
    	settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	if (cellularVideoPlaybackStatus.equals("off") || cellularVideoPlaybackStatus.equals("false") || cellularVideoPlaybackStatus .equals("0")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		String autoPlayStatus = settingsMenu.getAutoPlayStatus();
		if (autoPlayStatus.equals("off") || autoPlayStatus.equals("false") || autoPlayStatus.equals("0")) {
			settingsMenu.autoPlayTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayStatus().equals(autoPlayStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("Autoplay toggle is ON");
		settingsMenu.backBtn().waitForVisible().tap();
    }

	public void disableAutoPlay() {
		settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	if (cellularVideoPlaybackStatus.equals("off") || cellularVideoPlaybackStatus.equals("false") || cellularVideoPlaybackStatus.equals("0")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		String autoPlayStatus = settingsMenu.getAutoPlayStatus();
		if (autoPlayStatus.equals("on") || autoPlayStatus.equals("true") || autoPlayStatus.equals("1")) {
			settingsMenu.autoPlayTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayStatus().equals(autoPlayStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("Autoplay toggle is OFF");
		settingsMenu.backBtn().waitForVisible().tap();
	}

	public void enableAutoPlayClip() {
		settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	if (cellularVideoPlaybackStatus.equals("off") || cellularVideoPlaybackStatus.equals("false") || cellularVideoPlaybackStatus.equals("0")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		String autoPlayClipStatus = settingsMenu.getAutoPlayClipsStatus();
		if (autoPlayClipStatus.equals("off") || autoPlayClipStatus.equals("false") || autoPlayClipStatus.equals("0")) {
			settingsMenu.autoPlayClipsTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayClipsStatus().equals(autoPlayClipStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("Autoplay xtras toggle is ON");
		settingsMenu.backBtn().waitForVisible().tap();
	}

	public void disableAutoPlayClip() {
		settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	if (cellularVideoPlaybackStatus.equals("off") || cellularVideoPlaybackStatus.equals("false") || cellularVideoPlaybackStatus.equals("0")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		String autoPlayClipStatus = settingsMenu.getAutoPlayClipsStatus();
		if (autoPlayClipStatus.equals("on") || autoPlayClipStatus.equals("true") || autoPlayClipStatus.equals("1")) {
			settingsMenu.autoPlayClipsTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayClipsStatus().equals(autoPlayClipStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("Autoplay xtras toggle is OFF");
		settingsMenu.backBtn().waitForVisible().tap();
	}
	
    public void disableAllVideoPlaybackToggle() {
    	settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	String autoPlayStatus = settingsMenu.getAutoPlayStatus();
    	String autoPlayClipsStatus = settingsMenu.getAutoPlayClipsStatus();
		if (autoPlayClipsStatus.equals("on") || autoPlayClipsStatus.equals("true") || autoPlayClipsStatus.equals("1")) {
			settingsMenu.autoPlayClipsTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayClipsStatus().equals(autoPlayClipsStatus)) {
				Assert.fail();
			}
		}
		if (autoPlayStatus.equals("on") || autoPlayStatus.equals("true") || autoPlayStatus.equals("1")) {
			settingsMenu.autoPlayTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayStatus().equals(autoPlayStatus)) {
				Assert.fail();
			}
		}
		if (cellularVideoPlaybackStatus.equals("on") || cellularVideoPlaybackStatus.equals("true") || cellularVideoPlaybackStatus.equals("1")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("All Video Playback toggle - OFF");
		settingsMenu.backBtn().waitForVisible().tap();
    }
    
    public void enableAllVideoPlaybackToggle() {
    	settings.settingsBtn().waitForVisible().tap();
    	String cellularVideoPlaybackStatus = settingsMenu.getCellVidPlaybackStatus();
    	String autoPlayStatus = settingsMenu.getAutoPlayStatus();
    	String autoPlayClipsStatus = settingsMenu.getAutoPlayClipsStatus();
    	if (cellularVideoPlaybackStatus.equals("off") || cellularVideoPlaybackStatus.equals("false") || cellularVideoPlaybackStatus.equals("0")) {
			settingsMenu.cellularTgl().waitForVisible().tap();
			if (settingsMenu.getCellVidPlaybackStatus().equals(cellularVideoPlaybackStatus)) {
				Assert.fail();
			}
		}
		if (autoPlayStatus.equals("off") || autoPlayStatus.equals("false") || autoPlayStatus.equals("0")) {
			settingsMenu.autoPlayTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayStatus().equals(autoPlayStatus)) {
				Assert.fail();
			}
		}
		if (autoPlayClipsStatus.equals("off") || autoPlayClipsStatus.equals("false") || autoPlayClipsStatus.equals("0")) {
			settingsMenu.autoPlayClipsTgl().waitForVisible().tap();
			if (settingsMenu.getAutoPlayClipsStatus().equals(autoPlayClipsStatus)) {
				Assert.fail();
			}
		}
		Logger.logMessage("All Video Playback toggle - ON");
		settingsMenu.backBtn().waitForVisible().tap();
    }
}