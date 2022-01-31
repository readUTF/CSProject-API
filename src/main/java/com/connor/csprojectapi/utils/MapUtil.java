package com.connor.csprojectapi.utils;

import java.util.HashMap;

public class MapUtil<X,Y> {

    HashMap<X, Y> map;

    public MapUtil(X key, Y value) {
        this.map = new HashMap<>();
        this.map.put(key, value);
    }

    public MapUtil<X, Y> add(X key, Y value) {
        this.map.put(key, value);
        return this;
    }

    public HashMap<X, Y> build() {
        return map;
    }
}
