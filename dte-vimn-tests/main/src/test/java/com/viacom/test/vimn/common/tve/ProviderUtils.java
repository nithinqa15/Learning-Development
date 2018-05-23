package com.viacom.test.vimn.common.tve;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.util.Config;


public class ProviderUtils {
	
    public static boolean isRefreshTokenCallPresent() {
        Logger.logMessage("Checking refreshToken call presence");
        try {
            ProxyLogUtils.getRequestURL(Config.StaticProps.XBOX_REFRESH_TOKEN);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
