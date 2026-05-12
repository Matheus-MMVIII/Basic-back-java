package com.basic.common;

import com.github.f4b6a3.ksuid.KsuidCreator;

public final class Ids {

    private Ids() {
    }

    public static String newId() {
        return KsuidCreator.getKsuid().toString();
    }
}