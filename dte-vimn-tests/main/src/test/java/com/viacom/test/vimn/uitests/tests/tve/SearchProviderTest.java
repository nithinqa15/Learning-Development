package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SearchProviderTest extends TveBaseTest {
    
    //Declare data
    String minorProviderName;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SearchProviderTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        
        minorProviderName = ProviderManager.getDefaultProvider();
        
    }

    @TestCaseId("35075")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void searchProviderAndroidTest() {

        splashChain.splashAtRest();
        chromecastChain.dismissChromecast();
        loginChain.logoutIfLoggedIn();

        home.enterSeriesView(seriesTitle, 1);
        series.scrollUpToSeriesTitle(seriesTitle);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(minorProviderName).closeKeyboard();
        selectProvider.minorProviderBtn(minorProviderName).waitForVisible();

    }

    @TestCaseId("35075")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void searchProvideriOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.fullEpisodesBtn().waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        if (!series.episodeLockBtn(episodeTitle).isVisible()) {
        	series.scrollDownToEpisodeTtl(episodeTitle);
        }
        series.episodeLockBtn(episodeTitle).waitForVisible().tap();
        if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
        	alerts.dontAllowBtn().waitForVisible().tap();
        } else if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.viewAllProvidersBtn().isVisible()) {
            selectProvider.backBtn().waitForVisible().tap();
        }
        selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type(minorProviderName);
        selectProvider.minorProviderBtn(minorProviderName).waitForVisible();

    }
}
