package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;
import org.testng.SkipException;

public class SelectProvider {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public SelectProvider() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact viewAllProvidersBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact closeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact fromFranchiseBackBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact attProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact brighthouseProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact claroProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact claroProviderBtn(String geoCode) {
        return new Interact(locator.getLocator(geoCode), locator.locatorData);
    }

    public Interact coxProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact directTVProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact frontierProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact mediacomProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact optimumProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rcnProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact serviceElectricProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact singtelProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact skyProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact telecentroProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact timeWarnerCableProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact verizonProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact wowProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact xfinityProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dontSeeProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact providerNotListedTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact typeProviderNameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact minorProviderBtn(String provider) {
        return new Interact(locator.getLocator(provider), locator.locatorData);
    }
    
    public Interact riksProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact getProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }  
    
    public Interact boxerProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }  

    public Interact stofaProviderBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact firstProviderOption() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    //PAGE METHODS

    public void tapDontSeeProviderBtnLandsOnProviderNotListedAndBack() {
        dontSeeProviderBtn().waitForVisible().tap();
        providerNotListedTxt().waitForVisible().goBack();
        dontSeeProviderBtn().waitForVisible();
    }

    public void fromMVPDtoProviderNotListedAndBack() {
        if (TestRun.isMTVINTLApp() || TestRun.isCCINTLApp()) {
            tapDontSeeProviderBtnLandsOnProviderNotListedAndBack();
        } else if ( TestRun.isCMTApp() || TestRun.isTVLandApp() 
	        		|| TestRun.isBETDomesticApp() || TestRun.isParamountApp()
        		    || TestRun.isMTVDomesticApp() || TestRun.isCCDomesticApp()
        		    || TestRun.isVH1App()) {
            viewAllProvidersBtn().waitForVisible().tap();
            tapDontSeeProviderBtnLandsOnProviderNotListedAndBack();
            backBtn().waitForVisible().tap();
            viewAllProvidersBtn().waitForVisible();
        } else {
            throw new SkipException("Skipping test: TVE is not supported on " + TestRun.getAppName());
        }
    }

}
