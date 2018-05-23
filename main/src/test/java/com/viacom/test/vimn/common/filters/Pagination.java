package com.viacom.test.vimn.common.filters;

public enum Pagination {

    PAGINATED ("Paginated"),
    NON_PAGINATED ("NonPaginated");

    private final String value;

    Pagination(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
