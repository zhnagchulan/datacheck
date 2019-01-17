package com.newcore.devtool.generators;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.newcore.devtool.models.DictInfo;
import com.newcore.devtool.utils.ConfigUtils;
import com.newcore.devtool.utils.FileUtils;
import com.newcore.devtool.utils.TemplateUtils;

import freemarker.template.Template;

/**
 * Created by maeag on 2016/4/19.
 */
public class DictionaryGenerator {

    /**
     * 生成一个字典文件
     *
     * @param dictName
     * @param dictInfoList
     * @author maeagle
     * @date 2016-1-21 13 :16:17
     */
    public static void generateInstanceFile(String dictName, List<DictInfo> dictInfoList,boolean replace) {
        Assert.notEmpty(dictInfoList, dictName + " is null!");
        Map<String, Object> rootMap = new HashMap<>();
        Map<String, Object> sourceMap = new HashMap<>();
        rootMap.put("source", sourceMap);
        sourceMap.put("system", ConfigUtils.getProperty("common.system"));
        sourceMap.put("dictItems", dictInfoList);
        sourceMap.put("dictName", dictName);
        Template template = TemplateUtils.getTemplate("sources/dictEnum.ftl");
        Writer out = null;
        try {
        	File file=FileUtils.buildDictSourceFile(dictName);
        	if(replace||!file.exists()) {
        		out = new OutputStreamWriter(new FileOutputStream(file));
        		template.process(rootMap, out);
        		out.close();
        	}
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
            }
        }
    }
}
