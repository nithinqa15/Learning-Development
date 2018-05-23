package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInMegacableTest extends TveBaseTest {

    Boolean megacableIsEnabled;
    
    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInMegacableTest(String runParams) {
    	super.setRunParams(runParams);
    }
    
    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }
        
        username = ProviderManager.getProviderUsername(StaticProps.MEGACABLE);
        password = ProviderManager.getProviderPassword(StaticProps.MEGACABLE);
        megacableIsEnabled = providerEnabled.isMegacableEnabled();
        
    }

    @TestCaseId("35097")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.MEXICO })
    public void signInMegacableAndroidTest() {

        if (megacableIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.tapEpisodeLockBtn(episodeTitle);
            signIn.goBackToProviders();
            selectProvider.megacableProviderBtn().waitForVisible().tap();
            signIn.megacableUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.megacablePasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.megacableSignInBtn().waitForVisible().tap();
            signIn.megacableClearAllSessions();
            signIn.megacableFailedLoginThrowException();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "Megacable is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }

    }

    @TestCaseId("35097")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP })
    public void signInMegacableiOSTest() {

        if (megacableIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.episodeLockBtn(episodeTitle).waitForVisible().tap();
            selectProvider.megacableProviderBtn().waitForVisible().tap().pause(1000);
            signIn.megacableUsernameTxb().waitForVisible().tap().type(username);
            keyboard.goToPasswordField();
            signIn.megacablePasswordTxb().waitForVisible().tap().type(password);
            signIn.megacableSignInBtn().waitForVisible().tap();
            signIn.megacableClearAllSessions();
            signIn.megacableFailedLoginThrowException();
            // signIn.successTxt().waitForPresent().waitForNotPresent(); this step is failing as the clear sessions and failed login checks take too long
            
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "Megacable is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }
}