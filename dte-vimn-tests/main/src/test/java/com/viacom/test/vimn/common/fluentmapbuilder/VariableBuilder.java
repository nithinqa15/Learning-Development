package com.viacom.test.vimn.common.fluentmapbuilder;

import java.util.Map;

public interface VariableBuilder<K, V> {

    ValueBuilder<K, V> variable(K variableName);
    
    Map<K, V> build();

    MapBuilder<K, V> mapBuilder();
}
