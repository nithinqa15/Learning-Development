package com.viacom.test.vimn.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;

import com.viacom.test.core.driver.DriverManager;
import com.viacom.test.core.interact.MobileInteract;
import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.uitests.pageobjects.Keyboard;
import com.viacom.test.vimn.uitests.pageobjects.ProgressBar;
import com.viacom.test.vimn.uitests.pageobjects.Series;
import com.viacom.test.vimn.uitests.pageobjects.SignIn;
import com.viacom.test.vimn.uitests.pageobjects.VideoPlayer;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.netty.handler.timeout.TimeoutException;

public class Interact extends MobileInteract {

	private AppiumDriver<MobileElement> driver;
	private Integer yScreenHeight;
	private Integer xScreenWidth;

	public Interact(By locator, HashMap<String, String> locatorData) {
		super(locator, locatorData);
		driver = DriverManager.getAppiumDriver();

		yScreenHeight = this.driver.manage().window().getSize().getHeight();
		xScreenWidth = this.driver.manage().window().getSize().getWidth();
	}

	/**********************************************************************************************
	 * Waits for a view to be loaded before timing out.
	 *
	 * @author Brandon Clark created October 1, 2015
	 * @version 1.0 October 1, 2015
	 * @return Fluid instance of the Interact class
	 * @exception TimeoutException
	 *              - A view is not loaded within the given timeout period.
	 ***********************************************************************************************/
	public Interact waitForViewLoad() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.loadingSpinnerIcn().pause(StaticProps.MEDIUM_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	/**********************************************************************************************
	 * Waits for a player to be loaded before timing out.
	 *
	 * @author Vincent Barresi created February 2, 2017
	 * @version 1.0 February 2, 2017
	 * @return Fluid instance of the Interact class
	 * @exception TimeoutException
	 *              - A player is not loaded within the given timeout period.
	 ***********************************************************************************************/
	public Interact waitForPlayerLoad() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.videoProgressBarIcn().pause(StaticProps.MEDIUM_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	/**********************************************************************************************
	 * Waits for a view to be loaded before timing out.
	 *
	 * @author Vincent Barresi created November 18, 2016
	 * @version 1.0 November 18, 2016
	 * @return Fluid instance of the Interact class
	 * @exception TimeoutException
	 *              - Sign In view is not loaded within the given timeout period.
	 ***********************************************************************************************/
	public Interact waitForSignInPage() {
		SignIn signIn = new SignIn();
		signIn.pleaseWaitText().pause(StaticProps.SMALL_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	public Interact hideKeyboard() {
		if (TestRun.isAndroid()) {
			DriverManager.getAndroidDriver().hideKeyboard();
		} else {
			new Keyboard().doneBtn().waitForVisible().tap();
		}
		return this;
	}

	/**********************************************************************************************
	 * Scrolls up a specific number of scrolls relative to device's coordinates
	 * provided from the bottom of the screen
	 *
	 * @param numOfScrolls
	 *          - {@link Integer} - The number of scrolls up to execute.
	 * @author Gabriel Fioretti created November 28, 2016
	 * @version 1.0 November 28, 2016
	 * @return Fluid instance of the Interact class
	 ***********************************************************************************************/
	public Interact scrollUpClipsView(Integer numOfScrolls) {
		int scrollIter = 0;
		while (scrollIter < numOfScrolls) {
			Integer x = DriverManager.getAppiumDriver().manage().window().getSize().getWidth() / 2;
			Integer startY = DriverManager.getAppiumDriver().manage().window().getSize().getHeight() / 2;
			Integer endY = startY + DriverManager.getAppiumDriver().manage().window().getSize().getHeight() / 4;
			Logger.logMessage("Scroll up with starting coordinates " + x + "," + startY + " and ending coordinates " + x + ","
					+ endY + ".");
			if (TestRun.isAndroid()) {
				DriverManager.getAndroidDriver().swipe(x, startY, x, endY, 2);
			} else {
				DriverManager.getIOSDriver().executeScript("UIATarget.localTarget().dragFromToForDuration({x:" + x + ", " + "y:"
						+ startY + "}, {x:" + x + ", y:" + endY + "}, 1)");
			}
			scrollIter++;
		}
		return this;
	}

	public Interact waitForPlaylistLoad() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.loadingSpinnerPlaylistIcn().pause(StaticProps.MEDIUM_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	public Interact waitForClipsPlaylistLoad() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.loadingSpinnerClipsPlaylistIcn().pause(StaticProps.SMALL_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	public Interact waitForPosterSpinnerNotVisible() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.loadingSpinnerPosterIcn().pause(StaticProps.SMALL_PAUSE).waitForNotPresentOrVisible();
		return this;
	}

	@Override
	public MobileInteract waitForFlickedRightTo(Integer maxScrolls, Integer startXFromRight, Integer endXFromLeft) {
		Integer yStart = yScreenHeight / 2;
		Integer yEnd = yStart;
		Integer xStart = xScreenWidth * 9 / 10;
		Integer xEnd = xScreenWidth / 10;
		if (TestRun.isIos()) {
			int scrollIter = 0;
			while (scrollIter <= maxScrolls) {
				if (scrollIter == maxScrolls) {
					Assert.fail("Element not visible after '" + maxScrolls + "' flicks right.");
				}
				// this.flickRight(1, startXFromRight, endXFromLeft);
				this.scrollFromTo(xStart, yStart, xEnd, yEnd, 250);
				scrollIter++;
				if (this.isVisible().equals(true)) {
					break;
				}
			}
		} else {
			for (int scrollIter = 0; scrollIter <= maxScrolls && this.isVisible().equals(false); ++scrollIter) {
				if (scrollIter == maxScrolls) {
					Assert.fail("Element not visible after \'" + maxScrolls + "\' flicks right.");
				}
				// this.flickRight(1, startXFromRight, endXFromLeft);
				this.scrollFromTo(xStart, yStart, xEnd, yEnd, 250);
			}
		}
		return this;
	}

	public MobileInteract exposeControlRack() {
		VideoPlayer videoPlayer = new VideoPlayer();
		if (TestRun.isAndroid()) {
			if (!videoPlayer.maximizeBtn().isVisible()) {
				videoPlayer.playerCtr().waitForVisible().tapCenter();
			}
		} else {
			videoPlayer.playerCtr().waitForVisible().tap();
		}
		return this;
	}

	public MobileInteract exposeControlRackFullScreen() {
		new Interact(null, null).tap(xScreenWidth, yScreenHeight / 2);
		return this;
	}

	/**
	 * Method to scroll Episode list down on series view
	 */
	public MobileInteract scrollDownOnSeriesView(Integer scrollSpeed) {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;

		if (TestRun.isAndroid()) {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 3 / 4;
			endX = startX;
			endY = startY - 250;
		} else {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 3 / 4;
			endX = startX;
			endY = -250;
		}
		Logger.logMessage("Scroll down with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ startX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}

	public MobileInteract waitForScrolledDownTo(Integer maxScrolls, Integer scrollSpeed) {
		for (int scrollIter = 0; scrollIter <= maxScrolls && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == maxScrolls) {
				Assert.fail("Element not visible after \'" + maxScrolls + "\' scrolls down.");
			}
			this.scrollDownOnSeriesView(scrollSpeed);
		}
		return this;
	}

	/**
	 * Method to scroll Episode list up on series view
	 */
	public MobileInteract scrollUpOnSeriesView(Integer scrollSpeed) {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;
		if (TestRun.isAndroid()) {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 3 / 4;
			endX = startX;
			endY = startY + 250;
		} else {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 2 / 10;
			endX = startX;
			endY = yScreenHeight + 250;
		}
		Logger.logMessage("Scroll up with starting coordinates " + startX + "," + startY + " and ending coordinates " + endX
				+ "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}

	public MobileInteract waitForScrolledUpTo(Integer numOfScrolls, Integer scrollSpeed) {
		for (int scrollIter = 0; scrollIter <= numOfScrolls && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numOfScrolls) {
				Assert.fail("Element not visible after " + numOfScrolls + " scrolls");
			}
			this.scrollUpOnSeriesView(scrollSpeed);
		}
		return this;
	}

	/**
	 * Method to swipe right direction on Home View
	 */
	public MobileInteract flickCarouselRight() {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;

		if (TestRun.isAndroid()) {
			startX = xScreenWidth * 9 / 10;
			startY = yScreenHeight / 2;
			endX = xScreenWidth / 10;
			endY = startY;
		} else {
			startX = xScreenWidth * 9 / 10;
			startY = yScreenHeight / 2;
			endX = -xScreenWidth;
			endY = startY;
		}
		Logger.logMessage("Swiping right with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ endX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		return this;
	}

	public MobileInteract waitForCarouselFlickedRightTo(Integer numberOfFlicks) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.flickCarouselRight();
		}
		return this;
	}

	/**
	 * Method to swipe left direction on Home View
	 */
	public MobileInteract flickCarouselLeft() {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;
		if (TestRun.isIos()) {
			startX = xScreenWidth / 25;
			startY = yScreenHeight / 2;
			endX = xScreenWidth + 200;
			endY = startY;
		} else {
			startX = xScreenWidth / 10;
			startY = yScreenHeight / 2;
			endX = xScreenWidth * 9 / 10;
			endY = startY;
		}
		Logger.logMessage("Swiping left with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ endX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		return this;
	}

	public MobileInteract waitForCarouselFlickedLeftTo(Integer numberOfFlicks) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.flickCarouselLeft();
		}
		return this;
	}

	/**
	 * Method to scroll down on clips view, one video container at time
	 * 
	 * @param scrollSpeed
	 * @return Fluid instance of the Interact class
	 */
	public MobileInteract scrollDownOnClipsView(Integer scrollSpeed) {
		Integer startX = null;
		Integer startY = null;
		Integer endX = null;
		Integer endY = null;
		if (TestRun.isAndroid()) {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 3 / 4;
			endX = startX;
			endY = yScreenHeight / 2;
		} else {
			startX = xScreenWidth / 2;
			startY = yScreenHeight * 3 / 4;
			endX = startX;
			endY = -250;
		}
		Logger.logMessage("Scroll up with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ startX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}

	/**
	 * Method to repeatedly scroll down on clips view, one video container at
	 * time. Must input exact number of clips to flick down.
	 * 
	 * @param numberOfFlicks
	 * @param scrollSpeed
	 * @return
	 */
	public MobileInteract waitForScrolledDownToOnClipsView(Integer numberOfFlicks, Integer scrollSpeed) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.scrollDownOnClipsView(scrollSpeed);
		}
		return this;
	}

	public MobileInteract scrollUpOnClipsView(Integer scrollSpeed) {
		
		Integer	startX = xScreenWidth / 2;
		Integer	startY = yScreenHeight / 5;
		Integer	endX = startX;
		Integer	endY = yScreenHeight / 2;
		
		Logger.logMessage("Scroll down with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ startX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}

	public MobileInteract waitForScrolledUpToOnClipsView(Integer numberOfFlicks) {
		for (int i = 0; i < numberOfFlicks && !this.isVisible(); i++) {
			this.scrollUpOnClipsView(StaticProps.NORMAL_SCROLL);
		}
		if (!this.isVisible()) {
			Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
		}
		return this;
	}

	public MobileInteract flickToNextSeriesFromSeriesView() {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;

		if (TestRun.isAndroid()) {
			startX = xScreenWidth * 9 / 10;
			startY = yScreenHeight / 5;
			endX = xScreenWidth / 10;
			endY = startY;
		} else {
			startX = yScreenHeight / 2;
			startY = xScreenWidth * 9 / 10;
			endX = -yScreenHeight;
			endY = xScreenWidth / 10;
		}

		Logger.logMessage("Swiping left with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ endX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		return this;
	}

	public MobileInteract waitForFlickedToNextSeriesFromSeriesView(Integer numberOfFlicks) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.flickToNextSeriesFromSeriesView();
		}
		return this;
	}

	public MobileInteract flickToPreviousSeriesFromSeriesView() {
		Integer startX;
		Integer startY;
		Integer endX;
		Integer endY;

		if (TestRun.isAndroid()) {
			startX = xScreenWidth / 10;
			startY = yScreenHeight / 5;
			endX = xScreenWidth * 9 / 10;
			endY = startY;
		} else {
			startX = yScreenHeight / 7;
			startY = xScreenWidth * 9 / 10;
			endX = yScreenHeight + 100;
			endY = xScreenWidth / 10;
		}

		Logger.logMessage("Swiping right with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ endX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		return this;
	}

	public MobileInteract waitForFlickedToPreviousSeriesFromSeriesView(Integer numberOfFlicks) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.flickToPreviousSeriesFromSeriesView();
		}
		return this;
	}

	public MobileInteract flickSeasonSelectorRight() {
		Series series = new Series();
		MobileElement seasonSelectorContainer = series.seasonSelectorContainer().waitForVisible().getMobileElement();
		Integer startX = seasonSelectorContainer.getCenter().getX();
		Integer startY = seasonSelectorContainer.getCenter().getY();
		Integer endX;
		Integer endY = startY;

		if (TestRun.isAndroid()) {
			endX = startX / 10;
		} else {
			endX = -startX;
		}
		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		Logger.logMessage("Swiping right with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ endX + "," + endY + ".");
		return this;
	}

	public MobileInteract waitForSeasonSelectorFlickedRight(Integer numberOfFlicks) {
		for (int scrollIter = 0; scrollIter <= numberOfFlicks && this.isVisible().equals(false); ++scrollIter) {
			if (scrollIter == numberOfFlicks) {
				Assert.fail("Element not visible after " + numberOfFlicks + " flicks");
			}
			this.flickSeasonSelectorRight();
		}
		return this;
	}

	public MobileInteract swipeUpOnHomeView() {
		Integer startX = xScreenWidth / 2;
		Integer startY = yScreenHeight / 2;
		Integer endX = startX;
		Integer endY = xScreenWidth / 4;

		this.scroll(startX, startY, endX, endY, StaticProps.NORMAL_SCROLL);
		Logger.logMessage("Swiping up on home view with starting coordinates " + startX + "," + startY
				+ " and ending coordinates " + endX + "," + endY + ".");
		return this;
	}

	public MobileInteract scroll(Integer startX, Integer startY, Integer endX, Integer endY, Integer scrollSpeed) {
		if (TestRun.isAndroid()) {
			(new TouchAction(this.driver)).press(startX.intValue(), startY.intValue()).waitAction(scrollSpeed)
					.moveTo(endX.intValue(), endY.intValue()).release().perform();
		} else {
			(new TouchAction(this.driver)).press(startX.intValue(), startY.intValue())
					.moveTo(endX.intValue(), endY.intValue()).release().perform();
		}
		return this;
	}

	public MobileInteract longPress(Integer x, Integer y, Integer timeInMillis) {
		(new TouchAction(this.driver)).longPress(x, y, timeInMillis).release().perform();
		return this;
	}

	/**********************************************************************************************
	 * Will scroll down the clips view numOfFlicks times at the speed of
	 * scrollSpeed
	 * 
	 * @author Edward Poon created August 8, 2017
	 * @version 1.0 August 8, 2017
	 * @param numOfFlicks
	 * @param scrollSpeed
	 * @return the mobile interact that waitForScrollDownOnClipsView was called on
	 ***********************************************************************************************/
	public MobileInteract waitForScrollDownOnClipsView(Integer numOfFlicks) {
		for (int i = 0; i < numOfFlicks && !this.isVisible(); i++) {
			this.scrollDownOnClipsView(StaticProps.NORMAL_SCROLL);
		}
		if (!this.isVisible()) {
			Assert.fail("Element not visible after " + numOfFlicks + " flicks");
		}
		return this;
	}

	/**********************************************************************************************
	 * Will take screenshot of the calling interact object
	 * 
	 * @author Edward Poon created August 8, 2017
	 * @version 1.0 Sept 18, 2017
	 * @return the buffered image of the interact
	 ***********************************************************************************************/
	public BufferedImage takeScreenshotOfThisInteract() {
		Logger.logMessage("Taking screenshot of: " + this.getElementSimpleName());
		try {
			// Uses these values to crop the interact image
			int playerWidth = this.waitForVisible().getMobileElement().getSize().getWidth();
			int playerHeight = this.waitForVisible().getMobileElement().getSize().getHeight();
			Point playerCtrOrigin = this.waitForVisible().getMobileElement().getLocation();

			// Takes a screenshot and crops the interact
			BufferedImage img = ImageIO.read(takeScreenshot());
			BufferedImage croppedImg = img.getSubimage(playerCtrOrigin.getX(), playerCtrOrigin.getY(), playerWidth,
					playerHeight);
			return croppedImg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.logMessage("Failed to take screenshot of: " + this.getElementSimpleName());
		return null;
	}
	
	/**********************************************************************************************
	 * Compares the two screenshots and sees if they are equal
	 * 
	 * @author Edward Poon created August 8, 2017
	 * @version 1.0 Sept 18, 2017
	 * @return true if the screenshots are equal, false otherwise
	 ***********************************************************************************************/
	public static boolean areScreenshotsEqual(BufferedImage img1, BufferedImage img2) {
		if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if (img1.getRGB(x, y) != img2.getRGB(x, y))
						return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private File takeScreenshot() {
		Logger.logMessage("Take a screenshot of the entire screen");
		File screenshot = ((TakesScreenshot) DriverManager.getAppiumDriver()).getScreenshotAs(OutputType.FILE);
		return screenshot;
	}

	public Interact waitForVisible(){
		super.waitForVisible();
		return this;
	}

	public int getAreaOfThisInteract(){
		Dimension size = this.getMobileElement().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		return width * height;
	}
	
	/**
	 * Method to Open Device settings window for WiFi turn ON & OFF (Only for iOS)
	 */
	public MobileInteract openDeviceSettingWindow(Integer scrollSpeed) {

		Integer startX = xScreenWidth ;
		Integer startY = yScreenHeight ;
		Integer endX = startX;
		Integer endY = -250;
		
		Logger.logMessage("Scroll Up with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ startX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}
	
	/**
	 * Method to Close Device settings window for WiFi turn ON & OFF (Only for iOS)
	 */
	public MobileInteract closeDeviceSettingWindow(Integer scrollSpeed) {

		Integer startX = xScreenWidth / 2 ;
		Integer startY = yScreenHeight / 2 - 75;
		Integer endX = startX ;
		Integer endY = yScreenHeight;
		
		Logger.logMessage("Scroll Down with starting coordinates " + startX + "," + startY + " and ending coordinates "
				+ startX + "," + endY + ".");
		this.scroll(startX, startY, endX, endY, scrollSpeed);
		return this;
	}

	/**
	 * Determines if element is located at the bottom of the screen and is partially hidden. Useful on
	 * Clips View and recycle views in general where an element can be present and visible but out of
	 * reach for interaction.
	 * @return Boolean
	 */
	public boolean isPartiallyHidden() {
		int location = this.waitForPresent().getMobileElement().getLocation().getY();
		int height = this.waitForPresent().getMobileElement().getSize().height;
		int finalLocation = location + height;

		Logger.logMessage("Location of Element " + this.getElementSimpleName() + " on Y Axis: " + location);
		Logger.logMessage("Height of Element " + this.getElementSimpleName() + ": " + height);
		Logger.logMessage("Final Location of " + this.getElementSimpleName() + ": " + finalLocation);
		return yScreenHeight <= finalLocation;
	}
	
	/**
	 * Method to scroll on apptentive message window 
	 */

	public MobileInteract scrolledDownOnApptentiveMessageWindow(Integer maxScrolls, Integer scrollSpeed) {
			
			for (int scrollIter = 0; scrollIter <= maxScrolls && this.isVisible().equals(false); ++scrollIter) {
				if (scrollIter == maxScrolls) {
					Assert.fail("Element not visible after \'" + maxScrolls + "\' scrolls down.");
				}
				Integer startX = xScreenWidth / 2;
				Integer startY = yScreenHeight / 2;
				Integer endX = startX;
				Integer endY = -250;
		
				Logger.logMessage("Scroll down with starting coordinates " + startX + "," + startY + " and ending coordinates "
						+ startX + "," + endY + ".");
				this.scroll(startX, startY, endX, endY, scrollSpeed);
			}
		
		return this;
	}
}