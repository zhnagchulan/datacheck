package com.newcore.devtool.models;

import java.io.Serializable;

/**
 * 系统数据字典内容.
 *
 * @author maeagle
 * @date 2016-4-19 9:13
 */
public class DictInfo implements Serializable {

    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典Key的名称
     */
    private String keyName;
    /**
     * 字典Key
     */
    private String key;
    /**
     * 字典值
     */
    private String description;

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
