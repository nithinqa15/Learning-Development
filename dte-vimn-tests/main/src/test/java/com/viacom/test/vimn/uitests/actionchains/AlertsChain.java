package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.pageobjects.Alerts;

public class AlertsChain {

    Alerts alerts;

    // ACTION CHAIN CONSTRUCTOR
    public AlertsChain() {
        alerts = new Alerts();
    }

    // ACTION CHAIN METHODS
    private void tapOnAllowBtn() {
    	
    	Logger.logMessage("Dismissing Alerts view");
    	if (alerts.allowBtn().isVisible(25)) {
    		alerts.allowBtn().waitForVisible().tap();
            Logger.logMessage("Notifications Allowed");
    	}
    }
    
	private void tapOnDontAllowBtn() {
    	
    	Logger.logMessage("Dismissing Alerts view");
    	if (alerts.dontAllowBtn().isVisible()) {
    		alerts.dontAllowBtn().waitForVisible().tap();
            Logger.logMessage("Notifications or Apple SSO Don't Allowed");
    	}
    }

    public void dismissAlerts() {
        if (TestRun.isVH1App()) {
            if (TestRun.isAndroid()) {
            	//TO Do - Need to check Android behavior once Push notification integrate 
            } else {
            	tapOnAllowBtn();
            }
        } else if (!TestRun.isAndroid()) {
        	tapOnDontAllowBtn();
        }
    }
}
