package com.viacom.test.vimn.common.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;

import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;

import io.appium.java_client.MobileBy;

public class Locator {

    public HashMap<String, String> locatorData;
    private String elementClassName;
    private String elementName;

    public Locator() {
        locatorData = new HashMap<>();
    }

    private JSONObject getElementJSON() {
        String elementFileName = ConfigProps.ELEMENT_FILE_PATH + elementClassName + ".json";

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(elementFileName));
        } catch (FileNotFoundException e) {
            Assert.fail("No Element File found.", e);
        } catch (IOException e) {
            Assert.fail("Failed to query Class Element File '" + elementClassName + "' "
                    + "with element '" + elementName + "'.", e);
        } catch (ParseException e) {
            Assert.fail("Failed to query Class Element File '" + elementClassName + "' "
                    + "with element '" + elementName + "'.", e);
        }
        return (JSONObject) jsonObject.get(elementName);
    }

    private By parseLocator(String inText, String inText2) {
        String locatorString = "";
        String simpleName = "";
        By locator = null;
        try {
            JSONObject elementObject = getElementJSON();
            simpleName = (String) elementObject.get("SimpleName");
            JSONObject osObject = (JSONObject) elementObject.get(TestRun.getMobileOS().value());
            JSONObject appObject;

            if (osObject.containsKey(ConfigProps.APP_NAME)) {
                appObject = (JSONObject) osObject.get(ConfigProps.APP_NAME);
            } else {
                appObject = (JSONObject) osObject.get(StaticProps.ALL_APPS);
            }
            if (appObject.containsKey(StaticProps.ALL_DEVICES)) {
                locatorString = (String) appObject.get(StaticProps.ALL_DEVICES);
            }  else if (appObject.containsKey(StaticProps.PHONE) && TestRun.isPhone()) {
                locatorString = (String) appObject.get(StaticProps.PHONE);
            } else if (appObject.containsKey(StaticProps.TABLET) && TestRun.isTablet()) {
                locatorString = (String) appObject.get(StaticProps.TABLET);
            } else if (appObject.containsKey(StaticProps.ALL_DEVICES_IOS10) && TestRun.isiOS10()) {
                locatorString = (String) appObject.get(StaticProps.ALL_DEVICES_IOS10);
            } else if (appObject.containsKey(StaticProps.ALL_DEVICES_IOS11) && !TestRun.isiOS10()) {
                locatorString = (String) appObject.get(StaticProps.ALL_DEVICES_IOS11);
            }
            if (appObject.containsKey(StaticProps.ALL_DEVICES_ANDROID_7) && TestRun.isAndroid7()) {
                locatorString = (String) appObject.get(StaticProps.ALL_DEVICES_ANDROID_7);
            }
        } catch (Exception e) {
            Assert.fail("Error on JSON query.", e);
        }

        String locatorArr[] = locatorString.split("::");
        String locatorType = locatorArr[0];
        String locatorDef = locatorArr[1];

        if (inText2 != null) {
            if (locatorDef.contains(StaticProps.ANCILLARY_LOCATOR_VARIABLE_UPPER)) {
                locatorDef = locatorDef.replace(StaticProps.ANCILLARY_LOCATOR_VARIABLE_UPPER, inText2.toUpperCase());
            } else if (locatorDef.contains(StaticProps.ANCILLARY_LOCATOR_VARIABLE_LOWER)) {
                locatorDef = locatorDef.replace(StaticProps.ANCILLARY_LOCATOR_VARIABLE_LOWER, inText2.toLowerCase());
            } else {
                locatorDef = locatorDef.replace(StaticProps.ANCILLARY_LOCATOR_VARIABLE, inText2);
            }
        }

        if (inText != null) {
            if (locatorDef.contains(StaticProps.LOCATOR_VARIABLE_UPPER)) {
                locatorDef = locatorDef.replace(StaticProps.LOCATOR_VARIABLE_UPPER, inText.toUpperCase());
            } else if (locatorDef.contains(StaticProps.LOCATOR_VARIABLE_LOWER)) {
                locatorDef = locatorDef.replace(StaticProps.LOCATOR_VARIABLE_LOWER, inText.toLowerCase());
            } else {
                locatorDef = locatorDef.replace(StaticProps.LOCATOR_VARIABLE, inText);
            }

            locatorData.put("SimpleName", simpleName.replace(StaticProps.LOCATOR_VARIABLE, inText));
        } else {
            locatorData.put("SimpleName", simpleName);
        }

        if (locatorDef.contains(StaticProps.LOCATOR_VARIABLE_PACKAGE)) {
            locatorDef = locatorDef.replace(StaticProps.LOCATOR_VARIABLE_PACKAGE, getAppPackage());
        }

        if (locatorType.equalsIgnoreCase(StaticProps.LOCATOR_ANDROID_UIAUTOMATOR)) {
            locator = MobileBy.AndroidUIAutomator(locatorDef);
        } else if (locatorType.equalsIgnoreCase(StaticProps.LOCATOR_IOS_UIAUTOMATION)) {
            locator = MobileBy.IosUIAutomation(locatorDef);
        } else if (locatorType.equalsIgnoreCase(StaticProps.LOCATOR_XPATH)) {
            locator = MobileBy.xpath(locatorDef);
        } else if (locatorType.equalsIgnoreCase(StaticProps.LOCATOR_ACCESSIBILITY_ID)) {
            locator = MobileBy.AccessibilityId(locatorDef);
        } else if (locatorType.equalsIgnoreCase(StaticProps.LOCATOR_IOS_NS_PREDICATE)) {
            locator = MobileBy.iOSNsPredicateString(locatorDef);
        }

        if (locator == null) {
            Assert.fail("No matching locator found for Class Element '" + elementClassName + "' "
                    + "with element '" + elementName + "'.");
        }
        return locator;
    }

    public By getLocator() {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        int startingIndex = className.lastIndexOf(".");
        elementClassName = className.substring(startingIndex, className.length()).replace(".", "");
        elementName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return parseLocator(null, null);
    }

    public By getLocator(String inText) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        int startingIndex = className.lastIndexOf(".");
        elementClassName = className.substring(startingIndex, className.length()).replace(".", "");
        elementName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return parseLocator(inText, null);
    }

    public By getLocator(String inText, String inText2) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        int startingIndex = className.lastIndexOf(".");
        elementClassName = className.substring(startingIndex, className.length()).replace(".", "");
        elementName = Thread.currentThread().getStackTrace()[2].getMethodName();
        return parseLocator(inText, inText2);
    }

    public String getAppPackage() {
        String appPackage = "";
        if (TestRun.isMTVDomesticApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.MTV_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.MTV_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isMTVINTLApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.MTV_INTL_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.MTV_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isCCDomesticApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.CC_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.CC_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isCCINTLApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.CC_INTL_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.CC_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isBETDomesticApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.BET_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.BET_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isBETINTLApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.BET_INTL_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.BET_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isTVLandApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.TVLAND_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.TVLAND_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isCMTApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.CMT_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.CMT_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else if (TestRun.isParamountApp()) {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.PARAMOUNT_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.PARAMOUNT_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        } else {
            if (TestRun.isQABuild()) {
                appPackage = ConfigProps.VH1_ANDROID_ALPHA_QA_APP_PACKAGE;
            } else {
                appPackage = ConfigProps.VH1_ANDROID_BETA_RELEASE_APP_PACKAGE;
            }
        }
        return appPackage;
    }

    public String getAppBundle() {
        String appBundle = "";
        if (TestRun.isTVLandApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = Config.ConfigProps.TVLAND_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.TVLAND_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isCCDomesticApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = Config.ConfigProps.CC_DOMESTIC_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.CC_DOMESTIC_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isCCINTLApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = Config.ConfigProps.CC_INTL_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.CC_INTL_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isMTVDomesticApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.MTV_DOMESTIC_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = ConfigProps.MTV_DOMESTIC_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isMTVINTLApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.MTV_INTL_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = ConfigProps.MTV_INTL_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isBETDomesticApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.BET_DOMESTIC_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = ConfigProps.BET_DOMESTIC_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isBETINTLApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.BET_INTL_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = ConfigProps.BET_INTL_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isVH1App()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.VH1_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.VH1_IOS_BUNDLE_ID_DEV;
            }
        } else if (TestRun.isCMTApp()) {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.CMT_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.CMT_IOS_BUNDLE_ID_DEV;
            }
        } else {
            if (TestRun.isReleaseBuild()) {
                appBundle = ConfigProps.PARAMOUNT_IOS_BUNDLE_ID_RELEASE;
            } else {
                appBundle = Config.ConfigProps.PARAMOUNT_IOS_BUNDLE_ID_DEV;
            }
        }
        return appBundle;
    }

}
