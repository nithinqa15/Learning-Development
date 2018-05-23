package com.viacom.test.vimn.common.fluentmapbuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FluentMapBuilder implements VariableBuilder<String, String>, ValueBuilder<String, String>, MapBuilder<String, String> {

    private final List<Map.Entry<String, String>> entries;
    private String lastKey;
    private Map<String, String> map;

    public FluentMapBuilder() {
        this.entries = new ArrayList<>();
    }

    /**
     * Set key to map that represents the variable to be tested.
     * @param variable
     * @return fluid instance of ValueBuilder
     */
    @Override
    public ValueBuilder<String, String> variable(String variable) {
        lastKey = variable;
        return this;
    }

    /**
     * Creates a new entry with last variable added and respective value attributed
     * @param value
     * @return fluid instance of VariableBuilder
     */
    @Override
    public VariableBuilder<String, String> value(String value) {
        entries.add(new AbstractMap.SimpleEntry<>(lastKey, value));
        return this;
    }

    /**
     * Set and return final map of variables and values
     */
    @Override
    public Map<String, String> build() {
        setMap();
        return map;
    }

    /**
     * Set final map and return fluid instance of MapBuilder
     * @return
     */
    @Override
    public MapBuilder<String, String> mapBuilder() {
        setMap();
        return this;
    }

    /**
     * Merge two maps
     * @param secondMap - map to be merged
     * @return final map merged
     */
    @Override
    public Map<String, String> plus(Map<String, String> secondMap) {
        return Stream.of(map, secondMap)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    /**
     * Logic to collect all entries and return as a map.
     */
    private void setMap() {
        map = entries.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
