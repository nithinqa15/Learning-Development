package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class DontSeeProvider {
	
	 private Locator locator;
	   
	    public DontSeeProvider() {
	        locator = new Locator();
	    }
	    
	    public Interact freePreviewBtn() {
	        return new Interact(locator.getLocator(), locator.locatorData);
	    }
}
