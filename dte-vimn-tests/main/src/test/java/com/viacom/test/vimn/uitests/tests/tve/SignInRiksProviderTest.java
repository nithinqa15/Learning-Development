package com.viacom.test.vimn.uitests.tests.tve;

import java.lang.reflect.InvocationTargetException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInRiksProviderTest extends TveBaseTest {

	// Declare data
    Boolean rikstvIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInRiksProviderTest(String runParams) {
        super.setRunParams(runParams);
    }

	@BeforeMethod(alwaysRun = true)
	public void setupTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        
        username = ProviderManager.getProviderUsername(StaticProps.RIKSTV);
        password = ProviderManager.getProviderPassword(StaticProps.RIKSTV);
        rikstvIsEnabled = providerEnabled.isRiksEnabled();   
	}
	
	@TestCaseId(StaticProps.ET_URL + "10718760-2cd6-4f6a-8eac-a7ad0080e651")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP })
	public void signInRiksProviderAndroidTest() {
			
        if (rikstvIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
     		series.tapEpisodeLockBtn(episodeTitle);	
     		
     		selectProvider.riksProviderBtn().waitForVisible().tap();
     		signIn.rikstvUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
     		signIn.rikstvPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
     		signIn.rikstvSubmitBtn().waitForVisible().tap();
     		signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoPlaying(ConfigProps.VIDEO_IMAGE_TIMEOUT);
            loginChain.logoutIfLoggedIn();
        } else {
        	
            String message = "RiksTV is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
	}
	
	@TestCaseId("")
	@Features(GroupProps.TVE)
	@Test(groups = { GroupProps.FULL, GroupProps.TVE })
	@Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP })
	public void signInRiksProvideriOSTest() {
			
        if (rikstvIsEnabled) {
        	
            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();
	
            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
		    series.tapEpisodeLockBtn(episodeTitle);	
		    
		    if (selectProvider.backBtn().waitForViewLoad().isVisible() && !selectProvider.riksProviderBtn().isVisible()) {
                selectProvider.backBtn().waitForVisible().tap();
            }
	        selectProvider.riksProviderBtn().waitForVisible().tap();
	        signIn.rikstvUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username);
	        keyboard.goToPasswordField();
	        signIn.rikstvPasswordTxb().waitForVisible().tap().type(password);
	        signIn.rikstvSubmitBtn().waitForVisible().tap();
	        signIn.successTxt().waitForPresent().waitForNotPresent();
	        series.episodePauseBtn(episodeTitle).waitForVisible();
	        videoPlayer.verifyVideoIsPlayingByScreenshots(5);
	        
        } else {
        	
            String message = "RiksTV is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
	}
}

