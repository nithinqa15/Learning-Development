package com.viacom.test.vimn.uitests.tests.tve;

import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.TveBaseTest;
import com.viacom.test.vimn.common.exceptions.ProviderException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.support.DataProviderManager;
import com.viacom.test.vimn.uitests.support.LocaleDataFactory;
import com.viacom.test.vimn.uitests.support.ProviderManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.TestCaseId;

public class SignInSingtelTest extends TveBaseTest {

    Boolean singtelIsEnabled;

    @Factory(dataProvider = StaticProps.DEFAULT_DATA_PROVIDER, dataProviderClass = DataProviderManager.class)
    public SignInSingtelTest(String runParams) {super.setRunParams(runParams);}

    @BeforeMethod(alwaysRun = true)
    public void setupTest() {

        if (seriesTitle == null) {
            super.tveSetupTest();
        }

        username = ProviderManager.getProviderUsername(StaticProps.SINGTEL);
        password = ProviderManager.getProviderPassword(StaticProps.SINGTEL);
        singtelIsEnabled = providerEnabled.isSingtelEnabled();

    }

    // GF 18.11.2016 - Leaving as broken until we have feeds working and a build that can actually run this test
    @TestCaseId("35086")
    @Features(Config.GroupProps.TVE)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.TVE })
    @Parameters({ Config.ParamProps.ANDROID, Config.ParamProps.CC_INTL_APP, Config.ParamProps.MTV_INTL_APP, Config.ParamProps.SINGAPORE })
    public void signInSingtelAndroidTest() {

        if (singtelIsEnabled) {

            splashChain.splashAtRest();
            loginChain.logoutIfLoggedIn();
            autoPlayChain.enableAutoPlay();

            home.enterSeriesView(seriesTitle, numberOfSwipes);
            selectSeasonChain.navigateToSeason(episodeSeasonNumber);
            series.tapEpisodeLockBtn(episodeTitle);
            signIn.goBackToProviders();
            selectProvider.singtelProviderBtn().waitForVisible().tap();
            signIn.singtelUsernameTxb().waitForSignInPage().waitForVisible().tap().type(username).closeKeyboard();
            signIn.singtelPasswordTxb().waitForVisible().tap().type(password).closeKeyboard();
            signIn.singtelSignInBtn().waitForVisible().tap();
            signIn.waitForSigningInText();
            signIn.successTxt().waitForVisible().waitForNotPresent();
            series.waitForEpisodeLoad(episodeTitle);
            videoPlayer.verifyVideoIsPlayingByScreenshots(5);
        } else {
            String message = "Singtel is not a provider in " + LocaleDataFactory.getCountryName() +
                    " so skipping test";
            Logger.logMessage(message);
            throw new ProviderException(message);
        }

    }

    @TestCaseId("35086")
    @Features(Config.GroupProps.TVE)
    @Test(groups = { Config.GroupProps.BROKEN, Config.GroupProps.TVE  })
    @Parameters({ Config.ParamProps.IOS, Config.ParamProps.CC_INTL_APP, Config.ParamProps.MTV_INTL_APP, Config.ParamProps.SINGAPORE })
    public void signInSingteliOSTest() {

        // TO-DO

    }

}
