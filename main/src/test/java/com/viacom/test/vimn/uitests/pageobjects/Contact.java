package com.viacom.test.vimn.uitests.pageobjects;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.BrandDataFactory;

public class Contact {

    @SuppressWarnings("unused")
	private Locator locator;

    String supportEmail;
    String subjectTxt;
    String bodyTxt;

    // PAGE OBJECT CONSTRUCTOR
    public Contact()  {
        locator = new Locator();
    }

    public String getSupportEmail() {
        supportEmail = BrandDataFactory.getAppContactSupportToEmail();
        return supportEmail;
    }

    public String getSubjectTxt() {
        String brandName = TestRun.getAppName();
        String country = LocaleDataFactory.getCountryName();
        if (Config.ConfigProps.RUN_AS_FACTORY) {
            subjectTxt = brandName + " " + country + " Support";
        } else {
            String appVersion = new SettingsMenu().getAppVersion();
            subjectTxt = brandName + " " + country + " Support " + appVersion;
        }
        return subjectTxt;
    }

    public String getBodyTxt() {
        String brandName = TestRun.getAppName();
        bodyTxt = "At $brandName we love getting email, especially from people using our apps.".replace("$brandName", brandName);
        return bodyTxt;
    }
}
