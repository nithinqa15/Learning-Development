package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.vimn.common.TveBaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.viacom.test.core.driver.DriverManager;
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

public class SignInTelecentroTest extends TveBaseTest {

    Boolean telecentroIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInTelecentroTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.TELECENTRO);
        password = ProviderManager.getProviderPassword(StaticProps.TELECENTRO);
        telecentroIsEnabled = providerEnabled.isTelecentroEnabled();

    }

    @TestCaseId("35091")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.ARGENTINA })
    public void signInTelecentroAndroidTest() {

        if (telecentroIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollUpToSeriesTitle(seriesTitle);
            series.tapEpisodeLockBtn(episodeTitle);
            selectProvider.telecentroProviderBtn().waitForVisible().tap();
            signIn.telecentroUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.telecentroPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.telecentroSignInBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Telecentro is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

    @TestCaseId("35091")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE  })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.ARGENTINA })
    public void signInTelecentroiOSTest() {

        if (telecentroIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.episodeLockBtn(seriesTitle).waitForScrolledTo().tap();
            if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible()) {
                selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
            }
            Logger.logMessage(DriverManager.getAppiumDriver().getPageSource());
            selectProvider.telecentroProviderBtn().waitForVisible().tap().pause(15000);
            signIn.telecentroUsernameTxb().waitForVisible().tap().type(username);
            keyboard.goToPasswordField();
            signIn.telecentroPasswordTxb().waitForVisible().tap().type(password);
            signIn.telecentroSignInBtn().hideKeyboard().waitForVisible().tap();
            //signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(seriesTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Telecentro is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

}
