package com.chinalife.datacheck.dicts;

import java.util.Arrays;
import java.util.Optional;

public enum SEX {

    MALE("1", "男"),
    FEMALE("2", "女"),
    UNKNOWN("3", "未知");

    private String _key;

    private String _description;

    SEX(String key, String description) {
        _key = key;
        _description = description;
    }

    public String getKey() {
        return _key;
    }

    public String getDescription() {
        return _description;
    }

    public static SEX valueOfKey(String key) {
        Optional<SEX> optionalValue = Arrays.asList(SEX.values()).stream()
                .filter(item -> item.getKey().equals(key)).findFirst();
        if (optionalValue.isPresent())
            return optionalValue.get();
        throw new RuntimeException("Can't find enum SEX for key " + key);
    }
}
