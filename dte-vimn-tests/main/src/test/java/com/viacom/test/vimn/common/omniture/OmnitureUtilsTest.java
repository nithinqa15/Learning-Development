package com.viacom.test.vimn.common.omniture;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class OmnitureUtilsTest {

    SoftAssert softAssert;

    @Test
    public void verifyGetHourDMethod() {
        softAssert = new SoftAssert();

        String dateWithDayWithOnlyOneDigit = "Mon Oct  9 14:07:21 CEST 2017";
        softAssert.assertEquals(OmnitureUtils.getHourD(dateWithDayWithOnlyOneDigit), "14");

        String dateWithDayWithTwoDigits = "Tue Oct 10 11:46:23 CEST 2017";
        softAssert.assertEquals(OmnitureUtils.getHourD(dateWithDayWithTwoDigits), "11");

        softAssert.assertAll();
    }

}
