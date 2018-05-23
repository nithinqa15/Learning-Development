package com.viacom.test.vimn.common.tve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viacom.test.vimn.common.proxy.ProxyLogUtils;
import com.viacom.test.vimn.common.proxy.ProxyUtils;
import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.JsonUtils;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccessToken {
    @JsonProperty("accessTokenTTL")
    private int accessTokenTTL;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
    @JsonProperty("refreshTokenTTL")
    private int refreshTokenTTL;
    @JsonProperty("mvpdId")
    private String mvpdId;
    @JsonProperty("function")
    private String function;
    @JsonProperty("status")
    private String status;

    public static GetAccessToken getConfiguration() {
        ProxyLogUtils.waitForResponse(Config.StaticProps.XBOX_ACCESS_TOKEN, 3);
        String response = ProxyLogUtils.getResponse(Config.StaticProps.XBOX_ACCESS_TOKEN);
        return JsonUtils.fromJson(response, GetAccessToken.class);
    }

    public static void decreaseAccessTokenTTL() {
        ProxyUtils.rewriteResponse(Config.StaticProps.XBOX_ACCESS_TOKEN, "(\"accessTokenTTL\":\\d+})", "\"accessTokenTTL\":30}");
    }

    public int getAccessTokenTTL() {
        return accessTokenTTL;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getRefreshTokenTTL() {
        return refreshTokenTTL;
    }

    public String getMvpdId() {
        return mvpdId;
    }

    public String getFunction() {
        return function;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "GetAccessToken{" +
                "accessTokenTTL=" + accessTokenTTL +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenTTL=" + refreshTokenTTL +
                ", mvpdId='" + mvpdId + '\'' +
                ", function='" + function + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}



