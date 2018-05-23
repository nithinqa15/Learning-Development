package com.viacom.test.vimn.common.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.w3c.dom.Document;

public class Config {

    private static String SYSTEM_TEST_PROP = "system.test.";
    private static String USER_DIR_PROP = "user.dir";

    public interface ParamProps {
        String IOS = "iOS";
        String ANDROID = "Android";
        String ALL_DEVICES = StaticProps.ALL_DEVICES;
        String PHONE = StaticProps.PHONE;
        String TABLET = StaticProps.TABLET;
        String ALL_APPS = StaticProps.ALL_APPS;
        String BET_DOMESTIC_APP = StaticProps.BET_DOMESTIC_APP_NAME;
        String BET_INTL_APP = StaticProps.BET_INTL_APP_NAME;
        String CC_DOMESTIC_APP = StaticProps.CC_DOMESTIC_APP_NAME;
        String CC_INTL_APP = StaticProps.CC_INTL_APP_NAME;
        String CMT_APP = StaticProps.CMT_APP_NAME;
        String MTV_DOMESTIC_APP = StaticProps.MTV_DOMESTIC_APP_NAME;
        String MTV_INTL_APP = StaticProps.MTV_INTL_APP_NAME;
        String NICK_APP = StaticProps.NICK_APP_NAME;
        String NICK_JR_APP = StaticProps.NICK_JR_APP_NAME;
        String PARAMOUNT_APP = StaticProps.PARAMOUNT_APP_NAME;
        String TVLAND_APP = StaticProps.TVLAND_APP_NAME;
        String VH1_APP = StaticProps.VH1_APP_NAME;
        String USA = "United States of America";
        String BRAZIL = "Brazil";
        String MEXICO = "Mexico";
        String ARGENTINA = "Argentina";
        String PERU = "Peru";
        String COLUMBIA = "Columbia";
        String COSTA_RICA = "Costa Rica";
        String CHILE = "Chile";
        String VENEZUALA = "Venezuala";
        String EL_SALVADOR = "El Salvador";
        String GUATEMALA = "Guatemala";
        String HONDURAS = "Honduras";
        String NICARAGUA = "Nicaragua";
        String SINGAPORE = "Singapore";
        String POLAND = "Poland";
        String AUSTRIA = "Austria";
        String DENMARK = "Denmark";
        String NORWAY = "Norway";
        String BELGIUM = "Belgium";
        String GERMANY = "Germany";
        String NETHERLANDS = "Netherlands";
        String ITALY = "Italy";
        String UNITED_KINGDOM = "United Kingdom";
    }

    public interface GroupProps {
        String FULL = "Full";
        String SMOKE = "Smoke";
        String BROKEN = "Broken";
        String ON_REQUEST = "OnRequest";
        String DEBUG = "Debug";
        String DEPRECATED = "Deprecated";
        String SETTINGS = "Settings";
        String HOME_SCREEN = "HomeScreen";
        String SHOW_DETAILS = "ShowDetails";
        String TVE = "TVE";
        String LIVE_TV = "LiveTV";
        String VIDEO_PLAYER = "VideoPlayer";
        String VIDEO_ADS = "VideoAds";
        String RESUME_PLAYBACK = "ResumePlayback";
        String CLIPS = "Clips";
        String CLIPS_CONTINOUS_PLAYBACK = "ClipsContinousPlayback";
        String OMNITURE = "Omniture";
        String MEGABEACON = "Megabeacon";
        String HEARTBEAT = "Heartbeat";
        String BENTO_SMOKE = "BentoSmoke";
        String ALL_SHOWS = "AllShows";
        String NOTIFIERS = "Notifiers";
        String SHORTFORM_ONLY = "ShortForm_Only";
        String EPISODES_ONLY = "Episodes_Only";
        String GENERAL = "General";
        String PLAYBACK = "Playback";
        String SHARE = "Share";
        String SINGLE_BINARY = "singlebinary";
        String TRUE_BACKGROUND_VIDEO_SERVICES = "TrueBackgroundVideoServices";
        String APPTENTIVE = "Apptentive";
        String FILTERS_UNIT_TEST = "FiltersUnitTest";
        String NAVIGATION_AND_REPORTING = "NavigationAndReporting";
        String MOVIES_CONTENT = "MoviesContent";
        String FIGHTS_CONTENT = "FightsContent";
        String CONTENT_DETAIL_SCREEN = "ContentDetailScreen";
        String TVE_FLOW_AND_REPORTING = "TVEFlowAndReporting";
        String FB_ANALYTICS = "FBAnalytics";
        String CONFIG_END_POINT = "ConfigEndPoint";
        String Search = "Search";
        String VIDEOPLAYBACK_ON_OFF_VIDEO_PLAY_PAUSE = "VideoPlaybackOnOffVideoPlayPause";
        String TVE_SMOKE = "TVESmoke";
    }

    public interface AttributeProps {
        String TEXT = "text";
        String NAME = "name";
        String VALUE = "value";
        String INDEX = "index";
        String RESOURCE_ID = "resource-id";
    }

    public interface StaticProps {
        String GLOBAL_USER = "admin@";
        String IOS = "iOS";
        String ANDROID = "Android";
        String ALL = "All";
        String BET_DOMESTIC_APP_NAME = "BET_DOMESTIC";
        String BET_INTL_APP_NAME = "BET_INTL";
        String CC_DOMESTIC_APP_NAME = "CC_DOMESTIC";
        String CC_INTL_APP_NAME = "CC_INTL";
        String CMT_APP_NAME = "CMT";
        String MTV_DOMESTIC_APP_NAME = "MTV_DOMESTIC";
        String MTV_INTL_APP_NAME = "MTV_INTL";
        String NICK_APP_NAME = "NICK";
        String NICK_JR_APP_NAME = "NICK_JR";
        String PARAMOUNT_APP_NAME = "PARAMOUNT";
        String TVLAND_APP_NAME = "TVLand";
        String VH1_APP_NAME = "VH1";
        String ALL_APPS = "AllApps";
        String ALL_DEVICES = "AllDevices";
        String ALL_DEVICES_IOS10 = "AllDevicesiOS10";
        String ALL_DEVICES_IOS11 = "AllDevicesiOS11";
        String ALL_DEVICES_ANDROID_7 = "AllDevicesAndroid7";
        String PHONE = "Phone";
        String TABLET = "Tablet";
        String DEV_BUILD = "Dev";
        String QA_BUILD = "QA";
        String RELEASE_BUILD = "Release";
        String ATTACHMENT_DATE_FORMAT = "MMddyyhhmmssSSSa";
        String PACKAGE_DATE_FORMAT = "MMddyyhhmmss";
        Integer MINOR_PAUSE = 500;
        Integer SMALL_PAUSE = 1000;
        Integer MEDIUM_PAUSE = 2000;
        Integer LARGE_PAUSE = 3000;
        Integer XLARGE_PAUSE = 5000;
        Integer XXLARGE_PAUSE = 7500;
        Integer SUPER_LARGE_PAUSE = 15000;
        Integer IMPLICIT_WAIT_MILLISECOND = 20;
        Integer HOME_SCREEN_LAUNCH_TIME = 10;
        String LOCATOR_VARIABLE_PACKAGE = "$inPackage";
        String LOCATOR_VARIABLE = "$inText";
        String ANCILLARY_LOCATOR_VARIABLE = "$inText2";
        String LOCATOR_VARIABLE_UPPER = "$inTextU";
        String ANCILLARY_LOCATOR_VARIABLE_UPPER = "$inText2U";
        String LOCATOR_VARIABLE_LOWER = "$inTextL";
        String ANCILLARY_LOCATOR_VARIABLE_LOWER = "$inText2L";
        String LOCATOR_ANDROID_UIAUTOMATOR = "AndroidUIAutomator";
        String LOCATOR_IOS_UIAUTOMATION = "IosUIAutomation";
        String LOCATOR_ACCESSIBILITY_ID = "AccessibilityId";
        String LOCATOR_IOS_NS_PREDICATE = "IosNsPredicate";
        String LOCATOR_XPATH = "XPath";
        String SCROLL_UP = "Up";
        String SCROLL_DOWN = "Down";
        Integer MAX_ARTICLE_SWIPE = 10;
        String MOBILE_OS_LOG = "Mobile OS: ";
        String APPLICATION_LOG = "Application: ";
        String DEVICE_NAME_LOG = "DeviceName: ";
        String LOCALE_LOG = "Locale: ";
        String DEVICE_CATEGORY_LOG = "Device Category: ";
        String ETID_CATEGORY_LOG = "Enterprise Tester ID: ";
        String DEFAULT_DATA_PROVIDER = "defaultDataProvider";
        String SKY = "Sky";         
        String RIKSTV =  "RiksTV";
        String GET =  "Get";
        String BOXER =  "Boxer";  
        String STOFA =  "Stofa";
        String MEGACABLE = "Megacable";
        String TELECENTRO = "Telecentro";
        String BLUE_TO_GO = "BlueToGo";
        String ARMSTRONG = "Armstrong";
        String DISH = "Dish";
        String OPTIMUM = "Optimum";
        String CLARO = "Claro";
        String DIRECTTV = "DirectTV";
        String WOW = "WOW";
        String ATT = "ATT";
        String SERVICEELECTRIC = "ServiceElectric";
        String VERIZON = "Verizon";
        String RCN = "RCN";
        String COX = "COX";
        String TIMEWARNERCABLE = "Spectrum";
        String MEDIACOM = "Mediacom";
        String BRIGHTHOUSE = "Brighthouse";
        String FRONTIER = "Frontier";
        String XFINITY = "XFinity";
        String SINGTEL = "Singtel";
        String ET_URL = "http://enttester-app-1.mtvn.ad.viacom.com/et5/home/index.rails#/script/edit/";
        String TESTRAIL_PROJECT_ID = "26";
        String TEST_ID = "TestID:";
        Integer SUPER_FAST_SCROLL = 100;
        Integer FAST_SCROLL = 250;
        Integer NORMAL_SCROLL = 500;
        Integer SLOW_SCROLL = 1000;
        String XML_DUMP_FOLDER = Config.getFilePath("PathToXMLDumps");
        String MEDIAGEN_URL = "media.mtvnservices.com";
        String MTVUNDERTHETHUMB = "mtvunderthethumb";
        String GEOINFO = "geo-info";
        String APPNAME = "AppName";
        String APPSUPPORTNAME = "AppSupportName";
        String APPSUPPORTCONTACTEMAIL = "AppContactSupportToEmail";
        String OMNITUREAPPID = "OmnitureAppId";
        String OMNITUREAPPNAME = "OmnitureAppName";
        String OMNITUREBRANDID = "OmnitureBrandId";
        String VIDOWNER = "vidOwner";
        String PLAYERMGID = "playerMgid";
        String PLAYERMGIDREPORT = "playerMgidReport";
        String CHANNEL = "channel";
        String DOMAIN = "Domain";
        String IMAGEDOMAIN = "ImageDomain";
        String LIVETV = "LiveTV";
        String LIVE_FEED_BASE_URL = "http://api.playplex.viacom.com";
        String STAGING_FEED_BASE_URL = "http://testing.api.playplex.viacom.vmn.io";
        String FEED_REGEX = ".*api.playplex.viacom[a-z\\.]+/feeds/networkapp/intl.*";
        String LIVE_FEED_BASE_GEO_URL = "http://api.playplex.viacom.com/geo-info";
        String STAGING_FEED_BASE_GEO_URL = "http://testing.api.playplex.viacom.vmn.io/geo-info";
        String MAIN_CONFIG_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/main/$apiVersion?key=networkapp1.0&brand=$brand&platform=$platform&region=$region&version=$appVersion";
        String SERIES_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/series/items/$apiVersion/mgid:arc:series:$domain:$inText?key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
        String CLIPS_ITEM_FEED_BASE_URL = "$baseUrl/feeds/networkapp/intl/series/clips/$apiVersion/mgid:arc:series:$domain:$inText?key=networkapp1.0&platform=$platform&brand=$brand&region=$region&version=$appVersion";
        String REGEX_MAINFEED_URL = ".*feeds/networkapp/intl/main.*"; // modify this url because feed pointing to Live in iOS. This pattern cover both Test and Live
        String REGEX_PROMOFEED_URL = ".*feeds/networkapp/intl/promolist.*"; // modify this url because feed pointing to Live in iOS. This pattern cover both Test and Live
        String MRSS_BASE_URL = "intl-mrss-player-feed";
        String REGEX_MEDIA_MTVNSERVICES_URL = ".*http://media.mtvnservices.com/pmt/e1/access/index.html\\?uri.*";
        String EXTENDED_PROVIDERS_LIST = ".*tveauth/bueller/(ios|android)/.*/extendedProvidersList.*"; // modify this url because TVE pointing to Live in iOS. This pattern cover both stage and Live
        String GET_PROVIDERS_LIST = ".*/tveauth.*/(ios|android).*/(getProvidersList|providersList).*"; // pattern cover both stage and Live
        String FEATURES_LIST = ".*/tveauth/bueller/(ios|android)/.*/featuresList.*";
        String PMT_REQUEST_URL = "media.mtvnservices.com/pmt";
        String FREEWHEEL_REQUEST_URL = ".*fwmrm.*";
        String MOAT_ADS_HOST = ".*z.moatads.com.*";
        String FB_ANALYTICS_HOST = ".*graph.facebook.com.*";
        String ADB_MOBILE_CONFIG = ".*btg.mtvnservices.com/aria/app/ADBMobileConfig.js.*";
        
        // For Tve
        String ADOBE_AUTHORIZE_URL = ".*sp.auth.*adobe.com/adobe-services/authorizeDevice";
        String UNAUTHORIZE_HEADER = "Authzf-Error-Code";
        String XBOX_ACCESS_TOKEN = ".*xbox.mtvnservices(-[q|d]\\.mtvi)?.com/tveauth/v1/(ios|android)/([^/])+/getAccessToken\\?.*";
        String XBOX_REFRESH_TOKEN = ".*xbox.mtvnservices(-[q|d]\\.mtvi)?.com/tveauth/v1/(ios|android)/([^/])+/refreshToken\\?.*";
    }

    public interface ConfigProps {
        // RUNTIME CONFIG
        Boolean RUN_AS_FACTORY = Config.getBoolean("RunAsFactory");
        Boolean PREINSTALLED = Config.getBoolean("Preinstalled");
        Integer DEBUG_PROXY_PORT = Config.getInt("DebugProxyPort");
        String APP_NAME = Config.getString("AppName");
        String APP_TYPE = Config.getString("AppType");
        String MOBILE_OS = Config.getString("MobileOS");
        String LOCALE = Config.getString("Locale");
        String DEVICE_CATEGORY = Config.getString("DeviceCategory");
        Integer DEVICE_BANDWIDTH = Config.getInt("DeviceBandwidth");
        Integer NETWORK_LATENCY = Config.getInt("NetworkLatency");
        Boolean UPLOAD_REPORT_JENKINS = Config.getBoolean("UploadReportToJenkins");
        Boolean SET_HUE_LIGHTS = Config.getBoolean("SetHueLights");
        Boolean SEND_REPORT_AUTOEMAILS = Config.getBoolean("SendReportAutoEmails");
        Boolean SEND_REPORT_CHAT = Config.getBoolean("SendChatReport");
        Boolean RERUN_ON_FAILURE = Config.getBoolean("ReRunOnFailure");
        Integer RERUN_ON_FAILURE_COUNT = Config.getInt("ReRunOnFailureCount");
        String SEND_REPORT_EMAIL_ADDRESS = Config.getString("SendReportEmailAddress");
        Boolean SEND_SPLUNK_DATA = Config.getBoolean("SendSplunkData");
        String CONTENT_DOMAIN = Config.getString("ContentDomain");

        // APP CONFIG
        String ELEMENT_FILE_PATH = Config.getFilePath("PathToElements");
        String LAB_FILE_PATH = Config.getFilePath("PathToLabConfig");
        String LOCALEDATA_FILE_PATH = Config.getFilePath("PathToLocaleDataConfig");
        String BRAND_DATA_FILE_PATH = Config.getFilePath("PathToBrandDataConfig");
        String IOS_SCREENSHOT_WAIT = Config.getString("IOSAutoScreenshotWait");
        String MTV_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("MTVDomesticAndroidAppPackageAlphaQA");
        String MTV_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("MTVDomesticAndroidAppPackageBetaRelease");
        String MTV_DOMESTIC_ANDROID_LAUNCH_ACTIVITY = Config.getString("MTVDomesticAndroidLaunchActivity");
        String MTV_INTL_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("MTVINTLAndroidAppPackageAlphaQA");
        String MTV_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("MTVINTLAndroidAppPackageBetaRelease");
        String MTV_INTL_ANDROID_LAUNCH_ACTIVITY = Config.getString("MTVINTLAndroidLaunchActivity");
        String CC_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("CCDomesticAndroidAppPackageAlphaQA");
        String CC_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("CCDomesticAndroidAppPackageBetaRelease");
        String CC_DOMESTIC_ANDROID_LAUNCH_ACTIVITY = Config.getString("CCDomesticAndroidLaunchActivity");
        String CC_INTL_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("CCINTLAndroidAppPackageAlphaQA");
        String CC_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("CCINTLAndroidAppPackageBetaRelease");
        String CC_INTL_ANDROID_LAUNCH_ACTIVITY = Config.getString("CCINTLAndroidLaunchActivity");
        String BET_DOMESTIC_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("BETDomesticAndroidAppPackageAlphaQA");
        String BET_DOMESTIC_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("BETDomesticAndroidAppPackageBetaRelease");
        String BET_DOMESTIC_ANDROID_LAUNCH_ACTIVITY = Config.getString("BETDomesticAndroidLaunchActivity");
        String BET_INTL_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("BETINTLAndroidAppPackageAlphaQA");
        String BET_INTL_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("BETINTLAndroidAppPackageBetaRelease");
        String BET_INTL_ANDROID_LAUNCH_ACTIVITY = Config.getString("BETINTLAndroidLaunchActivity");
        String TVLAND_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("TVLandAndroidAppPackageAlphaQA");
        String TVLAND_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("TVLandAndroidAppPackageBetaRelease");
        String TVLAND_ANDROID_LAUNCH_ACTIVITY = Config.getString("TVLandAndroidLaunchActivity");
        String PARAMOUNT_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("ParamountAndroidAppPackageAlphaQA");
        String PARAMOUNT_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("ParamountAndroidAppPackageBetaRelease");
        String PARAMOUNT_ANDROID_LAUNCH_ACTIVITY = Config.getString("ParamountAndroidLaunchActivity");
        String CMT_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("CMTAndroidAppPackageAlphaQA");
        String CMT_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("CMTAndroidAppPackageBetaRelease");
        String CMT_ANDROID_LAUNCH_ACTIVITY = Config.getString("CMTAndroidLaunchActivity");
        String VH1_ANDROID_ALPHA_QA_APP_PACKAGE = Config.getString("VH1AndroidAppPackageAlphaQA");
        String VH1_ANDROID_BETA_RELEASE_APP_PACKAGE = Config.getString("VH1AndroidAppPackageBetaRelease");
        String VH1_ANDROID_LAUNCH_ACTIVITY = Config.getString("VH1AndroidLaunchActivity");
        String MTV_DOMESTIC_IOS_BUNDLE_ID_DEV = Config.getString("MTVDomesticiOSBundleIDDev");
        String MTV_DOMESTIC_IOS_BUNDLE_ID_RELEASE = Config.getString("MTVDomesticiOSBundleIDRelease");
        String MTV_INTL_IOS_BUNDLE_ID_DEV = Config.getString("MTVINTLiOSBundleIDDev");
        String MTV_INTL_IOS_BUNDLE_ID_RELEASE = Config.getString("MTVINTLiOSBundleIDRelease");
        String CC_DOMESTIC_IOS_BUNDLE_ID_DEV = Config.getString("CCDomesticiOSBundleIDDev");
        String CC_DOMESTIC_IOS_BUNDLE_ID_RELEASE = Config.getString("CCDomesticiOSBundleIDRelease");
        String CC_INTL_IOS_BUNDLE_ID_DEV = Config.getString("CCINTLiOSBundleIDDev");
        String CC_INTL_IOS_BUNDLE_ID_RELEASE = Config.getString("CCINTLiOSBundleIDRelease");
        String TVLAND_IOS_BUNDLE_ID_DEV = Config.getString("TVLandiOSBundleIDDev");
        String TVLAND_IOS_BUNDLE_ID_RELEASE = Config.getString("TVLandiOSBundleIDRelease");
        String BET_DOMESTIC_IOS_BUNDLE_ID_DEV = Config.getString("BETDomesticiOSBundleIDDev");
        String BET_DOMESTIC_IOS_BUNDLE_ID_RELEASE = Config.getString("BETDomesticiOSBundleIDRelease");
        String BET_INTL_IOS_BUNDLE_ID_DEV = Config.getString("BETINTLiOSBundleIDDev");
        String BET_INTL_IOS_BUNDLE_ID_RELEASE = Config.getString("BETINTLiOSBundleIDRelease");
        String CMT_IOS_BUNDLE_ID_DEV = Config.getString("CMTiOSBundleIDDev");
        String CMT_IOS_BUNDLE_ID_RELEASE = Config.getString("CMTiOSBundleIDRelease");
        String VH1_IOS_BUNDLE_ID_DEV = Config.getString("VH1iOSBundleIDDev");
        String VH1_IOS_BUNDLE_ID_RELEASE = Config.getString("VH1iOSBundleIDRelease");
        String PARAMOUNT_IOS_BUNDLE_ID_DEV = Config.getString("ParamountiOSBundleIDDev");
        String PARAMOUNT_IOS_BUNDLE_ID_RELEASE = Config.getString("ParamountiOSBundleIDRelease");
        String IOS_AUTO_ACCEPT_ALERTS = Config.getString("AutoAcceptiOSAlerts");
        String APPLICATION_TITLE = Config.getString("ApplicationTitle");
        String BUILD_NUMBER = Config.getString("BuildNumber");
        String APP_URL = Config.getString("AppUrl");
        String PATH_TO_APP_PACKAGE = Config.getString("PathToAppPackage");
        String API_VERSION = Config.getString("ApiVersion");
        String IOS_APP_VERSION = Config.getString("iOSAppVersion");
        String ANDROID_APP_VERSION = Config.getString("AndroidAppVersion");

        // REPORT CONFIG
        String EMAIL_SENDER_ADDRESS = Config.getString("EmailSenderAddress");
        String SCREENSHOT_FILE_PATH = Config.getFilePath("PathToScreenshots");
        String ALLURE_RESULTS_IOS_PATH = Config.getFilePath("PathToAllureiOSResults");
        String ALLURE_RESULTS_ANDROID_PATH = Config.getFilePath("PathToAllureAndroidResults");
        Boolean ATTACH_APP_XMLTREE_LOGS = Config.getBoolean("AttachAppXMLTreeLogs");
        Boolean ATTACH_APP_PROXY_LOGS = Config.getBoolean("AttachAppProxyLogs");
        Boolean ATTACH_DEVICE_LOGS = Config.getBoolean("AttachDeviceLogs");
        String HUE_BRIDGE_IP = Config.getString("HueBridgeIP");
        String HUE_USER_ID = Config.getString("HueUserId");
        String HUE_ANDROID_LIGHT_ID = Config.getString("HueAndroidLightId");
        String HUE_IOS_LIGHT_ID = Config.getString("HueiOSLightId");
        String JENKINS_IOS_WORKSPACE_FILE_PATH =  Config.getString("PathToiOSJenkinsWorkspace");
        String JENKINS_ANDROID_WORKSPACE_FILE_PATH =  Config.getString("PathToAndroidJenkinsWorkspace");
        String JENKINS_IOS_WORKSPACE_URL =  Config.getString("iOSJenkinsWorkspaceUrl");
        String JENKINS_ANDROID_WORKSPACE_URL =  Config.getString("AndroidJenkinsWorkspaceUrl");
        String S3_BUCKET_NAME = Config.getString("S3BucketName");
        String IOS_MEGA_BEACON_URL = Config.getString("iOSMegaBeaconUrl");
        String ANDROID_MEGA_BEACON_URL = Config.getString("AndroidMegaBeaconUrl");

        // SPLUNK CONFIG
        String SPLUNK_USERNAME = Config.getString("SplunkUsername");
        String SPLUNK_PASSWORD = Config.getString("SplunkPassword");
        String SPLUNK_INDEX = Config.getString("SplunkIndex");

        // CHAT CONFIG
        String DTE_CHAT_WEBHOOK_URL = Config.getString("DTEChatWebHookUrl");
        String ANDROID_CHAT_WEBHOOK_URL = Config.getString("AndroidChatWebHookUrl");
        String IOS_CHAT_WEBHOOK_URL = Config.getString("iOSChatWebHookUrl");

        // SLACK CONFIG
        String PLAYPLEX_ANDROID_SLACK_WEBHOOK = Config.getString("PlayPlexAndroidSlackWebhook");
        String PLAYPLEX_IOS_SLACK_WEBHOOK = Config.getString("PlayPlexiOSSlackWebhook");

        // WEBDRIVER CONFIG
        Integer VIDEO_IMAGE_TIMEOUT = Config.getInt("VideoImageTimeout");
        Integer MAX_WAIT_TIME = Config.getInt("WaitForWaitTime");
        String SERVER_COMMAND_TIMEOUT = Config.getString("ServerCommandTimeout");
        Integer POLLING_TIME = Config.getInt("PollingTime");

        // OMNITURE CONFIG
        String OMNITURE_REPORT_INDENTIFIER = Config.getString("OmnitureIdentifier");
    }

    private static String getFilePath(final String parameterName) {
        String parameterValue = System.getProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase());
        if (parameterValue != null) {
            return System.getProperty(USER_DIR_PROP) + parameterValue.replace("/", File.separator);
        }
        String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
        System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
        return System.getProperty(USER_DIR_PROP) +  propFromXML.replace("/", File.separator);
    }

    private static Integer getInt(final String parameterName) {
        String parameterValue = System.getProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase());
        if (parameterValue != null) {
            return Integer.parseInt(parameterValue);
        }
        String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
        System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
        return Integer.parseInt(propFromXML);
    }

    private static Boolean getBoolean(final String parameterName) {
        String parameterValue = System.getProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase());
        if (parameterValue != null) {
            return Boolean.valueOf(parameterValue);
        }
        String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
        System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
        return Boolean.valueOf(propFromXML);
    }

    private static String getString(final String parameterName) {
        String parameterValue = System.getProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase());
        if (parameterValue != null) {
            return parameterValue;
        }
        String propFromXML = getXPathValueFromFile(getConfigFileLocation(), getParameterValue(parameterName));
        System.setProperty(SYSTEM_TEST_PROP + parameterName.toLowerCase(), propFromXML);
        return propFromXML;
    }

    private static String getConfigFileLocation() {
        String fileLoc = System.getProperty(USER_DIR_PROP) + "/src/test/resources/TestNGSuiteConfig.xml";
        return fileLoc.replace("/", File.separator);
    }

    private static String getParameterValue(String parameterName) {
        return "//parameter[@name='" + parameterName + "']/@value";
    }

    private static String getXPathValueFromFile(final String fileLocation, final String xpathQuery) {
        String value = null;
        try {
            File file = new File(fileLocation);
            DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = xmlFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(file);
            XPathFactory xpathFact = XPathFactory.newInstance();
            XPath xpath = xpathFact.newXPath();
            value = (String) xpath.evaluate(xpathQuery, xmlDoc, XPathConstants.STRING);
        } catch (Exception e) {
            Assert.fail("Failed to retrieve configuration value from Config File at '" + fileLocation
                    + "' with xpath query '" + xpathQuery + "'.", e);
        }
        return value;
    }
}
