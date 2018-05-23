package com.viacom.test.vimn.common.util;

import com.viacom.test.core.report.HipChat;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

import static com.viacom.test.vimn.common.util.Config.ConfigProps.*;

public class HipChatRoom {

    public static void sendChat(String includedGroups) {
    	
    	String hipChatURL = getHipChatWebHook();
        String subject = "";
        String color = "green";
        String message = subject
        		+ "New test execution has started on:"
                + "<br>App:&nbsp;&nbsp;" + ConfigProps.APP_NAME
                + "<br>Locale:&nbsp;&nbsp;" + ConfigProps.LOCALE
                + "<br>App Version:&nbsp;&nbsp;" + TestRun.getAppVersion()
                + "<br>Test Group:&nbsp;&nbsp;" + includedGroups
                + "<br>Build Server:&nbsp;&nbsp;" + getBuildServer();
          
            HipChat.sendChat(hipChatURL, message, color);
        }

    private static String getHipChatWebHook() {
        return TestRun.isAndroid() ? ANDROID_CHAT_WEBHOOK_URL : IOS_CHAT_WEBHOOK_URL;
    }

    public static String getBuildServer() {
        return ConfigProps.APP_URL.contains("stage") ? "stage.build.viacom.com" : "build.viacom.com";
    }
}