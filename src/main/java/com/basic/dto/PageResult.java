package com.basic.dto;

import java.util.List;

public class PageResult<T> {
    private List<T> data;
    private Long nextCursor;

    public PageResult(List<T> data, Long nextCursor) {
        this.data = data;
        this.nextCursor = nextCursor;
    }

    public List<T> getData() { return data; }
    public Long getNextCursor() { return nextCursor; }
}