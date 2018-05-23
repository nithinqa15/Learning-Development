package com.viacom.test.vimn.uitests.actionchains;


import com.viacom.test.core.util.Logger;
import com.viacom.test.vimn.uitests.pageobjects.Series;

public class SelectSeasonChain {

    private Series series;

    public SelectSeasonChain() {
        series = new Series();
    }

    // ACTION CHAIN METHODS
    public void navigateToSeason(Integer seasonNumber) {

        if (seasonNumber == null) {
            Logger.logMessage("Season Number is null. There is no season to navigate to.");
        } else if (series.allSeasonsBtn().waitForPlaylistLoad().isVisible()) {
            switch (seasonNumber) {
                case 1:
                    if (!series.seasonOneBtn().isPresent()) {
                        series.seasonOneBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonOneBtn().waitForVisible().tap();
                    }
                    break;
                case 2:
                    if (!series.seasonTwoBtn().isPresent()) {
                        series.seasonTwoBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonTwoBtn().waitForVisible().tap();
                    }
                    break;
                case 3:
                    if (!series.seasonThreeBtn().isPresent()) {
                        series.seasonThreeBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonThreeBtn().waitForVisible().tap();
                    }
                    break;
                case 4:
                    if (!series.seasonFourBtn().isPresent()) {
                        series.seasonFourBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonFourBtn().waitForVisible().tap();
                    }
                    break;
                case 5:
                    if (!series.seasonFiveBtn().isPresent()) {
                        series.seasonFiveBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonFiveBtn().waitForVisible().tap();
                    }
                    break;
                case 6:
                    if (!series.seasonSixBtn().isPresent()) {
                        series.seasonSixBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonSixBtn().waitForVisible().tap();
                    }
                    break;
                case 7:
                    if (!series.seasonSevenBtn().isPresent()) {
                        series.seasonSevenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonSevenBtn().waitForVisible().tap();
                    }
                    break;
                case 8:
                    if (!series.seasonEightBtn().isPresent()) {
                        series.seasonEightBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonEightBtn().waitForVisible().tap();
                    }
                    break;
                case 9:
                    if (!series.seasonNineBtn().isPresent()) {
                        series.seasonNineBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonNineBtn().waitForVisible().tap();
                    }
                    break;
                case 10:
                    if (!series.seasonTenBtn().isPresent()) {
                        series.seasonTenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonTenBtn().waitForVisible().tap();
                    }
                    break;
                case 11:
                    if (!series.seasonElevenBtn().isPresent()) {
                        series.seasonElevenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonElevenBtn().waitForVisible().tap();
                    }
                    break;
                case 12:
                    if (!series.seasonTwelveBtn().isPresent()) {
                        series.seasonTwelveBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonTwelveBtn().waitForVisible().tap();
                    }
                    break;
                case 13:
                    if (!series.seasonThirteenBtn().isPresent()) {
                        series.seasonThirteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonThirteenBtn().waitForVisible().tap();
                    }
                    break;
                case 14:
                    if (!series.seasonFourteenBtn().isPresent()) {
                        series.seasonFourteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonFourteenBtn().waitForVisible().tap();
                    }
                    break;
                case 15:
                    if (!series.seasonFifteenBtn().isPresent()) {
                        series.seasonFifteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonFifteenBtn().waitForVisible().tap();
                    }
                    break;
                case 16:
                    if (!series.seasonSixteenBtn().isPresent()) {
                        series.seasonSixteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonSixteenBtn().waitForVisible().tap();
                    }
                    break;
                case 17:
                    if (!series.seasonSeventeenBtn().isPresent()) {
                        series.seasonSeventeenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonSeventeenBtn().waitForVisible().tap();
                    }
                    break;
                case 18:
                    if (!series.seasonEighteenBtn().isPresent()) {
                        series.seasonEighteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonEighteenBtn().waitForVisible().tap();
                    }
                    break;
                case 19:
                    if (!series.seasonNineteenBtn().isPresent()) {
                        series.seasonNineteenBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonNineteenBtn().waitForVisible().tap();
                    }
                    break;
                case 20:
                    if (!series.seasonTwentyBtn().isPresent()) {
                        series.seasonTwentyBtn().waitForSeasonSelectorFlickedRight(20).tap();
                    } else {
                        series.seasonTwentyBtn().waitForVisible().tap();
                    }
                    break;
                }
            }
        }

    }
