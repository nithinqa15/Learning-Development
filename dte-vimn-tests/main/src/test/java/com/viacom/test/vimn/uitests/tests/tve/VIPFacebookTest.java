package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class VIPFacebookTest extends TveBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public VIPFacebookTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (xFinity == null) {
            super.initializePageObjects();
        }

    }

    // GF 14/11/2016 - Marked as broken because FB login view is completely NAF
    @TestCaseId("34993")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPFacebookAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        signIn.goBackToProviders();
        selectProvider.xfinityProviderBtn().waitForVisible().tap();

        xFinity.facebookBtn().waitForVisible().tap();
        xFinity.facebookWeb().waitForVisible();

    }

    //GF - 02/06/2017 - Set test to broken group as TVE component is unstable
    @TestCaseId("34993")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void VIPFacebookiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.xfinityProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.facebookBtn().waitForVisible().tap();
        if (xFinity.okAlertBtn().isVisible()) {
        	xFinity.okAlertBtn().waitForVisible().tap();
        }
        if (!xFinity.facebookWeb().isVisible()) {
      	  String message = "Facebook account not configured in local device";
            Logger.logMessage(message);
            throw new SkipException(message);
      } else {
          xFinity.facebookWeb().waitForVisible();
      }
    }
  }
