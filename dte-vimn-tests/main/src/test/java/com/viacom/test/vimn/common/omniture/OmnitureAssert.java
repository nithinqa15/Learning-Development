package com.viacom.test.vimn.common.omniture;

import com.viacom.test.core.util.Logger;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import java.util.Map;

public class OmnitureAssert extends Assertion {

    // LinkedHashMap to preserve the order
    private Map<AssertionError, IAssert> m_errors = Maps.newLinkedHashMap();

    @Override
    public void executeAssert(IAssert a) {
        try {
            Logger.logMessage("Expected Value: " + a.getExpected() + " || Actual Value: "+ a.getActual());
            a.doAssert();
        } catch(AssertionError ex) {
            onAssertFailure(a, ex);
            m_errors.put(ex, a);
        }
    }

    public void assertAll() {
        if (!m_errors.isEmpty()) {
            StringBuilder sb = new StringBuilder("The following values failed:\n");
            boolean first = true;
            for (Map.Entry<AssertionError, IAssert> ae : m_errors.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    sb.append("\n");
                }
                sb.append(ae.getKey().getMessage());
            }
            throw new AssertionError(sb.toString());
        } else {
            Logger.logMessage("Test Successfully Completed: All Actual Values Matched With Expected Values");
        }
    }
}
