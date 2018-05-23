package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;
@Deprecated
public class ToTwitterAndBackTest extends TveBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ToTwitterAndBackTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (xFinity == null) {
            super.initializePageObjects();
        }

    }

    @TestCaseId("34987")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.TVLAND_APP, ParamProps.CMT_APP,
            Config.ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void toTwitterAndBackAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        signIn.goBackToProviders();
        selectProvider.xfinityProviderBtn().waitForVisible().tap();
        xFinity.twitterBtn().waitForVisible().tap();
        xFinity.twitterOAuthScreen().waitForVisible().goBack();
        xFinity.twitterBtn().waitForVisible().goBack();
        selectProvider.xfinityProviderBtn().waitForVisible().goBack();
        settingsMenu.signInBtn().waitForVisible();

    }

    //PV - Twitter Sign in failed on testing.io
    @TestCaseId("34987")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.DEPRECATED, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.TVLAND_APP, ParamProps.CMT_APP, ParamProps.VH1_APP, Config.ParamProps.PARAMOUNT_APP })
    public void toTwitterAndBackiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        settings.settingsBtn().waitForVisible().tap();
        settingsMenu.signInBtn().waitForVisible().tap();
        if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.xfinityProviderBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.xfinityProviderBtn().waitForVisible().tap();

        xFinity.twitterBtn().waitForVisible().tap().pause(StaticProps.XLARGE_PAUSE);
        if (xFinity.okAlertBtn().isVisible()) {
        	xFinity.okAlertBtn().waitForVisible().tap();
        }
        xFinity.twitterOAuthScreen().waitForVisible();
        xFinity.twitterCancelBtn().waitForVisible().tap();
        selectProvider.backBtn().waitForVisible().tap();
        if (TestRun.isPhone()) {
            signIn.closeBtn().waitForViewLoad().waitForVisible().tap();
        }
        settingsMenu.autoPlayTgl().waitForVisible();
    }
  }
