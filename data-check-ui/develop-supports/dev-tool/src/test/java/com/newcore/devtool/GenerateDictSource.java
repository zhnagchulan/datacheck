package com.newcore.devtool;

import com.newcore.devtool.dao.api.DictionaryDao;
import com.newcore.devtool.models.DictInfo;
import com.newcore.devtool.utils.ConfigUtils;
import com.newcore.devtool.generators.DictionaryGenerator;

import org.apache.commons.lang3.BooleanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 从IPBPS的数据库中读取字典内容，并生成数据字典jar.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-core.xml", "/dao/applicationContext-dao.xml"})
public class GenerateDictSource {

    @Resource
    DictionaryDao dictionaryDao;

    @Test
    public void generateDictSourceFile() {
        List<String> dictNameList = dictionaryDao.getDictNameList();
        boolean replace=BooleanUtils.toBoolean(ConfigUtils.getProperty("source.file.replace"));
        for (String dictName : dictNameList) {
            List<DictInfo> dictInfoList = dictionaryDao.getDictInfoList(dictName);
            DictionaryGenerator.generateInstanceFile(dictName, dictInfoList,replace);
        }
    }
}
