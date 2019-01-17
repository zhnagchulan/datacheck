package com.newcore.devtool.dao.api;

import com.newcore.devtool.models.DictInfo;

import java.util.List;

/**
 * Created by maeag on 2016/4/19.
 */
public interface DictionaryDao {

    /**
     * 根据字典名称，查询字典列表信息
     *
     * @param dictName 字典名称
     * @return
     */
    public List<DictInfo> getDictInfoList(String dictName);

    /**
     * 查询字典名称列表
     *
     * @return
     */
    public List<String> getDictNameList();
}
