package com.viacom.test.vimn.uitests.support;

public class ProviderManager {

    public ProviderManager() {

    }
    
    public static String getDefaultProvider() {
    	return LocaleDataFactory.getDefaultProviderData().get("name");
    }
    
    public static String getDefaultUsername() {
    	return LocaleDataFactory.getDefaultProviderData().get("username");
    }
    
    public static String getDefaultPassword() {
        return LocaleDataFactory.getDefaultProviderData().get("password");
    }

    public static String getDefaultNoAuthZUsername() { return LocaleDataFactory.getDefaultProviderData().get("noAuthZUsername"); }

    public static String getDefaultNoAuthZPassword() { return LocaleDataFactory.getDefaultProviderData().get("noAuthZPassword"); }

    public static String getDefaultInvalidUsername() {
        return LocaleDataFactory.getDefaultProviderData().get("invalidUsername");
    }

    public static String getDefaultInvalidPassword() {
        return LocaleDataFactory.getDefaultProviderData().get("invalidPassword");
    }
    
    public static String getProvider(String providerName) {
    	return LocaleDataFactory.getProviderData(providerName).get("name");
    }
    
    public static String getProviderUsername(String providerName) {
    	return LocaleDataFactory.getProviderData(providerName).get("username");
    }
    
    public static String getProviderPassword(String providerName) {
    	return LocaleDataFactory.getProviderData(providerName).get("password");
    }

    public static String getProviderNoAuthZUsername(String providerName) {
        return LocaleDataFactory.getProviderData(providerName).get("noAuthZUsername");
    }

    public static String getProviderNoAuthZPassword(String providerName) {
        return LocaleDataFactory.getProviderData(providerName).get("noAuthZPassword");
    }

    public static String getProviderInvalidUsername(String providerName) {
        return LocaleDataFactory.getProviderData(providerName).get("invalidUsername");
    }

    public static String getProviderInvalidPassword(String providerName) {
        return LocaleDataFactory.getProviderData(providerName).get("invalidPassword");
    }

}