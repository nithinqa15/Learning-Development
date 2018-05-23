package com.viacom.test.vimn.common.fluentmapbuilder;

public interface ValueBuilder<K, V> {

    VariableBuilder<K, V> value(V expectedValue);

}
