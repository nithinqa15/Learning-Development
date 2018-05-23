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
import com.viacom.test.vimn.common.util.TestRun;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInBlueToGoTest extends TveBaseTest {

    //Declare data
    Boolean blueToGoIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInBlueToGoTest(String runParams) {
        super.setRunParams(runParams);
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.BLUE_TO_GO);
        password = ProviderManager.getProviderPassword(StaticProps.BLUE_TO_GO);
        blueToGoIsEnabled = providerEnabled.isBlueToGoEnabled();

    }

    @TestCaseId("35090")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.BROKEN, GroupProps.TVE })
    @Parameters({ ParamProps.ANDROID, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.MEXICO })
    public void signInBlueToGoAndroidTest() {

        splashChain.splashAtRest();
        loginChain.logoutIfLoggedIn();
        autoPlayChain.enableAutoPlay();

        home.enterSeriesView(seriesTitle, numberOfSwipes);
        selectSeasonChain.navigateToSeason(episodeSeasonNumber);
        series.scrollUpToSeriesTitle(seriesTitle);
        series.tapEpisodeLockBtn(episodeTitle);
        signIn.goBackToProviders();
        selectProvider.blueToGoProviderBtn().waitForVisible().tap();
        signIn.blueToGoUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username);
        signIn.blueToGoPasswordTxb().hideKeyboard().waitForVisible().tap().type(password);
        signIn.blueToGoSignInBtn().hideKeyboard().waitForVisible().tap();
        signIn.waitForSigningInText();
        signIn.successTxt().waitForVisible().waitForNotPresent();
        series.waitForEpisodeLoad(episodeTitle);
        videoPlayer.verifyVideoIsPlayingByScreenshots(5);

    }

    @TestCaseId("35090")
    @Features(GroupProps.TVE)
    @Test(groups = { GroupProps.FULL, GroupProps.TVE })
    @Parameters({ ParamProps.IOS, ParamProps.MTV_INTL_APP, ParamProps.CC_INTL_APP, ParamProps.MEXICO })
    public void signInBlueToGoiOSTest() {


        if (blueToGoIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.seriesThumbBtn(seriesTitle).waitForVisible().tap();
            series.seriesTtl(seriesTitle).waitForViewLoad().waitForPresent();
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.scrollDownToEpisodeTtl(episodeTitle);
            series.episodeLockBtn(episodeTitle).waitForVisible().tap();
            if (selectProvider.fromFranchiseBackBtn().waitForViewLoad().isVisible() && !selectProvider.blueToGoProviderBtn().isVisible()) {
                selectProvider.fromFranchiseBackBtn().waitForVisible().tap();
            }
            selectProvider.blueToGoProviderBtn().waitForVisible().tap();
            signIn.blueToGoUsernameTxb().waitForVisible().tap().type("tlbxauthnauthz@test.com");
            keyboard.goToPasswordField();
            signIn.blueToGoPasswordTxb().waitForVisible().tap().type("testtlbx02");
            signIn.blueToGoSignInBtn().waitForVisible().tap();

            if (signIn.blueToGoDeviceLimitTxt().isVisible()) {
                signIn.blueToGoCloseAllSessionsBtn().waitForScrolledTo().tap().waitForNotPresentOrVisible();
            }
            //signIn.successTxt().waitForPresent().waitForNotPresent();
            series.episodePauseBtn(episodeTitle).waitForVisible();
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);

        } else {
            String message = "Blue To Go is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }
    }

}
