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

public class SignInGetProviderTest extends TveBaseTest {

	// Declare data
    Boolean getIsEnabled;
	
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInGetProviderTest(String runParams) {
        super.setRunParams(runParams);
    }

	@BeforeMethod(alwaysRun = true)
	public void setupTest() {
		
        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.GET);
        password = ProviderManager.getProviderPassword(StaticProps.GET);
        getIsEnabled = providerEnabled.isGetEnabled(); 
 
	}
	
	@TestCaseId("35083")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP })
	public void signInGetProviderAndroidTest() {
		
        if (getIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();
		
            home.enterSeriesView(seriesTitle, numberOfSwipes);
 		    series.tapEpisodeLockBtn(episodeTitle);	

            selectProvider.getProviderBtn().waitForVisible().tap();
            signIn.getUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.getPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.getSubmitBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            
        } else {
        	
            String message = "Get is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        } 
	}
	
	@TestCaseId("")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP })
	public void signInGetProvideriOSTest() {
		
        if (getIsEnabled) {

            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();
		
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
		    series.tapEpisodeLockBtn(episodeTitle);	
		    
		    if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.getProviderBtn().isVisible()) {
                selectProvider.backBtn().waitForVisible().tap();
            }
	        selectProvider.getProviderBtn().waitForVisible().tap();
	        signIn.getUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username);
	        keyboard.goToPasswordField();
	        signIn.getPasswordTxb().waitForVisible().tap().type(password);
	        signIn.getSubmitBtn().waitForVisible().tap();
	        signIn.successTxt().waitForPresent().waitForNotPresent();
	        series.episodePauseBtn(episodeTitle).waitForVisible();
	        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
            
        } else {
        	
            String message = "Get is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        } 
	}
}

