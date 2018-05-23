package com.viacom.test.vimn.uitests.support;

import com.viacom.test.vimn.common.util.TestRun;

public class ProviderEnabled {

    private String countryCode;

    public ProviderEnabled() {
        countryCode = TestRun.getLocale();
    }

    public Boolean isClaroEnabled() {
        return countryCode.equalsIgnoreCase("es_co")
                || countryCode.equalsIgnoreCase("es_cr")
                || countryCode.equalsIgnoreCase("es_sv")
                || countryCode.equalsIgnoreCase("es_gt")
                || countryCode.equalsIgnoreCase("es_hn")
                || countryCode.equalsIgnoreCase("es_cl")
                || countryCode.equalsIgnoreCase("es_ni");
    }

    public Boolean isBlueToGoEnabled() {
        return countryCode.equalsIgnoreCase("es_mx");
    }

    public Boolean isSkyEnabled() {
        return countryCode.equalsIgnoreCase("pt_br");
    }

    public Boolean isTelecentroEnabled() {
        return countryCode.equalsIgnoreCase("es_ar");
    }

    public Boolean isDtvEnabled() {
        return countryCode.equalsIgnoreCase("en_us")
                || countryCode.equalsIgnoreCase("es_ar")
                || countryCode.equalsIgnoreCase("es_cl")
                || countryCode.equalsIgnoreCase("es_co")
                || countryCode.equalsIgnoreCase("es_pe")
                || countryCode.equalsIgnoreCase("es_ve");
    }

    public Boolean isDishEnabled() {
        return countryCode.equalsIgnoreCase("es_mx");
    }

    public Boolean isMegacableEnabled() {
        return countryCode.equalsIgnoreCase("es_mx");
    }

    public Boolean isSingtelEnabled() {
        return countryCode.equalsIgnoreCase("en_sg");
    }
    
    public Boolean isRiksEnabled() {
        return countryCode.equalsIgnoreCase("no_no");
    }
    
    public Boolean isGetEnabled() {
        return countryCode.equalsIgnoreCase("no_no");
    }
    
    public Boolean isBoxerEnabled() {
        return countryCode.equalsIgnoreCase("da_dk");
    }
    
    public Boolean isStofaEnabled() {
        return countryCode.equalsIgnoreCase("da_dk");
    }
}
