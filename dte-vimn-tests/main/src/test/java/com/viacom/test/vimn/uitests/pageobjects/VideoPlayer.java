package com.viacom.test.vimn.uitests.pageobjects;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.sikuli.script.Finder;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.SkipException;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.sikuli.SikuliUtil;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.exceptions.ContentException;
import com.viacom.test.vimn.common.exceptions.InternetConnectionException;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.ConfigProps;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.Interact;
import com.viacom.test.vimn.common.util.Locator;
import com.viacom.test.vimn.common.util.TestRun;

import javax.imageio.ImageIO;

public class VideoPlayer {

    private Locator locator;

    // PAGE OBJECT CONSTRUCTOR
    public VideoPlayer() {
        locator = new Locator();
    }

    static String VALUE = Config.AttributeProps.VALUE;

    // PAGE OBJECT IDENTIFIERS
    public Interact playerCtr() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact fullscreenPlayerCtr() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact largePlayBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact smallPlayBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact smallPauseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact fullscreenSmallPlayBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact fullscreenSmallPauseBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact playCtrBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact progressTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact progressSpinner()  {
    	return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact durationTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact scrubberBar() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact maximizeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact minimizeBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact playFromBeginningBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact airPlayBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact contentNotAvailableIcn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact contentNotAvailableTxt() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact liveTVPlayer() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact slowInternetConnectionTxt() {
    	return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact retryBtn() {
    	return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact captionsBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact videoTitle() {
    		return new Interact(locator.getLocator(), locator.locatorData);
    }

    public Interact signInBtn() {
        return new Interact(locator.getLocator(), locator.locatorData);
    }

    // PAGE OBJECT METHODS
    public void verifyVideoNotPlaying(Integer timeout) {
    	Logger.logMessage("Verify a video is NOT playing.");
    	imagePause();
    	SikuliUtil.waitForScreenComparison(timeout, true, ConfigProps.SCREENSHOT_FILE_PATH);
    }

    public void verifyVideoPlaying(Integer timeout) {
    	Logger.logMessage("Verify a video is playing.");
    	imagePause();
    	SikuliUtil.waitForScreenComparison(timeout, false, ConfigProps.SCREENSHOT_FILE_PATH);
    }

    private void imagePause() {
    	try {
			Thread.sleep(StaticProps.XLARGE_PAUSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void verifyVideoIsPlaying(Integer timeoutInSec) {
        Logger.logMessage("Verify a video is playing.");

        String filePath1 = "";
        String filePath2 = "";
        String time1 = "";
        String time2 = "";

        try {

            //get the current device screenshot
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMddyyhhmmssa");
            String screenshotDateTime1 = dateTimeFormat.format(new Date());
            filePath1 = ConfigProps.SCREENSHOT_FILE_PATH + screenshotDateTime1 + ".png";
            FileUtils.copyFile(((TakesScreenshot) DriverManager.getAppiumDriver()).getScreenshotAs(OutputType.FILE),
                    new File(filePath1));

            playerCtr().waitForPresent().tapCenter();
            time1 = progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE);


            for (int i = 0; i <= timeoutInSec / 2; i++)
            {
                Thread.sleep((2000));
                Logger.logMessage("Video Playing, Current time " + progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE));
            }

            //get the current device screenshot
            String screenshotDateTime2 = dateTimeFormat.format(new Date());
            filePath2 = ConfigProps.SCREENSHOT_FILE_PATH + screenshotDateTime2 + ".png";
            FileUtils.copyFile(((TakesScreenshot) DriverManager.getAppiumDriver()).getScreenshotAs(OutputType.FILE),
                    new File(filePath2));

            playerCtr().waitForPresent().tapCenter();
            time2 = durationTxt().waitForPresent().getMobileElement().getAttribute(VALUE);

        } catch (Exception e) {
            Logger.logMessage("Failed to compare video screenshots " + e);
            Assert.fail("Failed to compare video screenshots.", e);
        }

        Finder finder = null;
        try {
            finder = new Finder(filePath1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = new Pattern(filePath2);
        finder.find(pattern.exact());
        if (finder.hasNext()) {
            Logger.logMessage("Screenshots are equal. Video is not playing.");
            Assert.fail("Screenshots are equal. Video is not playing.");
        }

        Assert.assertNotEquals(time1, time2, "Video timer is not incrementally increasing. Video is not playing.");
    }

    @SuppressWarnings("unused")
    public void verifyVideoIsNotPlaying(Integer timeoutInSec) {
        Logger.logMessage("Verify a video is not playing.");

        String filePath1 = "";
        String filePath2 = "";
        String time1 = "";
        String time2 = "";

        try {

            //get the current device screenshot
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMddyyhhmmssa");
            String screenshotDateTime1 = dateTimeFormat.format(new Date());
            filePath1 = ConfigProps.SCREENSHOT_FILE_PATH + screenshotDateTime1 + ".png";
            FileUtils.copyFile(((TakesScreenshot) DriverManager.getAppiumDriver()).getScreenshotAs(OutputType.FILE),
                    new File(filePath1));

            playerCtr().waitForPresent().tapCenter();
            time1 = progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE);

            //execute a paused delay
            for (int i = 0; i <= timeoutInSec / 2; i++)
            {
                Thread.sleep((2000));
                Logger.logMessage("Video not playing, Current time " + progressTxt().waitForPresent().getMobileElement().getText());
            }

            //get the current device screenshot
            String screenshotDateTime2 = dateTimeFormat.format(new Date());
            filePath2 = ConfigProps.SCREENSHOT_FILE_PATH + screenshotDateTime2 + ".png";
            FileUtils.copyFile(((TakesScreenshot) DriverManager.getAppiumDriver()).getScreenshotAs(OutputType.FILE),
                    new File(filePath2));

            playerCtr().waitForPresent().tapCenter();
            time2 = progressTxt().waitForPresent().getMobileElement().getAttribute(VALUE);

        } catch (Exception e) {
            Logger.logMessage("Failed to compare video screenshots " + e);
            Assert.fail("Failed to compare video screenshots.", e);
        }

        Finder finder = null;
        try {
            finder = new Finder(filePath1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = new Pattern(filePath2);
        finder.find(pattern.exact());
        if (!finder.hasNext()) {
            Logger.logMessage("Screenshots are equal. Video is not playing.");
        }

        Assert.assertEquals(time1, time2, "Video timer is not incrementally increasing. Video is not playing.");
    }

    public void verifyVideoIsPlayingByScreenshots(Integer timeoutInSec) {
        Logger.logMessage("Verify a video is playing.");
        try {
            Thread.sleep(timeoutInSec * 2000);
            byte[] firstScreenshot = DriverManager.getAppiumDriver().getScreenshotAs(OutputType.BYTES);
            BufferedImage firstImage = ImageIO.read(new ByteArrayInputStream(firstScreenshot));
            Thread.sleep(timeoutInSec * 2000);
            byte[] secondScreenshot = DriverManager.getAppiumDriver().getScreenshotAs(OutputType.BYTES);
            BufferedImage secondImage = ImageIO.read(new ByteArrayInputStream(secondScreenshot));
            Finder finder = new Finder(firstImage);
            Pattern pattern = new Pattern(secondImage);
            finder.find(pattern.exact());
            if (finder.hasNext()) {
                Logger.logMessage("Screenshots are equal. Video is not playing.");
                Assert.fail("Screenshots are equal. Video is not playing.");
            } else {
                Logger.logMessage("Screenshots are not equal. Video is playing.");
            }
        } catch (AssertionError | Exception e) {
            if (slowInternetConnectionTxt().isVisible()) {
                String message = "Screenshots are equal. Video is not playing due to internet connection issues.";
                throw new InternetConnectionException(message);
            } else if (contentNotAvailableTxt().isVisible()) {
                String message = "Screenshots are equal. Video is not playing because episode was not found" +
                        " or is no longer available.";
                throw new ContentException(message);
            } else {
                Assert.fail("Screenshots are equal. Video is not playing.");
            }
        }
    }

    @SuppressWarnings("unused")
    public void verifyVideoIsNotPlayingByScreenshots(Integer timeoutInSec) {
        Logger.logMessage("Verify a video is not playing.");
        try {
            Thread.sleep(timeoutInSec * 2000);
            byte[] firstScreenshot = DriverManager.getAppiumDriver().getScreenshotAs(OutputType.BYTES);
            BufferedImage firstImage = ImageIO.read(new ByteArrayInputStream(firstScreenshot));
            Thread.sleep(timeoutInSec * 2000);
            byte[] secondScreenshot = DriverManager.getAppiumDriver().getScreenshotAs(OutputType.BYTES);
            BufferedImage secondImage = ImageIO.read(new ByteArrayInputStream(secondScreenshot));
            Finder finder = new Finder(firstImage);
            Pattern pattern = new Pattern(secondImage);
            finder.find(pattern.exact());
            if (!finder.hasNext()) {
                Logger.logMessage("Screenshots are not equal. Video is playing.");
                Assert.fail("Screenshots are not equal. Video is playing.");
            } else {
                Logger.logMessage("Screenshots are equal. Video is not playing.");
            }
        } catch (AssertionError | Exception e) {
            if (slowInternetConnectionTxt().isVisible()) {
                String message = "Screenshots are equal. Video is not playing due to internet connection issues.";
                throw new InternetConnectionException(message);
            } else if (contentNotAvailableTxt().isVisible()) {
                String message = "Screenshots are equal. Video is not playing because episode was not found" +
                        " or is no longer available.";
                throw new ContentException(message);
            } else {
                Assert.fail("Screenshots are not equal. Video is playing.");
            }
        }
    }
    
	public Integer getDurationTxtInSeconds() {
		String durationText = durationTxt().waitForVisible().getMobileElement().getText();
		if (durationText.contains(":")) {
			Integer durationTimeInSeconds = Integer.parseInt(durationText.substring(0, durationText.indexOf(':'))) * 60
					+ Integer.parseInt(durationText.substring(durationText.indexOf(':') + 1, durationText.length()));
			return durationTimeInSeconds;
		} else {
			return Integer.parseInt(durationText);
		}
	}

	public Integer getProgressTxtInSeconds() {
		String progressText = progressTxt().waitForVisible().getMobileElement().getText();
		if (progressText.contains(":")) {
			Integer progressTimeInSeconds = Integer.parseInt(progressText.substring(0, progressText.indexOf(':'))) * 60
					+ Integer.parseInt(progressText.substring(progressText.indexOf(':') + 1, progressText.length()));
			return progressTimeInSeconds;
		} else {
			return Integer.parseInt(progressText);
		}
	}
	
	public Integer getProgressTxtInPercentage() {
		String progressText = progressTxt().waitForPresent().getMobileElement().getText();
		if (progressText.contains("%")) {
			Integer progressTimeInSeconds = Integer.parseInt(progressText.split("%")[0].trim());
			return progressTimeInSeconds;
		} else {
			return Integer.parseInt(progressText);
		}
	}

	public void getToEndOfVideo() {
		if (TestRun.isAndroid()) {
			playerCtr().exposeControlRack();

			// Gets the coordinates for the right edge of the scrubber and creates a point
			Point beginningOfScrubber = scrubberBar().waitForVisible().getMobileElement().getLocation();
			Point durationTxtPoint = durationTxt().waitForVisible().getMobileElement().getLocation();
			Integer xCoordForScrubber = durationTxtPoint.x - 100;
			Integer yCoordForScrubber = beginningOfScrubber.y;
			Point rightEdgeOfScrubber = new Point(xCoordForScrubber, yCoordForScrubber);

			// Tap on the right edge of the scrubber
			this.scrubberBar().waitForVisible().tap(rightEdgeOfScrubber.x, rightEdgeOfScrubber.y);
		} else if (TestRun.isIos()) {
			throw new SkipException("Skipping test: Get to end of video is not supported for iOS " + TestRun.getAppName());
		}
	}

    public void getToEndOfVideoUntilLocked() {
        Integer count = 0;
        while (!signInBtn().isVisible()) {
            Logger.logMessage("Video is unlocked. Scrubbing to the next video.");
            getToEndOfVideo();
            // Skips test if there are no locked videos after five attempts
            if (count > 5) {
                throw new SkipException("Could not find a locked episode after five attempts. Skipping test.");
            }
            count++;
        }
    }
	
	public void verifyEpisodeIsPlaying(String episodeTitle) {
        verifyVideoIsPlayingByScreenshots(5);
        playerCtr().exposeControlRack();
        Logger.logMessage("Is " + episodeTitle + " playing?");
        videoTitle().waitForVisible().getMobileElement().getText()
                .contains(episodeTitle);
    }
	
	public void playerLoad(Integer timeOut) {
		largePlayBtn().waitForPlayerLoad().pause(timeOut);
	}

}
