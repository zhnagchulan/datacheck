package com.newcore.devtool.dao.impl;

import com.halo.core.dao.support.CustomBeanPropertyRowMapper;
import com.newcore.devtool.dao.api.DictionaryDao;
import com.newcore.devtool.models.DictInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maeag on 2016/4/19.
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl implements DictionaryDao {

    /**
     * 日志管理工具实例.
     */
    private static Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);

    private static final String GET_DICTINFO_LIST = "SELECT DICT_CODE as DICT_NAME,FIELD_CODE as KEY_NAME, DICT_VALUE as `KEY`, DESCRIPTION FROM DICT_DETAIL where DICT_CODE = ? order by dict_order";

    private static final String GET_DICTNAME_LIST = "SELECT DICT_CODE FROM DICT_INFO s where s.TO_JAR = 'Y'";

    /**
     * JDBC操作工具
     */
    @Autowired
    private JdbcOperations jdbcTemplate;

    /**
     * 根据字典名称，查询字典列表信息
     *
     * @param dictName
     * @return
     */
    @Override
    public List<DictInfo> getDictInfoList(String dictName) {
        return jdbcTemplate.query(GET_DICTINFO_LIST, new CustomBeanPropertyRowMapper<>(DictInfo.class), dictName);
    }

    /**
     * 查询字典名称列表
     *
     * @return
     */
    @Override
    public List<String> getDictNameList() {
        return jdbcTemplate.queryForList(GET_DICTNAME_LIST, String.class);
    }

}
