package com.viacom.test.vimn.common.util;

import com.viacom.test.core.util.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import static com.viacom.test.vimn.common.util.Config.ConfigProps.*;

@Deprecated
public class Slack {

    public static void sendChat(String payload) {
        String failureMsg = "Failed to send test report to Slack Room";
        String successMsg = "Successfully sent test report to Slack Room.";
        String slackChatURL = getChatWebHook();

        try {
            CloseableHttpClient e = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(slackChatURL);
            StringEntity entity = new StringEntity(payload);
            request.addHeader("content-type", "application/json");
            request.setEntity(entity);
            HttpResponse response = e.execute(request);
            if(response.getStatusLine().getStatusCode() != 200) {
                Logger.logConsoleMessage(failureMsg);
                Logger.logConsoleMessage(response.getStatusLine().getReasonPhrase());
            } else {
                Logger.logConsoleMessage(successMsg);
            }
        } catch (Exception var9) {
            Logger.logConsoleMessage(failureMsg);
            var9.printStackTrace();
        }
    }

    public static String getChatWebHook() {
        return TestRun.isAndroid() ? PLAYPLEX_ANDROID_SLACK_WEBHOOK : PLAYPLEX_IOS_SLACK_WEBHOOK;
    }
}