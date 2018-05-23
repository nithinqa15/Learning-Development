package com.viacom.test.vimn.uitests.actionchains;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.proxy.ProxyRESTManager;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.DriverSpinUpException;
import com.viacom.test.vimn.common.exceptions.ProxySpinUpException;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.pageobjects.Home;

public class SplashChain {

    Home home;

    //ACTION CHAIN CONSTRUCTOR
    public SplashChain() {
        home = new Home();
    }

    //ACTION CHAIN METHODS
    public void splashAtRest() 
    {

	    home.waitUntilHomeScreenDisplay();
    }
}