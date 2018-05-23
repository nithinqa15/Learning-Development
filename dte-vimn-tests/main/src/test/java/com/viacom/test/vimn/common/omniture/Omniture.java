package com.viacom.test.vimn.common.omniture;

import com.viacom.test.core.util.Logger;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarPostDataParam;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class Omniture {

    public static void assertOmnitureValues(Map<String, String> expectedMap, Map<String, String> actualMap) {
        OmnitureAssert omnitureAssert = new OmnitureAssert();
        for (String key : expectedMap.keySet()) {
            Logger.logMessage("Testing the variable: " + key);
            omnitureAssert.assertEquals(actualMap.get(key), expectedMap.get(key), "Key:[" + key + "]");
        }
        omnitureAssert.assertAll();
    }

    public static void assertCallIsNotPresent(String param, List<HarEntry> omnitureCalls) {
        SoftAssert softAssert = new SoftAssert();
        for (HarEntry call : omnitureCalls) {
            if (call.getRequest().getPostData() != null) {
                List<HarPostDataParam> dataParams = call.getRequest().getPostData().getParams();
                dataParams.forEach((data) -> softAssert.assertTrue(!data.getValue().contains(param)));
            }
        }
        softAssert.assertAll();
    }
}