package com.viacom.test.vimn.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.viacom.test.core.report.Emailer;
import com.viacom.test.core.report.HipChat;

import com.viacom.test.vimn.common.util.Config.ConfigProps;

public class AppLib {

    private static String[] includedGroup;

    public synchronized static void sendResultEmail(String jenkinsReportURL, Integer passCount, Integer failCount, Integer skipCount) {
        // get relevant email report data
        String applicationTitle = ConfigProps.APPLICATION_TITLE;
        String appName = ConfigProps.APP_NAME;
        String targetOS = ConfigProps.MOBILE_OS;
        String appVersion = TestRun.isAndroid() ? ConfigProps.ANDROID_APP_VERSION : ConfigProps.IOS_APP_VERSION;
        String appURL = ConfigProps.APP_URL;
        String appLocale = ConfigProps.LOCALE;

        // set the email subject
        String subject = "";
        if (!failCount.equals(0)) {
            subject = applicationTitle + " - " + "App Version: " + appVersion + " - Test Automation Report - Failed";
        } else {
            subject = applicationTitle + " - " + "App Version: " + appVersion + " - Test Automation Report - Passed";
        }

        // set the email body
        String reportAttachmentTxt = "The test report was not uploaded.";
        if (ConfigProps.UPLOAD_REPORT_JENKINS && jenkinsReportURL != null) {
            reportAttachmentTxt = "A detailed functional report is available: <a href='" + jenkinsReportURL
                    + "'>report</a>";
        }
        String messageContent = "<body>Test run complete:"
                + "<br /><br />Application = " + applicationTitle
                + "<br />App Brand = " + appName
                + "<br />App Locale = " + appLocale
                + "<br />TargetOS = " + targetOS
                + "<br />App Version = " + appVersion
                + "<br />App URL = " + appURL
                + "<br />Device Categories = [Phone, Tablet]"
                + "<br />Tests passed = " + passCount.toString()
                + "<br />Tests skipped = " + skipCount.toString()
                + "<br />Tests failed = " + failCount.toString()
                + "<br />Build Server = " + HipChatRoom.getBuildServer()
                + "<br /><br />" + reportAttachmentTxt
                + "<br /><br />QA Automation</body>";

        // send the email report
        Emailer.sendEmail(ConfigProps.EMAIL_SENDER_ADDRESS, ConfigProps.SEND_REPORT_EMAIL_ADDRESS, subject, messageContent);
    }

    public synchronized static void sendResultHipChat(String jenkinsReportURL, Integer passCount, Integer failCount, Integer skipCount) {
        // set the chat subject and color
        String subject = "";
        String color = "";
        Integer totalTests = passCount + failCount + skipCount;
        if (failCount >= passCount) {
            color = "red";
        } else if (failCount < passCount && failCount != 0) {
            color = "yellow";
        } else if (failCount == 0) {
            color = "green";
        }

        // set the chat html message
        String message = subject
                + "<br>App:&nbsp;&nbsp;" + ConfigProps.APP_NAME
                + ", Locale:&nbsp;&nbsp;" + ConfigProps.LOCALE
                + "<br>App Version:&nbsp;&nbsp;" + TestRun.getAppVersion()
                + ", API Version:&nbsp;&nbsp;" + ConfigProps.API_VERSION
                + ", Content Domain:&nbsp;&nbsp;" + ConfigProps.CONTENT_DOMAIN
                + ", <a href='" + ConfigProps.APP_URL + "'>App build URL</a>"
                + "<br>Tests Passed:&nbsp;&nbsp;" + passCount.toString()
                + ", Tests Skipped:&nbsp;&nbsp;" + skipCount.toString()
                + ", Tests Failed:&nbsp;&nbsp;" + failCount.toString()
                + ", Total Tests:&nbsp;&nbsp;" + totalTests
                + "<br>Build Server:&nbsp;&nbsp;" + HipChatRoom.getBuildServer()
                + "<br><a href='" + jenkinsReportURL + "'>Test Report</a>";
        
        // send the chat to all the chat rooms
        List<String> postRooms = new ArrayList<String>();
        //postRooms.add(ConfigProps.DTE_CHAT_WEBHOOK_URL);
        if (TestRun.isAndroid()) {
            postRooms.add(ConfigProps.ANDROID_CHAT_WEBHOOK_URL);
        } else {
            postRooms.add(ConfigProps.IOS_CHAT_WEBHOOK_URL);
        }
        for (String postRoom : postRooms) {
            HipChat.sendChat(postRoom, message, color);
        }
    }

    public synchronized static void sendFailedInstallHipChat() {
        // set the chat subject and color
        String subject = "<strong>Test Automation Report - App Install Failure</strong>";
        String color = "yellow";

        // set the chat message indicating an install failure
        String message = subject
                + "<br>App:&nbsp;&nbsp;" + ConfigProps.APP_NAME
                + ", Locale:&nbsp;&nbsp;" + ConfigProps.LOCALE
                + ", Build:&nbsp;&nbsp;" + ConfigProps.BUILD_NUMBER
                + ", <a href='" + ConfigProps.APP_URL + "'>App Package</a>"
                + "<br>The app failed to install successfully. Is this app package valid?";

        // send the chat to all the chat rooms
        List<String> postRooms = new ArrayList<String>();
        postRooms.add(ConfigProps.DTE_CHAT_WEBHOOK_URL);
        if (TestRun.isAndroid()) {
            postRooms.add(ConfigProps.ANDROID_CHAT_WEBHOOK_URL);
        } else {
            postRooms.add(ConfigProps.IOS_CHAT_WEBHOOK_URL);
        }
        for (String postRoom : postRooms) {
            HipChat.sendChat(postRoom, message, color);
        }
    }

    @Deprecated
    public synchronized static void sendResultSlack(String jenkinsReportURL, Integer passCount,
                                                    Integer failCount, Integer skipCount) {

        String colour = "";
        String status = "";
        if (failCount >= passCount) {
            colour = "#ff0000";
            status = "Broken :hankey:";
        } else if (failCount < passCount && failCount != 0) {
            colour = "#ff9400";
            status = "Unstable :doge:";
        } else if (failCount == 0) {
            colour = "#02ce35";
            status = "Passed :conga_parrot:";
        }

        String title;
        if (TestRun.isAndroid()) {
            title = "PlayPlex Android Test Report";
        } else {
            title = "PlayPlex iOS Test Report";
        }

        Integer totalTests = passCount + failCount + skipCount;

        String payload = "{\n" +
                "    \"attachments\": [\n" +
                "        {\n" +
                "            \"fallback\": \"" + title + "\",\n" +
                "            \"color\": \"" + colour + "\",\n" +
                "            \"title\": \"" + title + "\",\n" +
                "            \"text\": \"<" + jenkinsReportURL + "|Click here> for a detailed report!\",\n" +
                "            \"fields\": [ {\n" +
                "                    \"title\": \"Brand\",\n" +
                "                    \"value\": \"" + ConfigProps.APP_NAME + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"API Version\",\n" +
                "                    \"value\": \"" + ConfigProps.API_VERSION + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"App Version\",\n" +
                "                    \"value\": \"" + TestRun.getAppVersion() + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Test Group\",\n" +
                "                    \"value\": \"" + Arrays.toString(includedGroup) + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Locale\",\n" +
                "                    \"value\": \"" + ConfigProps.LOCALE + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Status\",\n" +
                "                    \"value\": \"" + status + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Passed\",\n" +
                "                    \"value\": \"" + passCount.toString() + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Broken\",\n" +
                "                    \"value\": \"" + failCount.toString() + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Skipped\",\n" +
                "                    \"value\": \"" + skipCount.toString() + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"Total Tests\",\n" +
                "                    \"value\": \"" + totalTests.toString() + "\",\n" +
                "                    \"short\": true\n" +
                "                },\n" +
                "            ],\n" +
                "            \"footer\": \"Brought to you by PlayPlex Automation\",\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        Slack.sendChat(payload);
    }

    public static int safeLongToInt(long longNumber) {
        if (longNumber < Integer.MIN_VALUE || longNumber > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException(longNumber + " cannot be cast to int without changing its value.");
        }
        return (int) longNumber;
    }

    public static synchronized void setIncludedGroup(String[] includedGroup) {
        AppLib.includedGroup = includedGroup;
    }
}
