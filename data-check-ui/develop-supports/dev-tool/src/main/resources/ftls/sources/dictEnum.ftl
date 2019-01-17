package com.${source.system}.datacheck.dicts;

import java.util.Arrays;
import java.util.Optional;

public enum ${source.dictName?trim} {

    <#list source.dictItems as item>
    ${item.keyName?trim}("${item.key?trim}", "${item.description?trim}")<#if item_has_next>,<#else>;</#if>
    </#list>

    private String _key;

    private String _description;

    ${source.dictName?trim}(String key, String description) {
        _key = key;
        _description = description;
    }

    public String getKey() {
        return _key;
    }

    public String getDescription() {
        return _description;
    }

    public static ${source.dictName?trim} valueOfKey(String key) {
        Optional<${source.dictName?trim}> optionalValue = Arrays.asList(${source.dictName?trim}.values()).stream()
                .filter(item -> item.getKey().equals(key)).findFirst();
        if (optionalValue.isPresent())
            return optionalValue.get();
        throw new RuntimeException("Can't find enum ${source.dictName?trim} for key " + key);
    }
}
