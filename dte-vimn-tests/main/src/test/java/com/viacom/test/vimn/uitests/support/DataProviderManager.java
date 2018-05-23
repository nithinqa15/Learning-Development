package com.viacom.test.vimn.uitests.support;

import org.testng.annotations.DataProvider;

import com.viacom.test.core.props.DeviceCategory;
import com.viacom.test.vimn.common.util.Config.ConfigProps;

public class DataProviderManager {

	@DataProvider()
    static public Object[][] defaultDataProvider() {
		if (ConfigProps.RUN_AS_FACTORY) {
			return new Object[][] {
			new Object[] { DeviceCategory.PHONE.value() },
			new Object[] { DeviceCategory.TABLET.value() },	
			};
		} else {
			return new Object[][] {
		            new Object[] { ConfigProps.DEVICE_CATEGORY }
		    };
		}
    }
    
}
