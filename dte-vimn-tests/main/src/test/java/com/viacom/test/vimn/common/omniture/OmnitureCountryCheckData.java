package com.viacom.test.vimn.common.omniture;

import com.viacom.test.vimn.uitests.support.LocaleDataFactory;

import java.util.Map;

public class OmnitureCountryCheckData {

    private static final String UNSUPPORTED_COUNTRY_SCREEN = "unsupportedCountryScreen";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String USER_COUNTRY_CHECK = "userCountryCheck";
    //private static final String EN_US = "en-US"; // revisit after meeting
    private static final String APP_NAME = OmnitureUtils.getAppNameValueForPageCalls();

    private static Map<String,String> buildGeneralMap() {
        return new OmnitureMap()
                //.brandId(OmnitureUtils.getBrandID()) // MC-6958
                //.appID(OmnitureUtils.getAppID()) // MC-6958
                .build();
    }

    public static Map<String,String> buildUnsupportedCountryScreenExpectedMap() {
        return new OmnitureMap()
                .activity(UNSUPPORTED_COUNTRY_SCREEN)
                .pv(TRUE)
                .mapBuilder()
                .plus(buildGeneralMap());
    }

    public static Map<String,String> buildUserCountryCheckExpectedMap() {
        return new OmnitureMap()
                .activity(USER_COUNTRY_CHECK)
                .pv(FALSE)
                //.appName(APP_NAME) // MC-6958
                .appCluster(LocaleDataFactory.getCluster())
                .appCountry(LocaleDataFactory.getIsoGeoCode())
                //.appLanguage(EN_US) 
                .mapBuilder()
                .plus(buildGeneralMap());
    }

}
