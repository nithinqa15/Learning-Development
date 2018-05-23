package com.viacom.test.vimn.uitests.tests.tve;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import io.appium.java_client.MobileElement;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInRCNTest extends TveBaseTest {

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInRCNTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.RCN);
        password = ProviderManager.getProviderPassword(StaticProps.RCN);

    }

    @TestCaseId("35111")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInRCNAndroidTest() {

        splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.rcnProviderBtn().waitForVisible().tap();
        signIn.rcnUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
        signIn.rcnPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
        signIn.rcnSignInBtn().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35111")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInRCNiOSTest() {

        splashChain.splashAtRest();
        alertschain.dismissAlerts();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
        series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.episodeLockBtn(episodeTitle).waitForScrolledTo().tap();
        if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.xfinityProviderBtn().isVisible()) {
            selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
        }
        if (TestRun.isPhone()) {
        	selectProvider.viewAllProvidersBtn().waitForVisible().tap();
        } else {
        	//@PV - Tablet don't have the viellAllprovider button name
    		//This is the only way to find that element
    		List<MobileElement> viewAllProviderbutton = DriverManager.getAppiumDriver().findElementsByXPath("//XCUIElementTypeButton");
        	for (int i=0; i<viewAllProviderbutton.size(); i++){
        		if (viewAllProviderbutton.get(i).getAttribute("label") == null){
        			DriverManager.getAppiumDriver().findElementsByXPath("//XCUIElementTypeButton").get(i).click();
        			break;
        		}
        	}
        }
        selectProvider.typeProviderNameTxb().waitForVisible().tap().type("RCN");
        if (keyboard.returnBtn().isVisible()) {
        	keyboard.returnBtn().waitForVisible().tap().pause(StaticProps.SMALL_PAUSE);
        } 
        selectProvider.minorProviderBtn("RCN").waitForVisible().tap().pause(StaticProps.SUPER_LARGE_PAUSE);
        signIn.rcnUsernameTxb().waitForViewLoad().waitForVisible().tap().type(username);
        keyboard.goToPasswordField();
        signIn.rcnPasswordTxb().waitForVisible().tap().type(password);
        signIn.rcnSignInBtn().waitForVisible().tap();
        signIn.successTxt().waitForPresent().waitForNotPresent();
        series.episodePauseBtn(episodeTitle).waitForVisible();
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
    }
    
  }

