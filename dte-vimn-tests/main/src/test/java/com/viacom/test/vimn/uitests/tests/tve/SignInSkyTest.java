package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.GroupProps;
import com.viacom.test.vimn.common.util.Config.ParamProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInSkyTest extends TveBaseTest {

    //Declare data
    Boolean skyIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInSkyTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.SKY);
        password = ProviderManager.getProviderPassword(StaticProps.SKY);
        skyIsEnabled = providerEnabled.isSkyEnabled();

    }

    @TestCaseId("35101")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.BRAZIL })
    public void signInSkyAndroidTest() {

        if (skyIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.tapEpisodeLockBtn(episodeTitle);
            selectProvider.skyProviderBtn().waitForVisible().tap();
            signIn.skyUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.skyPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.skySignInBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoPlaying(ConfigProps.VIDEO_IMAGE_TIMEOUT);
            loginChain.logoutIfLoggedIn();
        } else {

            String message = "Sky is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }

    }

    @TestCaseId("35101")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.BRAZIL })
    public void signInSkyiOSTest() {

        if (skyIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.episodeLockBtn(episodeTitle).waitForVisible().tap();
            if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.skyProviderBtn().isVisible()) {
                selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
            }
            selectProvider.skyProviderBtn().waitForVisible().tap();
            if (com.viacom.test.core.util.TestRun.isXCUITest()) {
                signIn.skyUsernameTxb().waitForVisible().tap().type(username);
                keyboard.goToPasswordField();
                signIn.skyPasswordTxb().waitForVisible().tap().type(password);
                signIn.skySignInBtn().waitForVisible().tap();
            } else {
                signIn.skyUsernameTxb().waitForVisible().tap().type(username);
                signIn.skyPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
                signIn.skySignInBtn().hideKeyboard().waitForVisible().tap();
            }
            signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {

            String message = "Sky is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

}
