package com.viacom.test.vimn.common.fluentmapbuilder;

import java.util.Map;

public interface MapBuilder<K, V> {

    Map<K, V> plus(Map<K, V> secondMap);

}
