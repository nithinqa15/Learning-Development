package com.viacom.test.vimn.uitests.tests.tve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInStofaProviderTest extends TveBaseTest {

	// Declare data
    Boolean stofaIsEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
	public SignInStofaProviderTest(String runParams) {
	    super.setRunParams(runParams);
	}

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {

	    if (seriesTitle == null) {
	        super.tveSetupTest();
	    }
	             
	    username = ProviderManager.getProviderUsername(StaticProps.STOFA);
	    password = ProviderManager.getProviderPassword(StaticProps.STOFA);
	    stofaIsEnabled = providerEnabled.isStofaEnabled();   
	}

	@TestCaseId("35082")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP })
	public void SignInStofaProviderAndroidTest() {
		
        if (stofaIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();
	
            home.enterSeriesView(seriesTitle, numberOfSwipes);
		    series.tapEpisodeLockBtn(episodeTitle);	

	        selectProvider.stofaProviderBtn().waitForVisible().tap();
	        signIn.stofaUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
	        signIn.stofaPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
	        signIn.stofaSubmitBtn().waitForVisible().tap();
	        signIn.waitForSigningInText();
	        signIn.successTxt().waitForVisible().waitForNotPresent();
	        series.waitForEpisodeLoad(episodeTitle);
	        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
       
        } else {   	
            String message = "Stofa is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
	    }
	}
	
	@TestCaseId("35082")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP })
	public void SignInStofaProvideriOSTest() {
		
        if (stofaIsEnabled) {

            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();
	
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
		    series.tapEpisodeLockBtn(episodeTitle);	
		    
		    if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.stofaProviderBtn().isVisible()) {
                selectProvider.backBtn().waitForVisible().tap();
            }
	        selectProvider.stofaProviderBtn().waitForVisible().tap();
	        signIn.stofaUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username);
	        keyboard.goToPasswordField();
	        signIn.stofaPasswordTxb().waitForVisible().tap().type(password);
	        signIn.stofaSubmitBtn().waitForVisible().tap();
	        signIn.successTxt().waitForPresent().waitForNotPresent();
	        series.episodePauseBtn(episodeTitle).waitForVisible();
	        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
       
        } else {   	
            String message = "Stofa is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
	    }
	}
}

