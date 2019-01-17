package com.newcore.devtool.utils;

import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具.
 *
 * @author maeagle
 * @date 2016-1-21 13 :23:30
 */
public class FileUtils {

    /**
     * 创建文件夹.
     *
     * @param dir the dir
     * @author maeagle
     * @date 2016-1-21 13 :23:30
     */
    public static void createDirs(String dir) {
        File dirFile = new File(dir);
        dirFile.mkdirs();
    }

    /**
     * 生成字典枚举文件
     *
     * @param dictName
     * @return
     */
    public static File buildDictSourceFile(String dictName) {
        String fileStr = String.format(ConfigUtils.getProperty("source.dict.path"), ConfigUtils.getTargetPath("supports"), dictName);
        return new File(fileStr);
    }

    /**
     * 构建各种文件.
     *
     * @param projectName the project name
     * @param moduleName  the module name
     * @param fileType    the file type
     * @return the file
     * @author maeagle
     * @date 2016-1-21 13 :23:30
     */
    public static File buildFile(String projectName, String moduleName, String fileType) {
        String fileStr = null;
        if (fileType.indexOf("pom") != -1) {
            if (moduleName.equals("parent")) {
                fileStr = String.format(ConfigUtils.getDuplicatePath(moduleName + "." + fileType), ConfigUtils.getTargetPath(projectName));
            } else {
                fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
            }
        } else if (fileType.indexOf("applicationContext") != -1) {
            if (moduleName.equals("web-app")) {
                fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName, projectName);
            } else {
                fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName, moduleName.replace("-app", ""));
            }
        } else if (fileType.indexOf("logback") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("jetty") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("jettyWebConfig") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("web") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("weblogic") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("wsconfig") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("jdbc") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("webconfig") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("filestore") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("cache") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("profile-develop") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("profile-product") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("profile-test") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("session_test") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("mqconfig") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("zkconfig") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else if (fileType.indexOf("dasc") != -1) {
            fileStr = String.format(ConfigUtils.getDuplicatePath(fileType), ConfigUtils.getTargetPath(projectName), projectName, moduleName);
        } else {
            throw new RuntimeException("No fileType matches");
        }
        return new File(fileStr);
    }

    /**
     * Backup file file.
     *
     * @param orginFile the orgin file
     * @return the file
     * @author maeagle
     * @date 2016-1-21 13 :23:30
     */
    public static File backupFile(File orginFile) {
        Assert.notNull(orginFile);
        if (!orginFile.isFile() || !orginFile.exists())
            return null;
        File backupFile = new File(orginFile.getPath() + ".bak");
        try {
            FileCopyUtils.copy(orginFile, backupFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return backupFile;
    }
}
