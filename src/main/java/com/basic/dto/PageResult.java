package com.basic.dto;

import java.util.List;

public class PageResult<T> {
    private List<T> data;
    private String nextCursor;

    public PageResult(List<T> data, String nextCursor) {
        this.data = data;
        this.nextCursor = nextCursor;
    }

    public List<T> getData() { return data; }
    public String getNextCursor() { return nextCursor; }
}