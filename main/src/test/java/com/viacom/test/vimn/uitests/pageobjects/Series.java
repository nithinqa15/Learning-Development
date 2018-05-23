package com.viacom.test.vimn.uitests.pageobjects;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.testng.Assert;

import com.viacom.test.core.util.Logger;
import com.viacom.test.core.util.TestRun;
import com.viacom.test.vimn.common.exceptions.ContentException;
import com.viacom.test.vimn.common.exceptions.InternetConnectionException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;

public class Series {

    private Locator locator;
    private VideoPlayer videoPlayer;

    // PAGE OBJECT CONSTRUCTOR
    public Series() {
        locator = new Locator();
        videoPlayer = new VideoPlayer();
    }

    // PAGE OBJECT IDENTIFIERS
    public Interact seriesTtl(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }

    public Interact seriesCloseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact episodeTtl(String title) {
        return new Interact(locator.getLocator(title), locator.locatorData);
    }
    
    public Interact episodeDescriptionTxt(String description) {
        return new Interact(locator.getLocator(description), locator.locatorData);
    }

    public Interact episodeDescriptionTxt(String episodeTtl, String description) {
        return new Interact(locator.getLocator(episodeTtl, description), locator.locatorData);
    }
    
    public Interact episodePauseBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }
    
    public Interact episodePlayBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }
    
    public Interact episodeLockBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }

    public Interact noThanksBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }

    public Interact remindMeLaterBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }

    public Interact rateBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }

    public Interact allSeasonsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonOneBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonTwoBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonThreeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonFourBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonFiveBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonSixBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonSevenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonEightBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonNineBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonTenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonElevenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonTwelveBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonThirteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonFourteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonFifteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonSixteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonSeventeenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonEighteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonNineteenBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonTwentyBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact watchXtrasBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact fullEpisodesBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact shareBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact seasonSelectorContainer() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact progressBarIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact episodeLoadingSpinnerBtn(String episodeTitle) {
        return new Interact(locator.getLocator(episodeTitle), locator.locatorData);
    }

    public Interact backBtn() {
      return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact movieBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact trailerBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }
    
    public Interact movieMetaDataText(String movieMetaData) {
        return new Interact(locator.getLocator(movieMetaData), locator.locatorData);
    }

    public Interact specialBtn() {
    	return new Interact(locator.getLocator(),locator.locatorData); 
    }

    /**********************************************************************************************
     * ANDROID ONLY
     * Locks the device for a duration using adb.
     *
     * @author Vincent Barresi created September 27, 2016
     * @version 1.0 September 27, 2016
     * @param duration - {@link Integer} - The duration to lock the screen (seconds).
     ***********************************************************************************************/
    public void lockDevice(Integer duration) {
        if (TestRun.isAndroid()) {
            Logger.logMessage("Lock the device for '" + duration + "' second(s).");
            Runtime run = Runtime.getRuntime();
            try {
                // press power button
                Process process = run.exec("adb shell input keyevent 26");
                process.waitFor();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                Thread.sleep(duration * 1000);
                // unlock device
                process = run.exec("adb shell input keyevent WAKEUP");
                process.waitFor();
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // do nothing
        }
    }

    public void scrollUpToSeriesTitle(String seriesTitle) {
        if (!seriesTtl(seriesTitle).waitForPlaylistLoad().isVisible()) {
            seriesTtl(seriesTitle).waitForScrolledUpTo(15, Config.StaticProps.FAST_SCROLL);
        }
    }

    public void scrollUpToSeriesTitle(String seriesTitle, Integer numberOfScrolls) {
        if (!seriesTtl(seriesTitle).waitForPlaylistLoad().isVisible()) {
            seriesTtl(seriesTitle).waitForScrolledUpTo(numberOfScrolls, Config.StaticProps.FAST_SCROLL);
        }
    }

    public void switchToFullEpisodes() {
        if (fullEpisodesBtn().waitForPlaylistLoad().isVisible()) {
            fullEpisodesBtn().waitForPresent().tap();
        }
    }

    public void switchToClips() {
        if (watchXtrasBtn().waitForPlaylistLoad().isVisible()) {
            watchXtrasBtn().waitForPresent().tap();
        }
    }

    public void verifySeriesHasNoClipsTab() {
        if (watchXtrasBtn().isVisible() || watchXtrasBtn().isPresent()) {
            Assert.fail("Clips tab is present or visible.");
        } else {
            Logger.logMessage("Test Passed: Clips tab is not present as expected.");
        }
    }

    public void scrollDownToEpisodeTtl(String episodeTtl) {
        episodeTtl(episodeTtl).waitForScrolledDownTo(30, StaticProps.NORMAL_SCROLL);
    }

	public void tapEpisodePlayBtn(String episodeTtl) {
		if (!episodePlayBtn(episodeTtl).isVisible() && !episodePauseBtn(episodeTtl).isVisible()) {
			scrollDownToEpisodeTtl(episodeTtl);
		}
		if (episodePlayBtn(episodeTtl).isVisible()) {
			episodePlayBtn(episodeTtl).waitForVisible().tap().pause(StaticProps.MEDIUM_PAUSE);
			episodePauseBtn(episodeTtl).waitForVisible();
		}
	}

    public void tapEpisodePauseBtn(String episodeTtl) {
        if (!episodePauseBtn(episodeTtl).isVisible()) {
            scrollDownToEpisodeTtl(episodeTtl);
        }
        episodePauseBtn(episodeTtl).waitForVisible().tap();
        episodePlayBtn(episodeTtl).waitForVisible();
    }

    public void tapEpisodeLockBtn(String episodeTtl) {
        if (!episodeLockBtn(episodeTtl).isVisible()) {
            scrollDownToEpisodeTtl(episodeTtl);
        }
        episodeLockBtn(episodeTtl).waitForVisible().tap().waitForNotPresentOrVisible();
    }

    public void verifyEpisodeDescriptionIsVisible(String episodeTitle, String episodeDescription) {
        episodeDescriptionTxt(episodeDescription).waitForScrolledDownTo(1, Config.StaticProps.NORMAL_SCROLL);
    }

    public void tapEpisodeTitle(String episodeTitle) {
        if (!episodeTtl(episodeTitle).isVisible()) {
            scrollDownToEpisodeTtl(episodeTitle);
        }
        episodeTtl(episodeTitle).waitForVisible().tap();
    }

    public void verifyEpisodesOrder(List<String> episodesTitles) {
        for (String episodeTitle : episodesTitles) {
            if (TestRun.isIos()) {
                if (!episodeTtl(episodeTitle).isVisible() && !episodeTitle.contains("'")) {
                    scrollDownToEpisodeTtl(episodeTitle);
                }
            } else {
                if (!episodeTtl(episodeTitle).isVisible()) {
                    scrollDownToEpisodeTtl(episodeTitle);
                }
            }
        }
    }

    public void waitForEpisodeLoad(String episodeTitle) {
        if (episodeLoadingSpinnerBtn(episodeTitle).isVisible() && TestRun.isAndroid()) {
            episodeLoadingSpinnerBtn(episodeTitle).waitForNotPresentOrVisible();
        }
        if (videoPlayer.contentNotAvailableTxt().isVisible()) {
            String message = "The episode " + episodeTitle + " was not found or is no longer available. " +
                    "This could be a content issue.";
            throw new ContentException(message);
        } else if (videoPlayer.slowInternetConnectionTxt().isVisible()) {
            String message = "There's something wrong with the internet connection.";
            throw new InternetConnectionException(message);
        }
    }
    

    /**********************************************************************************************
  	 * Will flick to the next series and the next until the series with the title
  	 * of targetPropertyTitle is found. If it reaches the end without finding the
  	 * title, it will fail
  	 *
  	 * @author Edward Poon created August 2, 2017
  	 * @version 1.0 August 2, 2017
  	 ***********************************************************************************************/
    public void flickToNextSeriesUntilSeriesTitleIsVisible(String targetPropertyTitle, int numOfFlicks) {
  		for (int i = 0; i < numOfFlicks; i++) {
  			backBtn().flickToNextSeriesFromSeriesView();
  			backBtn().waitForVisible();
  		}
  		scrollUpToSeriesTitle(targetPropertyTitle);
  	}

    public void goBack() {
        backBtn().waitForPresent().tap();
    }

}
