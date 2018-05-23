package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.exceptions.FailedTVELoginException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;
import org.testng.SkipException;

public class SignIn {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public SignIn() {
        locator = new Locator();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact attUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact attPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact attSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoDeviceLimitTxt()   {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact blueToGoCloseAllSessionsBtn()    {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact boxerUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact boxerPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact boxerSubmitBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact brighthouseUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact brighthousePasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact brighthouseSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact claroUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact claroPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact claroSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact coxUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact coxPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact coxSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact directTVUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact directTVPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact directTVSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishForgotIdLink() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishForgotPasswordLink() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishForgotIdScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact dishForgotPasswordScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact frontierUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact frontierPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact frontierSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact getUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact getPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact getSubmitBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact mediacomUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact mediacomPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact mediacomSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacablePasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableFailedLoginTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableDeviceLimitTxt()   {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact megacableCloseAllSessionsBtn()    {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumFailedLoginTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumForgotIdLink()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumForgotPasswordLink() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumForgotIdScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact optimumForgotPasswordScreen() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rcnUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rcnPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rcnSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rikstvUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rikstvPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact rikstvSubmitBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact serviceElectricUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact serviceElectricPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact serviceElectricSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact singtelUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact singtelPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact singtelSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact skyUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact skyPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact skySignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact stofaUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact stofaPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact stofaSubmitBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact telecentroUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact telecentroPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact telecentroSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact timeWarnerCableUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact timeWarnerCablePasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact timeWarnerCableSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact verizonUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact verizonPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact verizonSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact wowUsernameTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact wowPasswordTxb() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact wowSignInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact subscriptionRequiredText()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact successTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact closeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact backBtn() { return new Interact(locator.getLocator(), locator.locatorData); }
    
    public Interact successCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signInText() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact pleaseWaitText()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signingInText()  {
        return new Interact(locator.getLocator(), locator.locatorData);
    }


    // PAGE INTERACTION METHODS
    public void goBackToProviders() {
        if (signInText().waitForSignInPage().isPresent()) {
            backBtn().waitForVisible().tap();
        }
    }

    public void waitForSigningInText() {
        if (signingInText().isVisible()) {
            signingInText().waitForNotPresentOrVisible();
        }
    }

    public void megacableClearAllSessions() {
        if (megacableDeviceLimitTxt().pause(10000).isVisible()) {
            if (TestRun.isAndroid()) {
                Integer startX = DriverManager.getAppiumDriver().manage().window().getSize().getWidth() / 2;
                Integer startY = DriverManager.getAppiumDriver().manage().window().getSize().getHeight() / 2;
                Integer endX = startX;
                Integer endY = startY / 5;
                megacableCloseAllSessionsBtn()
                        .scroll(startX, startY, endX, endY, Config.StaticProps.NORMAL_SCROLL)
                        .waitForVisible()
                        .tapCenter();
            } else {
                megacableCloseAllSessionsBtn().waitForVisible().tap();
            }
        }
    }

    public void megacableFailedLoginThrowException() {  // vb - 4.7.18 - seeing random failures from Megacable with valid credentials
        if (megacableFailedLoginTxt().pause(10000).isVisible()) {
            throw new FailedTVELoginException("There was an error trying to login to Megacable! Skipping test.");
        }
    }
}
