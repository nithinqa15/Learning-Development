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
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInDirectTVTest extends TveBaseTest {

    Boolean dteIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInDirectTVTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.DIRECTTV);
        password = ProviderManager.getProviderPassword(StaticProps.DIRECTTV);
        dteIsEnabled = providerEnabled.isDtvEnabled();

    }

    @TestCaseId("35106")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })
    public void signInDirectTVAndroidTest() {

        if (dteIsEnabled) {

            splashChain.splashAtRest();
            chromecastChain.dismissChromecast();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            series.fullEpisodesBtn().waitForVisible().tap();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.tapEpisodeLockBtn(episodeTitle);
            signIn.goBackToProviders();
            selectProvider.directTVProviderBtn().waitForVisible().tap();
            signIn.directTVUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.directTVPasswordTxb().waitForVisible().tap().type(password).submit();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "DirectTV is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

    @TestCaseId("35106")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.CMT_APP,
			  	  ParamProps.MTV_DOMESTIC_APP, ParamProps.TVLAND_APP,
			  	  ParamProps.BET_DOMESTIC_APP, ParamProps.PARAMOUNT_APP,
			  	  ParamProps.CC_DOMESTIC_APP, ParamProps.VH1_APP })

    public void signInDirectTViOSTest() {

        if (dteIsEnabled) {

            splashChain.splashAtRest();
            alertschain.dismissAlerts();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.fullEpisodesBtn().waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.seriesTtl(seriesTitle).waitForPlayerLoad().waitForPresent();
            series.tapEpisodeLockBtn(episodeTitle);
            if (alerts.dontAllowBtn().waitForViewLoad().isVisible()) {
            	alerts.dontAllowBtn().waitForVisible().tap();
            } else if (selectProvider.backBtn().waitForViewLoad().isVisible() &&  !selectProvider.directTVProviderBtn().isVisible()) {
                selectProvider.backBtn().waitForVisible().tap();
            }
            selectProvider.directTVProviderBtn().waitForVisible().tap();
            if (TestRun.getLocale().equals("en_us")) {
                signIn.directTVUsernameTxb().waitForVisible().tap().type("vmn1_streamtest@vmn.com");
                keyboard.goToPasswordField();
                signIn.directTVPasswordTxb().waitForVisible().tap().type("vmnstream");
                signIn.directTVPasswordTxb().waitForVisible().tapOffSetElement(0, 50);
            } else {
                if (TestRun.getLocale().equals("en_us")) {
                    signIn.directTVUsernameTxb().waitForVisible().tap().type("vmn1_streamtest@vmn.com");
                    signIn.directTVPasswordTxb().hideKeyboard().waitForVisible().tap().type("vmnstream");
                    signIn.directTVSignInBtn().hideKeyboard().waitForVisible().tap();
                } else {
                    signIn.directTVUsernameTxb().waitForVisible().tap().type(username);
                    signIn.directTVPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
                    signIn.directTVSignInBtn().hideKeyboard().waitForVisible().tap();
                }
            }
            //signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "DirectTV is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }
}

