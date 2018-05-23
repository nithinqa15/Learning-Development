package com.viacom.test.vimn.common.util;

import com.viacom.test.core.lab.TestDeviceInfo;
import com.viacom.test.core.props.DeviceCategory;
import com.viacom.test.core.props.MobileOS;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestRun {

    private static ThreadLocal<MobileOS> mobileOS = new ThreadLocal<MobileOS>();
    private static ThreadLocal<String> appName = new ThreadLocal<String>();
    private static ThreadLocal<String> locale = new ThreadLocal<String>();
    private static ThreadLocal<DeviceCategory> deviceCategory = new ThreadLocal<DeviceCategory>();
    private static ThreadLocal<String> appPackageID = new ThreadLocal<String>();
    private static ThreadLocal<String> androidLaunchActivity = new ThreadLocal<String>();
    private static ThreadLocal<String> appPackagePath = new ThreadLocal<String>();
    private static ThreadLocal<String> appVersion = new ThreadLocal<String>();
    private static ThreadLocal<String> contentDomain = new ThreadLocal<String>();

    public static synchronized void init(String runParams) {
        // set the os
        setMobileOS(MobileOS.valueOf(ConfigProps.MOBILE_OS.toUpperCase()));

        // set the app
        setAppName(ConfigProps.APP_NAME);

        // set the locale
        setLocale(ConfigProps.LOCALE);

        // set the device type
        if (ConfigProps.RUN_AS_FACTORY) {
            if (runParams.contains(DeviceCategory.PHONE.value())) {
                setDeviceCategory(DeviceCategory.PHONE);
            } else {
                setDeviceCategory(DeviceCategory.TABLET);
            }
        } else {
            setDeviceCategory(DeviceCategory.valueOf(ConfigProps.DEVICE_CATEGORY.toUpperCase()));
        }

        // set the app package
        if (isAndroid()) {
            if (isMTVDomesticApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.MTV_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.MTV_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.MTV_DOMESTIC_ANDROID_LAUNCH_ACTIVITY);
            } if (isMTVINTLApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.MTV_INTL_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.MTV_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.MTV_INTL_ANDROID_LAUNCH_ACTIVITY);
            } else if (isCCDomesticApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.CC_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.CC_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.CC_DOMESTIC_ANDROID_LAUNCH_ACTIVITY);
            } else if (isCCINTLApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.CC_INTL_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.CC_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.CC_INTL_ANDROID_LAUNCH_ACTIVITY);
            } else if (isTVLandApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.TVLAND_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.TVLAND_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.TVLAND_ANDROID_LAUNCH_ACTIVITY);
            } else if (isBETDomesticApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.BET_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.BET_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.BET_DOMESTIC_ANDROID_LAUNCH_ACTIVITY);
            } else if (isBETINTLApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.BET_INTL_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.BET_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.BET_INTL_ANDROID_LAUNCH_ACTIVITY);
            } else if (isCMTApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.CMT_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.CMT_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.CMT_ANDROID_LAUNCH_ACTIVITY);
            } else if (isVH1App()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.VH1_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.VH1_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.VH1_ANDROID_LAUNCH_ACTIVITY);
            } else if (isParamountApp()) {
                if (TestRun.isQABuild()) {
                    setAppPackageID(ConfigProps.PARAMOUNT_ANDROID_ALPHA_QA_APP_PACKAGE);
                } else {
                    setAppPackageID(ConfigProps.PARAMOUNT_ANDROID_BETA_RELEASE_APP_PACKAGE);
                }
                setAndroidLaunchActivity(ConfigProps.PARAMOUNT_ANDROID_LAUNCH_ACTIVITY);
            }
        } else {
            if (isMTVDomesticApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.MTV_DOMESTIC_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.MTV_DOMESTIC_IOS_BUNDLE_ID_RELEASE);
                }
            } if (isMTVINTLApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.MTV_INTL_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.MTV_INTL_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isCCDomesticApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.CC_DOMESTIC_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.CC_DOMESTIC_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isCCINTLApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.CC_INTL_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.CC_INTL_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isTVLandApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.TVLAND_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.TVLAND_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isBETDomesticApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.BET_DOMESTIC_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.BET_DOMESTIC_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isBETINTLApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.BET_INTL_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.BET_INTL_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isCMTApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.CMT_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.CMT_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isVH1App()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.VH1_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.VH1_IOS_BUNDLE_ID_RELEASE);
                }
            } else if (isParamountApp()) {
                if (isDevBuild()) {
                    setAppPackageID(ConfigProps.PARAMOUNT_IOS_BUNDLE_ID_DEV);
                } else {
                    setAppPackageID(ConfigProps.PARAMOUNT_IOS_BUNDLE_ID_RELEASE);
                }
            }
        }

        // set the app version
        if (isIos()) {
            setAppVersion(ConfigProps.IOS_APP_VERSION);
        } else {
            setAppVersion(ConfigProps.ANDROID_APP_VERSION);
        }
        
        // set the Content Domain
        setContentDomain(ConfigProps.CONTENT_DOMAIN);
    }

    public static synchronized void setMobileOS(MobileOS os) {
        mobileOS.set(os);
    }

    public static synchronized MobileOS getMobileOS() {
        return mobileOS.get();
    }

    public static synchronized void setAppName(String app) {
        appName.set(app);
    }

    public static synchronized String getAppName() {
        return appName.get();
    }

    public static synchronized void setLocale(String localeID) {
        locale.set(localeID);
    }

    public static synchronized String getLocale() {
        return locale.get();
    }

    public static synchronized void setDeviceCategory(DeviceCategory device) {
        deviceCategory.set(device);
    }

    public static synchronized DeviceCategory getDeviceCategory() {
        return deviceCategory.get();
    }

    public static synchronized void setAppPackageID(String id) {
        appPackageID.set(id);
    }

    public static synchronized String getAppPackageID() {
        return appPackageID.get();
    }

    public static synchronized void setAndroidLaunchActivity(String activity) {
        androidLaunchActivity.set(activity);
    }

    public static synchronized String getAndroidLaunchActivity() {
        return androidLaunchActivity.get();
    }

    public static synchronized void setAppPackagePath(String path) {
        appPackagePath.set(path);
    }

    public static synchronized String getAppPackagePath() {
        return appPackagePath.get();
    }

    public static synchronized Boolean isIos() {
        return getMobileOS().equals(MobileOS.IOS);
    }

    public static synchronized Boolean isAndroid() {
        return getMobileOS().equals(MobileOS.ANDROID);
    }

    public static synchronized Boolean isPhone() {
        return getDeviceCategory().equals(DeviceCategory.PHONE);
    }

    public static synchronized Boolean isTablet() {
        return getDeviceCategory().equals(DeviceCategory.TABLET);
    }

    public static synchronized Boolean isDevBuild() {
        return ConfigProps.APP_TYPE.equalsIgnoreCase(StaticProps.DEV_BUILD);
    }

    public static synchronized Boolean isQABuild() {
        return ConfigProps.APP_TYPE.equalsIgnoreCase(StaticProps.QA_BUILD);
    }

    public static synchronized Boolean isReleaseBuild() {
        return ConfigProps.APP_TYPE.equalsIgnoreCase(StaticProps.RELEASE_BUILD);
    }

    public static synchronized Boolean isMTVDomesticApp() {
        return getAppName().equalsIgnoreCase(StaticProps.MTV_DOMESTIC_APP_NAME);
    }

    public static synchronized Boolean isMTVINTLApp() {
        return getAppName().equalsIgnoreCase(StaticProps.MTV_INTL_APP_NAME);
    }

    public static synchronized Boolean isTVLandApp() {
        return getAppName().equalsIgnoreCase(StaticProps.TVLAND_APP_NAME);
    }

    public static synchronized Boolean isCCDomesticApp() {
        return getAppName().equalsIgnoreCase(StaticProps.CC_DOMESTIC_APP_NAME);
    }

    public static synchronized Boolean isCCINTLApp() {
        return getAppName().equalsIgnoreCase(StaticProps.CC_INTL_APP_NAME);
    }

    public static synchronized Boolean isBETDomesticApp() {
        return getAppName().equalsIgnoreCase(StaticProps.BET_DOMESTIC_APP_NAME);
    }

    public static synchronized Boolean isBETINTLApp() {
        return getAppName().equalsIgnoreCase(StaticProps.BET_INTL_APP_NAME);
    }

    public static synchronized Boolean isCMTApp() {
        return getAppName().equalsIgnoreCase(StaticProps.CMT_APP_NAME);
    }

    public static synchronized Boolean isVH1App() {
        return getAppName().equalsIgnoreCase(StaticProps.VH1_APP_NAME);
    }
    
    public static synchronized Boolean isParamountApp() {
        return getAppName().equalsIgnoreCase(StaticProps.PARAMOUNT_APP_NAME);
    }

    public static synchronized void setAppVersion(String appVersionNumber) {
        appVersion.set(appVersionNumber);
    }

    public static synchronized String getAppVersion() {
        return appVersion.get();
    }
    
    public static synchronized void setContentDomain(String contentDomainName) {
        contentDomain.set(contentDomainName);
    }

    public static synchronized String getContentDomain() {
        return contentDomain.get();
    }
    
    public static synchronized Boolean isAndroid7() {
        if (mobileOS.get().equals(MobileOS.ANDROID)) {
            if (ConfigProps.RUN_AS_FACTORY) {
                if (TestDeviceInfo.getDeviceOSVersion().contains("7.")) {
                    return true;
                }
            } else {
                String deviceID = "";
                String androidVersion = "";
                Runtime run = Runtime.getRuntime();
                try {
                    Process process = run.exec("adb devices");
                    process.waitFor();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("\t")) {
                            deviceID = line.split("\t")[0].trim();
                            break;
                        }
                    }
                    process = run.exec("adb -s " + deviceID + " shell getprop ro.build.version.release");
                    process.waitFor();
                    bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line2 = "";
                    while ((line2 = bufferedReader.readLine()) != null) {
                        androidVersion = line2;
                    }
                    if (androidVersion.contains("7.")) {
                        return true;
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static synchronized void killProxyPortInUse(Integer proxyPort) {
    	
    	Logger.logMessage("Kill the proxy port in use : " + proxyPort);
    	List<String> getPIDvalue = new ArrayList<>();
    	
    	Runtime run = Runtime.getRuntime();
    	
    	try {
    		Process getProxyPortDetail = run.exec("lsof -i:" + proxyPort.toString());
    		getProxyPortDetail.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getProxyPortDetail.getInputStream()));
            String line = bufferedReader.readLine();
            
            while (line != null) {
          	  getPIDvalue.add(line);
              line = bufferedReader.readLine();
            }
            
            Process killPID = run.exec("kill -9 " + getPIDvalue.get(1).split(" ")[4].trim());
            killPID.waitFor();
            
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static synchronized Boolean isiOS10() {
		if (mobileOS.get().equals(MobileOS.IOS)) {
			if (ConfigProps.RUN_AS_FACTORY) {
				if (TestDeviceInfo.getDeviceOSVersion().contains("10.")) {
					return true;
				}
			} else {
				CommandExecutorUtils.getiOSProductVersion().contains("10.");
				return true;
			}
		} 
		return false;
    }
    
    public static synchronized Boolean isLiveContentDomain() { 
    	return getContentDomain().equalsIgnoreCase("Live") ? true : false;
    }
}


