package com.viacom.test.vimn.uitests.support;

import com.viacom.test.vimn.common.util.Config;
import com.viacom.test.vimn.common.util.Config.StaticProps;
import com.viacom.test.vimn.common.util.TestRun;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class BrandDataFactory {

    private static String locale;
    private static JSONObject brandObj;

    public BrandDataFactory() {
        String dataConfigFile = Config.ConfigProps.BRAND_DATA_FILE_PATH;
        String brand = TestRun.getAppName();
        locale = TestRun.getLocale();

        try {
            JSONParser parser = new JSONParser();
            if (!dataConfigFile.isEmpty()) {
                FileReader reader = new FileReader(dataConfigFile);
                Object obj = parser.parse(reader);
                JSONObject json = (JSONObject) obj;
                brandObj = (JSONObject) json.get(brand);
                reader.close();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAppName() {
        return (String) brandObj.get(StaticProps.APPNAME);
    }

    public static String getAppContactSupportToEmail() {
        JSONObject appContactSupportToEmailObj = (JSONObject) brandObj.get(StaticProps.APPSUPPORTCONTACTEMAIL);
        return (String) appContactSupportToEmailObj.get(locale);
    }

    public static String getOmnitureAppId() {
        JSONObject omnitureAppIdObj = (JSONObject) brandObj.get(StaticProps.OMNITUREAPPID);
        return (String) omnitureAppIdObj.get("Dev");
    }

    public static String getOmnitureAppName() {
        return (String) brandObj.get(StaticProps.OMNITUREAPPNAME);
    }

    public static String getOmnitureBrandId() {
        return (String) brandObj.get(StaticProps.OMNITUREBRANDID);
    }

    public static String getDomain() {
        return (String) brandObj.get(StaticProps.DOMAIN);
    }

    public static String getImageDomain() {
        JSONObject imageDomainObj = (JSONObject) brandObj.get(StaticProps.IMAGEDOMAIN);
        return (String) imageDomainObj.get(locale);
    }

    public static boolean hasLiveTvEnabled() {
        return Boolean.parseBoolean((String) brandObj.get(StaticProps.LIVETV));
    }

    public static String getChannel() {
        return (String) brandObj.get(StaticProps.CHANNEL);
    }

    public static String getVidOwner() {
        return (String) brandObj.get(StaticProps.VIDOWNER);
    }

    public static String getPlayerMgid() {
        return (String) brandObj.get(StaticProps.PLAYERMGID);
    }

    public static String getPlayerMgidReport() {
        return (String) brandObj.get(StaticProps.PLAYERMGIDREPORT);
    }

}
