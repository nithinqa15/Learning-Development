package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class ViewAllProvidersTest extends TveBaseTest {
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public ViewAllProvidersTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        
    }

    @TestCaseId("35074")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void viewAllProvidersAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();

        selectProvider.fromMVPDtoProviderNotListedAndBack();
        
    }

    @TestCaseId("35074")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, 
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.CC_INTL_APP, 
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.CMT_APP, 
			  	  ParamProps.VH1_APP, ParamProps.PARAMOUNT_APP })
    public void viewAllProvidersiOSTest() {
        
    	splashChain.splashAtRest();
    	alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        
        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        if (!series.episodeLockBtn(episodeTitle).isVisible()) {
        	series.scrollDownToEpisodeTtl(episodeTitle);
        }
        series.tapEpisodeLockBtn(episodeTitle);
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        }
   
        if (TestRun.isTVLandApp() || TestRun.isCMTApp() || TestRun.isMTVDomesticApp() || TestRun.isVH1App() || TestRun.isParamountApp()) {
        	if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.viewAllProvidersBtn().isVisible()) {
                selectProvider.backBtn().waitForVisible().tap();
            }
            selectProvider.viewAllProvidersBtn().waitForVisible().tap();
            selectProvider.typeProviderNameTxb().waitForVisible();
            selectProvider.dontSeeProviderBtn().waitForVisible();
        }
        
        if (TestRun.isMTVINTLApp() || TestRun.isCCINTLApp()) {
            if (TestRun.isPhone()) {
            	if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.dontSeeProviderBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
                }
                selectProvider.dontSeeProviderBtn().waitForVisible().tap();
            } else {
            	if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.viewAllProvidersBtn().isVisible()) {
                    selectProvider.backBtn().waitForVisible().tap();
            	}
                selectProvider.viewAllProvidersBtn().waitForVisible().tap();
            }
            selectProvider.providerNotListedTxt().waitForPresent();
        }
    }
}
