package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

@Deprecated
public class XFinity {

    private Locator locator;
    
    // PAGE OBJECT CONSTRUCTOR
    public XFinity() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact facebookBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact twitterBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact signInRegisterBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact facebookWeb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact okAlertBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact emptyEmailMsg() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact emptyPasswordMsg() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact twitterCancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact signInCancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact createNowCancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact signInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact createOneNowBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact emailTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signUpEmailTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact passwordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signUpBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dateOfBirthTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact invalidEmailPasswordTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact okayBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinitySignUpPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityDisplayNameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityMaleBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityBirthdayTitleBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityBirthdayPickerYearBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityBirthdayFirstYearBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityBirthdayCancelBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityBirthdayOKBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityZipcodeTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinityDoneBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact xFinitySignInSuccessTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact twitterOAuthScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
}
