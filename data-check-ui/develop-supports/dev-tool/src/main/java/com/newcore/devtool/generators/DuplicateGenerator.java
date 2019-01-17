package com.newcore.devtool.generators;

import com.newcore.devtool.utils.FileUtils;
import com.newcore.devtool.utils.ConfigUtils;
import com.newcore.devtool.utils.TemplateUtils;
import freemarker.template.Template;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成各个工程的配置文件.
 *
 * @author maeagle
 * @date 2016-1-21 13 :16:16
 */
public class DuplicateGenerator {

    /**
     * 生成某个工程下全部Module的pom文件.
     *
     * @param fileType the file type
     * @author maeagle
     * @date 2016-1-21 13 :16:16
     */
    public static void generateFiles(String fileType) {

        String[] projectNames = ConfigUtils.getProperty("common.projects").split(",");
        Assert.notEmpty(projectNames);

        String[] moduleNames = ConfigUtils.getProperty("common.modules").split(",");
        Assert.notEmpty(moduleNames);
        for (String projectName : projectNames) {
            for (String moduleName : moduleNames) {

                // parent,model,service-api 两个module没有如下配置文件
                if ("parent,model,service-api".contains(moduleName)) {
                    if (fileType.indexOf("applicationContext") != -1
                            || fileType.indexOf("logback") != -1
                            || fileType.indexOf("jetty") != -1
                            || fileType.indexOf("web") != -1
                            || fileType.indexOf("weblogic") != -1
                            || fileType.indexOf("wsconfig") != -1
                            || fileType.indexOf("jdbc") != -1
                            || fileType.indexOf("filestore") != -1
                            || fileType.indexOf("cache") != -1
                            || fileType.indexOf("profile") != -1
                            || fileType.indexOf("mqconfig") != -1
                            || fileType.indexOf("dasc") != -1
                            || fileType.indexOf("zkconfig") != -1)
                        continue;
                }
                // web-app平台下没有dasc文件
                if (fileType.indexOf("dasc") != -1 && "web-app".equals(moduleName))
                    continue;
                // web-app平台下没有jdbc文件
                if (fileType.indexOf("jdbc") != -1 && "web-app".equals(moduleName))
                    continue;
                // 只有web-app平台下有session_test文件
                if (fileType.indexOf("session_test") != -1 && !"web-app".equals(moduleName))
                    continue;
                // 只有web-app平台下有jettyWebConfig文件
                if (fileType.indexOf("jettyWebConfig") != -1 && !"web-app".equals(moduleName))
                    continue;
                // common平台下没有batch-app工程
                if ("common".equals(projectName) && "batch-app".equals(moduleName))
                    continue;
                generateInstanceFile(projectName, moduleName, "duplicates/" + fileType + ".ftl", FileUtils.buildFile(projectName, moduleName, fileType));
            }
        }
    }

    /**
     * Build root map map.
     *
     * @param projectName the project name
     * @param moduleName  the module name
     * @return the map
     * @author maeagle
     * @date 2016-1-21 13 :16:17
     */
    private static Map<String, Object> buildRootMap(String projectName, String moduleName) {
        Map<String, Object> rootMap = new HashMap<>();
        Map<String, String> duplicateMap = new HashMap<>();
        rootMap.put("duplicate", duplicateMap);
        duplicateMap.put("system", ConfigUtils.getProperty("common.system"));
        duplicateMap.put("name", projectName);
        duplicateMap.put("module", moduleName);
        duplicateMap.put("appname", moduleName.replace("-", ""));
        duplicateMap.put("version", ConfigUtils.getProperty("duplicate.version"));
        duplicateMap.put("cxf", ConfigUtils.getProperty("duplicate.service.cxf"));
        duplicateMap.put("dubbo", ConfigUtils.getProperty("duplicate.service.dubbo"));
        duplicateMap.put("cache", ConfigUtils.getProperty("duplicate.cache"));
        duplicateMap.put("description", projectName + "-" + moduleName);
        return rootMap;
    }

    /**
     * Generate instance file.
     *
     * @param projectName  the project name
     * @param moduleName   the module name
     * @param templateName the template name
     * @param targetFile   the target file
     * @author maeagle
     * @date 2016-1-21 13 :16:17
     */
    private static void generateInstanceFile(String projectName, String moduleName, String templateName, File targetFile) {
        Map<String, Object> rootMap = buildRootMap(projectName, moduleName);
        Template template = TemplateUtils.getTemplate(templateName);

        FileUtils.backupFile(targetFile);
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(targetFile));
            template.process(rootMap, out);
            out.close();
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
